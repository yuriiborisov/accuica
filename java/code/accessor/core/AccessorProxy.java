package code.accessor.core;

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
	private final AccessorCalculator calculator;
	private final PrivilegesByKeyGetter privilegesByKeyGetter;

	public AccessorProxy(AccessorServiceConfiguration configuration, PrivilegesByKeyGetter privilegesByKeyGetter) {
		this.configuration = configuration;
		this.calculator = new AccessorCalculator(configuration);
		this.privilegesByKeyGetter = privilegesByKeyGetter;
	}

	@Around("execution(* *(..)) && @annotation(annotation)")
	public Object proceed(ProceedingJoinPoint call, MethodAccess annotation) throws Throwable {
		Object[] args = call.getArgs();
		String entity = annotation.entity();
		List<Privilege4Access> methodPrivileges = privilegesByKeyGetter.getPrivileges(annotation.key());
		AccessorCalculator accessorCalculator = new AccessorCalculator(configuration);
		if(annotation.filter()) {
			Map<String, Set<Privilege4Access>> userPrivileges = accessorCalculator.getUserPrivileges();
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
