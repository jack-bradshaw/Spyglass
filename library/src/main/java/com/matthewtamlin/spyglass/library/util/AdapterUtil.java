package com.matthewtamlin.spyglass.library.util;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.HandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.Handler;
import com.matthewtamlin.spyglass.library.meta_annotations.Use;
import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getUseAnnotations;

@SuppressWarnings("TryWithIdenticalCatches") // Can't actually collapse blocks until API 19
public class AdapterUtil {
	private static final String EXCEPTION_MESSAGE = "Could not instantiate class %1$s. " +
			"Does the class have a public no-arg constructor?";

	public static HandlerAdapter<?, Annotation> getHandlerAdapter(final Field field) {
		checkNotNull(field, "Argument \'field\' cannot be null.");

		final Annotation handlerAnnotation = AnnotationUtil.getHandlerAnnotation(field);

		checkNotNull(handlerAnnotation, "The supplied field does not have a handler annotation.");

		final Class<? extends HandlerAdapter> clazz = handlerAnnotation
				.annotationType()
				.getAnnotation(Handler.class)
				.adapterClass();

		try {
			return clazz.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, clazz), e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, clazz), e);
		}
	}

	public static HandlerAdapter<?, Annotation> getHandlerAdapter(final Method method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		final Annotation handlerAnnotation = AnnotationUtil.getHandlerAnnotation(method);

		checkNotNull(handlerAnnotation, "The supplied method does not have a handler annotation.");

		final Class<? extends HandlerAdapter> clazz = handlerAnnotation
				.annotationType()
				.getAnnotation(Handler.class)
				.adapterClass();

		try {
			return clazz.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, clazz), e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, clazz), e);
		}
	}

	public static DefaultAdapter<?, Annotation> getDefaultAdapter(final Field field) {
		checkNotNull(field, "Argument \'field\' cannot be null.");

		final Annotation defaultAnnotation = AnnotationUtil.getDefaultAnnotation(field);

		checkNotNull(defaultAnnotation, "The supplied field does not have a default annotation.");

		final Class<? extends DefaultAdapter> clazz = defaultAnnotation
				.annotationType()
				.getAnnotation(Default.class)
				.adapterClass();

		try {
			return clazz.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, clazz), e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, clazz), e);
		}
	}

	public static DefaultAdapter<?, Annotation> getDefaultAdapter(final Method method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		final Annotation defaultAnnotation = AnnotationUtil.getDefaultAnnotation(method);

		checkNotNull(defaultAnnotation, "The supplied method does not have a default annotation.");

		final Class<? extends DefaultAdapter> clazz = defaultAnnotation
				.annotationType()
				.getAnnotation(Default.class)
				.adapterClass();

		try {
			return clazz.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, clazz), e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, clazz), e);
		}
	}

	public static Map<Integer, UseAdapter<?, Annotation>> getUseAdapters(
			final Method method) {

		final Map<Integer, UseAdapter<?, Annotation>> adapters = new HashMap<>();

		final Map<Integer, Annotation> useAnnotations = getUseAnnotations(method);

		for (final Integer i : useAnnotations.keySet()) {
			final Class<? extends UseAdapter> adapterClass = useAnnotations
					.get(i)
					.annotationType()
					.getAnnotation(Use.class)
					.adapterClass();

			try {
				adapters.put(i, adapterClass.newInstance());
			} catch (final InstantiationException e) {
				throw new RuntimeException(String.format(EXCEPTION_MESSAGE, adapterClass), e);
			} catch (final IllegalAccessException e) {
				throw new RuntimeException(String.format(EXCEPTION_MESSAGE, adapterClass), e);
			}
		}

		return adapters;
	}
}