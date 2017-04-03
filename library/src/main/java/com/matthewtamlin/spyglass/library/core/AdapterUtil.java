package com.matthewtamlin.spyglass.library.core;

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

		final String exceptionMessage = String.format(
				"Could not instantiate HandlerAdapter from class %1$s. Does the class have a " +
						"public no-arg constructor?",
				clazz.getName());

		try {
			return clazz.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(exceptionMessage, e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(exceptionMessage, e);
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

		final String exceptionMessage = String.format(
				"Could not instantiate HandlerAdapter from class %1$s. Does the class have a " +
						"public no-arg constructor?",
				clazz.getName());

		try {
			return clazz.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(exceptionMessage, e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(exceptionMessage, e);
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

		final String exceptionMessage = String.format(
				"Could not instantiate DefaultAdapter from class %1$s. Does the class have a " +
						"public no-arg constructor?",
				clazz.getName());

		try {
			return clazz.newInstance();
		} catch (final InstantiationException e) {
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(exceptionMessage, e);
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

		final String exceptionMessage = String.format(
				"Could not instantiate DefaultAdapter from class %1$s. Does the class have a " +
						"public no-arg constructor?",
				clazz.getName());

		try {
			return clazz.newInstance();
		} catch (final InstantiationException e) {
			throw new RuntimeException(exceptionMessage, e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(exceptionMessage, e);
		}
	}

	public static UseAdapter<?, Annotation> getUseAdapterForParam(
			final Method method,
			final int index) {

		// Get annotations for all methods in the class
		final Annotation[][] annotationsByParam = method.getParameterAnnotations();

		// Iterate over the annotations for the specific parameter of interest
		for (final Annotation annotation : annotationsByParam[index]) {
			// May be null
			final Use useAnnotation = annotation.annotationType().getAnnotation(Use.class);

			if (useAnnotation != null) {
				final Class<? extends UseAdapter> adapterClass = useAnnotation.adapterClass();

				try {
					return adapterClass.newInstance();
				} catch (Exception e) {
					throw new RuntimeException(
							String.format(
									"Could not instantiate UseAdapter from class %1$s. Does the " +
											"class have a public no-arg constructor?",
									adapterClass),
							e);
				}
			}
		}

		return null;
	}
}