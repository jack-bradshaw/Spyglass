package com.matthewtamlin.spyglass.consumer;

/**
 * Exception that indicates a method in a target class threw an exception when invoked by the Spyglass framework.
 * The exception contains the target object, the called method name, and the original exception.
 */
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

	/**
	 * @return the target which threw the exception, not null
	 */
	public Object getTarget() {
		return target;
	}

	/**
	 * @return the name of the method which throw the exception, not null
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @return the exception which was thrown by the target, not null
	 */
	public Throwable getThrownByTarget() {
		return thrownByTarget;
	}
}