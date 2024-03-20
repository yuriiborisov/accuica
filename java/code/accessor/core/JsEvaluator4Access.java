package code.accessor.core;


import code.accessor.core.nashorn.NashornEvaluator;
import org.springframework.util.CollectionUtils;

import java.util.Map;

public interface JsEvaluator4Access {
	 NashornEvaluator evaluator = new NashornEvaluator();
//	boolean execute(Object entity, String statement);

	boolean execute(Object entity, User4Access user, String statement);


	Object perform(String statement, Map<String, Object> additionalMap);

	//Выполняет вычисления под пользователем
//	Object perform(String statement);

}
