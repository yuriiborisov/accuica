package code.uica.code.service;

import java.util.Map;

/**
 * Нужен для поиска объекта по идентификатору
 * @param <T> тип возвращаемого объекта
 */
public interface EntityService4UICA<T> {
	/**
	 * Общий метод для поиска объекта по идентификатору
	 * выдает code.entity в ответе на запрос UICA
	 * @param params параметры link{@EntityUICA.Params}
	 * @return
	 * @throws Exception
	 */
	T get(Map<String, String> params) throws Exception;
	String getType();
}
