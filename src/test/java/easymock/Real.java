package easymock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация маркер, говорящия от том что это настоящий объект, не мок
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Real {
	/**
	 * Альтернативное имя поля в тестируемом объекте
	 */
	String name() default "";
}
