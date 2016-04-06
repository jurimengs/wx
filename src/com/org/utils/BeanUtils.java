package com.org.utils;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.org.annotations.ClassContext;

/**
 * 
 * @author zhou_man
 *
 *         2016年3月24日
 */
public class BeanUtils {
	
	public static Object getBean(String beanName) {
		return ClassContext.getInstance().getObject(beanName);
	}

	/**
	 * 使用cglib动态代理创建对象，与jdk反射效率基本一样（createBeanJdk）
	 * 
	 * @param <T>
	 * @return
	 */
	public static Object createBeanCglib(Class<?> c) {
		Object res = Enhancer.create(c, new MethodInterceptorImpl());
		return res;
	}

	public static Object createBeanCglib(String className) {
		Class<?> c;
		try {
			c = Class.forName(className);
			return createBeanCglib(c);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用jdk反射 根据class反射
	 * @param <T>
	 * 
	 * @param c
	 * @return
	 */
	public static <T> T createBeanJdk(Class<T> c) {
		T s = null;
		try {
			s = c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 使用jdk反射根据class名称反射
	 * @param className
	 * @return
	 */
	public static Object createBeanJdk(String className) {
		Class<?> c;
		try {
			c = Class.forName(className);
			return createBeanJdk(c);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static class MethodInterceptorImpl implements MethodInterceptor {
		public Object intercept(Object obj, Method method, Object[] args,
				MethodProxy proxy) throws Throwable {
			System.out.println(method);
			proxy.invokeSuper(obj, args);
			return null;
		}
	}

}
