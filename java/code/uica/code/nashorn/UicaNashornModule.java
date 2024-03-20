package code.uica.code.nashorn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
@Scope("prototype")
public class UicaNashornModule implements code.uica.code.JsEvaluator4UICA {
	private final JsEvaluator4UICA evaluator = new JsEvaluator4UICA();

	private Map<String, Object> objectsAccessible;
	public UicaNashornModule() {
		this.objectsAccessible = new HashMap<>();
	}

	public UicaNashornModule addObjectAccessible(String key, Object o){
		this.objectsAccessible.put(key, o);
		return this;
	}

	private Map<String, Object> getMap(Object entity){
		objectsAccessible.put("entity", entity);
		return objectsAccessible;
	}


	//Вычисляет скрипт и возвращает true - false - разрешена или нет операция
	@Override
	public boolean execute(Object entity, String statement){
		Map<String, Object> map = getMap(entity);
		return isAllowed(map, statement);
	}

	@Override
	public Object perform(String statement,Map<String, Object> additionalMap) {
		Map<String, Object> map = getMap(null);
		if(!CollectionUtils.isEmpty(additionalMap)){
			map.putAll(additionalMap);
		}
		try{
			if(statement == null || "".equals(statement)){
				return true;
			}
			return evaluator.eval(map, statement,
					false,
					false,
					true,
					null,
					10);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	//возвращает результат выполнения скрипта Мапа параметров формируется только из сотрудника.
	@Override
	public Object perform(String statement){
		return perform(statement, null);
	}

	private boolean isAllowed(Map<String, Object> map, String statement){
		try{
			if(statement == null || "".equals(statement)){
				return true;
			}
			Object result_ = evaluator.eval(map, statement,
					false,
					false,
					true,
					null,
					10);
			return getBoolean(result_);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean getBoolean(Object object){
		if(object == null){
			//TODO выкинуть исключение что равно нулю
			return false;
		}
		try {
			return (Boolean) object == true ? true : false;
		}catch (Exception e){
			//TODO выкинуть исключение что должно возвращать true или false
			return false;
		}
	}
}
