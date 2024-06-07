package code.accessor.core.code.nashorn;

import org.openjdk.nashorn.api.scripting.ClassFilter;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.io.Closeable;
import java.util.*;

public class NashornEvaluator {

	private class GlobalMap extends SimpleBindings implements Closeable {

		private final static String NASHORN_GLOBAL = "code.uica.code.nashorn.global";
		private Bindings global;
		private Set<String> original_keys;

		public GlobalMap(Map<String, Object> map) {
			super(map);
		}

		@Override
		public Object put(String key, Object value) {
			if (key.equals(NASHORN_GLOBAL) && value instanceof Bindings) {
				global = (Bindings) value;
				original_keys = new HashSet<>(global.keySet());
				return null;
			}
			return super.put(key, value);
		}

		@Override
		public Object get(Object key) {
			return key.equals(NASHORN_GLOBAL) ? global : super.get(key);
		}

		@Override
		public void close() {
			if (global != null) {
				Set<String> keys = new HashSet<>(global.keySet());
				keys.removeAll(original_keys);
				for (String key : keys)
					put(key, global.remove(key));
			}
		}
	}

	//safety evaluation
	public Object eval(Map<String, Object> parameters, String script, boolean disableCriticalJSFunctions, boolean disableLoadJSFunctions, boolean defaultDenyJavaClasses, List<String> javaClassesExceptionList, int maxAllowedExecTimeInSeconds) throws Exception {
		ScriptEngine engineReflex = null;
		try{
			NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
			engineReflex = factory.getScriptEngine(new ClassFilterImpl());
		}catch(Exception ex) {
			throw new Exception("Impossible to initialize the Nashorn Engine: " + ex.getMessage());
		}

		final ScriptEngine engine = engineReflex;

		if(disableCriticalJSFunctions)
			engine.eval("quit=function(){throw 'quit() not allowed';};exit=function(){throw 'exit() not allowed';};print=function(){throw 'print() not allowed';};echo=function(){throw 'echo() not allowed';};readFully=function(){throw 'readFully() not allowed';};readLine=function(){throw 'readLine() not allowed';};$ARG=null;$ENV=null;$EXEC=null;$OPTIONS=null;$OUT=null;$ERR=null;$EXIT=null;");
		if(disableLoadJSFunctions)
			engine.eval("load=function(){throw 'load() not allowed';};loadWithNewGlobal=function(){throw 'loadWithNewGlobal() not allowed';};");

		//code.uica.code.nashorn-polyfill.js
		engine.eval("var global=this;var window=this;var process={env:{}};var console={};console.debug=print;console.log=print;console.warn=print;console.error=print;");


		final ScriptMonitor scriptMonitor = new ScriptMonitor();
		SecurityContext context = SecurityContextHolder.getContext();
		Runnable run = () -> {
			try {
				scriptMonitor.scriptResult = engine.eval(script, new GlobalMap(parameters));
			} catch (ScriptException e) {
				throw new RuntimeException(e);
			} finally {
				scriptMonitor.stop();
//				ThreadContext.clearAll();
			}
		};

		DelegatingSecurityContextRunnable wrappedRunnable =
				new DelegatingSecurityContextRunnable(run, context);

		scriptMonitor.startAndWait(new Thread(wrappedRunnable), maxAllowedExecTimeInSeconds);
		Object ret = scriptMonitor.scriptResult;
		return ret;
	}

	private class ClassFilterImpl implements ClassFilter {
		final List<String> javaClassesExceptionList;
		boolean denyJavaClasses;
		public ClassFilterImpl(){
			this.javaClassesExceptionList = Collections.emptyList();
			this.denyJavaClasses = false;
		}

		public ClassFilterImpl(final List<String> classList, boolean denyJavaClasses){
			this.javaClassesExceptionList = classList;
			this.denyJavaClasses = denyJavaClasses;
		}

		@Override
		public boolean exposeToScripts(String s) {
			if(javaClassesExceptionList != null && javaClassesExceptionList.contains(s))
				return denyJavaClasses;
			return !denyJavaClasses;
		}
	}

	private class ScriptMonitor{

		public Object scriptResult = null;
		private boolean stop = false;
		Object lock = new Object();

		@SuppressWarnings("deprecation")
		public void startAndWait(Thread threadToMonitor, int secondsToWait) {
			threadToMonitor.start();
			synchronized (lock) {
				if(!stop) {
					try {
						if(secondsToWait<1)
							lock.wait();
						else
							lock.wait(1000*secondsToWait);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
			if(!stop) {
				threadToMonitor.interrupt();
				threadToMonitor.stop();
				throw new RuntimeException("Javascript forced to termination: Execution time bigger then " + secondsToWait + " seconds");
			}
		}
		public void stop() {
			synchronized (lock) {
				stop = true;
				lock.notifyAll();
			}
		}
	}

}
