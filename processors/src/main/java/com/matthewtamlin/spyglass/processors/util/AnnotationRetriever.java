package com.matthewtamlin.spyglass.processors.util;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.CALL_HANDLER_ANNOTATIONS;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.DEFAULT_ANNOTATIONS;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.USE_ANNOTATIONS;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.VALUE_HANDLER_ANNOTATIONS;

@Tested(testMethod = "automated")
public class AnnotationRetriever {
	public static Annotation getValueHandlerAnnotation(final Element method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

		for (final Class<? extends Annotation> a : VALUE_HANDLER_ANNOTATIONS) {
			if (method.getAnnotation(a) != null) {
				return method.getAnnotation(a);
			}
		}

		return null;
	}

	public static Annotation getCallHandlerAnnotation(final Element method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

		for (final Class<? extends Annotation> a : CALL_HANDLER_ANNOTATIONS) {
			if (method.getAnnotation(a) != null) {
				return method.getAnnotation(a);
			}
		}

		return null;
	}

	public static Annotation getDefaultAnnotation(final Element method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

		for (final Class<? extends Annotation> a : DEFAULT_ANNOTATIONS) {
			if (method.getAnnotation(a) != null) {
				return method.getAnnotation(a);
			}
		}

		return null;
	}

	public static Map<Integer, Annotation> getUseAnnotations(final ExecutableElement method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

		final Map<Integer, Annotation> useAnnotationsByIndex = new HashMap<>();
		final List<? extends VariableElement> params = method.getParameters();

		for (int i = 0; i < params.size(); i++) {
			for (final Class<? extends Annotation> a : USE_ANNOTATIONS) {
				final Annotation foundAnnotation = params.get(i).getAnnotation(a);

				if (foundAnnotation != null) {
					useAnnotationsByIndex.put(i, foundAnnotation);
				}
			}
		}

		return useAnnotationsByIndex;
	}
}