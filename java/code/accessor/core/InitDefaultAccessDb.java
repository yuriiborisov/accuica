package code.accessor.core;

import code.accessor.core.impl.MethodAccess4AccessImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
//import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.annotation.PostConstruct;
import java.util.*;


@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class InitDefaultAccessDb {
	@Value("#{new Boolean('${default.initialization.privileges.enabled}')}")
	private Boolean defaultInitializationEnabled;

	private final MethodAccessService4Access methodAccessService;

	public InitDefaultAccessDb(final MethodAccessService4Access methodAccessService, final PrivilegeService4Access p) {

		this.methodAccessService = methodAccessService;
	}

	private final List<Class> accessors = new ArrayList<>();


	@PostConstruct
	private void init(){
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

		scanner.addIncludeFilter(new AssignableTypeFilter(Accessor.class));
		//Поиск ведем в том же пакете, где сигнальный интерфейс акцессоров
		//Собираем все акcессоры.
		scanner
				.findCandidateComponents(Accessor.class.getPackageName())
				.stream()
				.map(BeanDefinition::getBeanClassName)
				.filter(Objects::nonNull)
				.forEach(e->accessors.add(getClassForName(e)));
		//Если надо инитить по умолчанию- инитим
		if(defaultInitializationEnabled) {
			accessors.forEach(service -> initMethodPrivileges(getInfo(service)));
		}
	}

	private Class getClassForName(final String name){
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	private List<DefaultAccessInfo> getInfo(Class clazz){
		List<DefaultAccessInfo> infos = new ArrayList<>();
		Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
			MethodAccess annotation = method.getAnnotation(MethodAccess.class);
			if(annotation != null){
				DefaultAccessInfo info = new DefaultAccessInfo();
				info.setKey(annotation.key());
				info.setDescription(annotation.description());
				info.setHumanName(annotation.humanName());
				Set<String> entities = new HashSet<>();
//				Arrays.stream(annotation.entities()).forEach(e -> entities.add(e));
				info.setEntity(annotation.entity());
				infos.add(info);
			}
		});
		return infos;
	}

	protected void initMethodPrivileges(List<DefaultAccessInfo> infos){
		for(DefaultAccessInfo info : infos){
			if(methodAccessService.getByKey(info.getKey()) == null){
				String key = info.getKey();
				MethodAccess4AccessImpl methodAccess4Access = new MethodAccess4AccessImpl();
				methodAccess4Access.setKey(key);
				methodAccess4Access.setEntityType(info.getEntity());
				methodAccess4Access.setHumanName(info.getHumanName());
				methodAccess4Access.setDescription(info.getDescription());
				methodAccess4Access.setPrivilegeIds(Collections.EMPTY_LIST);
				methodAccessService.create(methodAccess4Access);
			}
		}
	}

}
