package code.accessor.core.code;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Использует сервисы из основной части
 */
@Aspect
@Component
public class AccessorProxy {

	private final AccessorServiceConfiguration configuration;
	private final PrivilegesByKeyGetter privilegesByKeyGetter;

	private final AccessorCalculator calculator;

	public AccessorProxy(AccessorServiceConfiguration configuration, PrivilegesByKeyGetter privilegesByKeyGetter, AccessorCalculator calculator) {
		this.configuration = configuration;
		this.privilegesByKeyGetter = privilegesByKeyGetter;
		this.calculator = calculator;
	}

	@Around("execution(* *(..)) && @annotation(annotation)")
	public Object proceed(ProceedingJoinPoint call, MethodAccess annotation) throws Throwable {
		Object[] args = call.getArgs();
		String entity = annotation.entity();
		List<Privilege4Access> methodPrivileges = configuration.getMethodAccessService4Access().getPrivileges(annotation.key());
		if(annotation.filter()) {
			Map<String, Set<Privilege4Access>> userPrivileges = calculator.getUserPrivileges();
			List<Privilege4Access> common = methodPrivileges.stream().filter(userPrivileges.get(annotation.entity())::contains).collect(Collectors.toList());
			return filter(call,args, common);
		}

		for (int argIndex = 0; argIndex < args.length; argIndex++) {
			if(NashornAccessible.class.isAssignableFrom(args[argIndex].getClass())){
				calculator.checkAccessForCurrentUser(entity, args[argIndex], methodPrivileges);
				return call.proceed();
			}
		}
		calculator.checkAccessForCurrentUser(entity, null, methodPrivileges);
		return call.proceed();
	}

	private Object filter(final ProceedingJoinPoint call, final Object[] args, final List<Privilege4Access> privileges)
			throws Throwable {
		for (int argIndex = 0; argIndex < args.length; argIndex++) {
			if(args[argIndex] instanceof Collection<?>){
				args[argIndex] = privileges.stream()
						.filter(e -> e.isFilter())
						.map(e-> {
							Object filter = configuration.getFilterConfig4Access().getFilter(e.getFilterValue());
							return filter;
						})
						.filter(
						Objects::nonNull).collect(Collectors.toList());

			}
		}
		return call.proceed(args);
	}
}
