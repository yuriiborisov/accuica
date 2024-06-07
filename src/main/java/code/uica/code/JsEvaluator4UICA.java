package code.uica.code;


import java.util.Map;

public interface JsEvaluator4UICA {
	//проверка заточенная под uic
	boolean execute(Object entity, String statement);
	//Выполняет вычисления под пользователем
	Object perform(String statement);

	//Выполняет вычисления под пользователем . В отличие от предыдущего метода добавляет в мапу code.entity
	Object perform(String statement, Map<String, Object> additionalMap);

	JsEvaluator4UICA addObjectAccessible(String key, Object o);

}
