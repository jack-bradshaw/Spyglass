package com.matthewtamlin.spyglass.library.core;

import java.lang.reflect.Method;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class ArgumentUtil {
	public static Map<Integer, Object> getArgsFromUseAnnotations(final Method method) {
		//TODO
		return null;
	}

	public static Integer getIndexOfUnannotatedArg(final Method method) {
		//TODO
		return null;
	}

	public static Integer countArguments(final Method method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		return method.getParameterTypes().length;
	}
}