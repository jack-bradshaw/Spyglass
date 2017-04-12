package com.matthewtamlin.spyglass.library.util;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;
import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;
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

	public static ValueHandlerAdapter<?, Annotation> getValueHandlerAdapter(final Field field) {
		checkNotNull(field, "Argument \'field\' cannot be null.");

		final Annotation handlerAnnotation = AnnotationUtil.getValueHandlerAnnotation(field);

		if (handlerAnnotation == null) {
			return null;
		}

		final Class<? extends ValueHandlerAdapter> adapterClass = handlerAnnotation
				.annotationType()
				.getAnnotation(ValueHandler.class)
				.adapterClass();

		try {
			return adapterClass.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, adapterClass), e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, adapterClass), e);
		}
	}

	public static ValueHandlerAdapter<?, Annotation> getValueHandlerAdapter(final Method method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		final Annotation handlerAnnotation = AnnotationUtil.getValueHandlerAnnotation(method);

		if (handlerAnnotation == null) {
			return null;
		}

		final Class<? extends ValueHandlerAdapter> adapterClass = handlerAnnotation
				.annotationType()
				.getAnnotation(ValueHandler.class)
				.adapterClass();

		try {
			return adapterClass.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, adapterClass), e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, adapterClass), e);
		}
	}

	public static DefaultAdapter<?, Annotation> getDefaultAdapter(final Field field) {
		checkNotNull(field, "Argument \'field\' cannot be null.");

		final Annotation defaultAnnotation = AnnotationUtil.getDefaultAnnotation(field);

		if (defaultAnnotation == null) {
			return null;
		}

		final Class<? extends DefaultAdapter> adapterClass = defaultAnnotation
				.annotationType()
				.getAnnotation(Default.class)
				.adapterClass();

		try {
			return adapterClass.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, adapterClass), e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, adapterClass), e);
		}
	}

	public static DefaultAdapter<?, Annotation> getDefaultAdapter(final Method method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		final Annotation defaultAnnotation = AnnotationUtil.getDefaultAnnotation(method);

		if (defaultAnnotation == null) {
			return null;
		}

		final Class<? extends DefaultAdapter> adapterClass = defaultAnnotation
				.annotationType()
				.getAnnotation(Default.class)
				.adapterClass();

		try {
			return adapterClass.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, adapterClass), e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(String.format(EXCEPTION_MESSAGE, adapterClass), e);
		}
	}

	public static Map<Integer, UseAdapter<?, Annotation>> getUseAdapters(final Method method)  {
		checkNotNull(method, "Argument \'method \' cannot be null.");

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