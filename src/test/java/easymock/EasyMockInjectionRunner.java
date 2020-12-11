package easymock;

import org.easymock.EasyMockRunner;
import org.easymock.TestSubject;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Раннер, который не только инсертит моки в тестовый класс, но так же и инсертит реальные объекты, которые представлены аннотацией {@link Real}
 *
 */
public class EasyMockInjectionRunner extends EasyMockRunner {
	public EasyMockInjectionRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {
		//инжектим наши бины
		injectRealObjects(target);
		//и далее идем по стандартному воркфлоу изимока
		return super.withBefores(method, target, statement);
	}


	public static void injectRealObjects(Object test) {
		final List<Object> testObjects = getTestObjects(test);
		final Map<String, Object> injectedFields = getInjectedFields(test);
		for (Object testObject : testObjects) {
			injectToObject(testObject, injectedFields);
		}
	}

	private static void injectToObject(Object testObject, Map<String, Object> injectedFields) {
		Class<?> targetClass = testObject.getClass();
		List<Field> fieldsToInject = new ArrayList<>(Arrays.asList(targetClass.getDeclaredFields()));
		while ((targetClass = targetClass.getSuperclass()) != Object.class) {
			fieldsToInject.addAll(Arrays.asList(targetClass.getDeclaredFields()));
		}

		fieldsToInject.stream()
				.filter(field -> injectedFields.containsKey(field.getName()))
				.forEach(field -> {
					final Object value = injectedFields.get(field.getName());
					ReflectionTestUtils.setField(testObject, field.getName(), value);
				});
	}

	private static List<Object> getTestObjects(Object objectWithTarget) {
		Class<?> aClass = objectWithTarget.getClass();
		return Stream.of(aClass.getDeclaredFields())
				.filter(f -> f.getAnnotation(TestSubject.class) != null)
				.map(f -> getValueFromField(objectWithTarget, f))
				.collect(Collectors.toList());
	}

	private static Map<String, Object> getInjectedFields(Object test) {
		return Stream.of(test.getClass().getDeclaredFields())
				.filter(f -> f.getAnnotation(Real.class) != null)
				.collect(Collectors.toMap(f -> {
					final Real real = f.getAnnotation(Real.class);
					return real.name().length() > 0 ? real.name() : f.getName();
				}, f -> getValueFromField(test, f)));
	}

	private static Object getValueFromField(Object target, Field field) {
		try {
			field.setAccessible(true);
			return field.get(target);
		} catch (IllegalAccessException e) {
			System.err.println(String.format("Error with get value from field %s, return null", field.getName()));
			e.printStackTrace();
			return null;
		}
	}
}
