package learn.java.basics.generic;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericReflectionExample {

	private static List<Foo> list = new ArrayList<>();

	public static void main(String[] args) {
//		example1();
//		example2();
//		example3();
		example4();
//		Bar<Integer> bar = Bar.<Integer>create();
//		Bar<Object> objectBar = Bar.create();
//      Double func = bar.func(0D);
//		Double func1 = bar.<Double>func(0D);
    }

	/**
	 * 反射操作泛型，弥补了运行期间泛型擦除问题
	 */
	private static void example1() {
		// interface java.lang.reflect.ParameterizedType
		log.info("{}", ParameterizedType.class);
		// interface java.lang.reflect.TypeVariable
		log.info("{}", TypeVariable.class);
		// interface java.lang.reflect.GenericArrayType
		log.info("{}", GenericArrayType.class);
		// interface java.lang.reflect.WildcardType
		log.info("{}", WildcardType.class);
	}

	/**
	 * 获得父类或接口泛型类型，子类继承父类时指定了具体类型
	 */
	private static void example2() {
		Type[] genericInterfaces = Foo.class.getGenericInterfaces();
		// Type genericSuperclass = Foo.class.getGenericSuperclass();
		for (Type type : genericInterfaces) {
			if (type instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType)type;
				// T
				log.info("ArgumentActualType: {}", parameterizedType.getActualTypeArguments()[0]);
				// interface learn.java.basics.generic.BaseGeneric
				log.info("RawType: {}", parameterizedType.getRawType());
				// null
				log.info("OwnerType: {}", parameterizedType.getOwnerType());
			}
		}
	}


	/**
	 * 获得父类或接口泛型类型，子类继承父类时没有指定具体类型
	 */
	private static void example3() {
		Type[] genericInterfaces = Bar.class.getGenericInterfaces();
		for (Type type : genericInterfaces) {
			if (type instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType)type;
				// T
				log.info("ArgumentActualType: {}", parameterizedType.getActualTypeArguments()[0]);
				// interface learn.java.basics.generic.BaseGeneric
				log.info("RawType: {}", parameterizedType.getRawType());
				// null
				log.info("OwnerType: {}", parameterizedType.getOwnerType());
			}
		}
	}

	/**
	 * 获取泛型参数
	 */
	private static void example4() {
		List<Foo> list = new ArrayList<>();
		TypeVariable[] typeParameters = list.getClass().getTypeParameters();
		for (TypeVariable  typeVariable :typeParameters) {
			// E
			log.info("{}", typeVariable.getName());
			// class java.util.ArrayList
			log.info("{}", typeVariable.getGenericDeclaration());
			// class java.lang.Object
			log.info("{}", typeVariable.getBounds());
		}
	}

	/**
	 * 利用反射获得方法属性参数化类型，方法形参参数化类型，进一步获得参数化类型的原生类型和泛型参数具体类型
	 */
	private static void example5() {
		try {
			Field field = GenericReflectionExample.class.getDeclaredField("list");
			Type genericType = field.getGenericType();
			if (genericType instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) genericType;

				// class learn.java.basics.generic.Foo
				log.info("ArgumentActualType: {}", parameterizedType.getActualTypeArguments()[0]);
				// interface java.util.List
				log.info("RawType: {}", parameterizedType.getRawType());
				// null
				log.info("OwnerType: {}", parameterizedType.getOwnerType());
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
}

interface BaseGeneric<T> {

	T get();
}

class Foo implements BaseGeneric<Integer> {

	private Integer value;

	@Override
	public Integer get() {
		return value;
	}
}

class Bar<T> implements BaseGeneric<T> {

	private T value;

	@Override
	public T get() {
		return value;
	}

	/**
	 * 'learn.java.basics.generic.Bar.this' cannot be referenced from a static context
	 */
	/*public static Bar<T> create() {
		return new Bar<>();
	}*/

	/**
	 * It's ok, but
	 * in use: Bar<Integer> bar = Bar.<Integer>create();
	 */
	public static <E> Bar<E> create() {
		return new Bar<>();
	}

	/**
	 * 泛型方法的 <T> 和泛型类的 <T> 不是同一个 T
	 */
	public <T> T func(T obj) {
		// 下属的代码编译不通过，编译器提示 Required type: T Provided: T，这就说明泛型类的 T 和泛型方法的 T 不是同一个 T，他们之间没有
		// 任何关系，所以泛型方法的泛型参数应该和泛型类的命名不同，以示区分。
		// return value;
		// It's ok.
		return obj;
	}
}

class BarChild extends Bar<Integer> {

}