package com.matthewtamlin.spyglass.consumer;

public class TargetException extends RuntimeException {
	private Object target;

	private String methodName;

	private Throwable thrownByTarget;

	public TargetException(final Object target, final String methodName, final Throwable thrownByTarget) {
		super(String.format(
				"Target of type \'%1$s\' threw an exception when method \'%2$s\' was invoked.",
				target.getClass(),
				methodName));

		this.target = target;
		this.methodName = methodName;
		this.thrownByTarget = thrownByTarget;

		initCause(thrownByTarget);
	}

	public Object getTarget() {
		return target;
	}

	public String getMethodName() {
		return methodName;
	}

	public Throwable getThrownByTarget() {
		return thrownByTarget;
	}
}