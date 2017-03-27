package com.matthewtamlin.spyglass.library.core;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.HandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.Handler;

import java.lang.annotation.Annotation;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@SuppressWarnings("TryWithIdenticalCatches") // Can't actually collapse blocks until API 19
public class AdapterUtil {
	public static HandlerAdapter<?, Annotation> getHandlerAdapter(
			final Annotation handlerAnnotation) {

		checkNotNull(handlerAnnotation, "Argument \'handlerAnnotation\' cannot be null.");

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

	public static DefaultAdapter<?, Annotation> getDefaultAdapter(
			final Annotation defaultAnnotation) {

		checkNotNull(defaultAnnotation, "Argument \'defaultAnnotation\' cannot be null.");

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
}