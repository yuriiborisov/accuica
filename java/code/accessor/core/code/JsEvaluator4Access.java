package code.accessor.core.code;


import code.accessor.core.code.dto.User4Access;
import code.accessor.core.code.nashorn.NashornEvaluator;

import java.util.Map;

public interface JsEvaluator4Access {
	 NashornEvaluator evaluator = new NashornEvaluator();
//	boolean execute(Object entity, String statement);

	boolean execute(Object entity, User4Access user, String statement);


	Object perform(String statement, Map<String, Object> additionalMap);

	//Выполняет вычисления под пользователем
//	Object perform(String statement);

}
