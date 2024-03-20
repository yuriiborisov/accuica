package code.accessor.core.exception;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * Кастомный класс для эксепшенов
 * в основном удобная фича тут
 *
 * public CustomException(String format, Object... argArray) {
 * 		super(getTuple(format, argArray).getMessage(), getTuple(format, argArray).getThrowable());
 *        }
 */
public class BaseException extends RuntimeException {

	protected BaseException(String format, Object... argArray) {
		super(getTuple(format, argArray).getMessage(), getTuple(format, argArray).getThrowable());
	}

	private static FormattingTuple getTuple(String format, Object[] argArray){
		return MessageFormatter.arrayFormat(format, argArray);
	}
}
