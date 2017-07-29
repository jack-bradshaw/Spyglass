package com.matthewtamlin.spyglass.processor.mirror_helpers.type_mirror_helper;

import com.matthewtamlin.avatar.element_supplier.ElementId;

public class Data {
	@ElementId("with primitive boolean")
	public void method(boolean b) {}

	@ElementId("with primitive byte")
	public void method(byte b) {}

	@ElementId("with primitive char")
	public void method(char c) {}

	@ElementId("with primitive double")
	public void method(double d) {}

	@ElementId("with primitive float")
	public void method(float f) {}

	@ElementId("with primitive int")
	public void method(int i) {}

	@ElementId("with primitive long")
	public void method(long l) {}

	@ElementId("with primitive short")
	public void method(short s) {}

	@ElementId("with boxed boolean")
	public void method(Boolean b) {}

	@ElementId("with boxed byte")
	public void method(Byte b) {}

	@ElementId("with boxed char")
	public void method(Character c) {}

	@ElementId("with boxed double")
	public void method(Double d) {}

	@ElementId("with boxed float")
	public void method(Float f) {}

	@ElementId("with boxed int")
	public void method(Integer i) {}

	@ElementId("with boxed long")
	public void method(Long l) {}

	@ElementId("with boxed short")
	public void method(Short s) {}

	@ElementId("with object")
	public void method(Object o) {}

	@ElementId("with number")
	public void method(Number n) {}

	@ElementId("with custom number implementation")
	public void method(NumberImplementation n) {}

	@ElementId("with custom number implementation subclass")
	public void method(NumberImplementationSubclass n) {}

	private class NumberImplementation extends Number {
		@Override
		public int intValue() {
			return 0;
		}

		@Override
		public long longValue() {
			return 0;
		}

		@Override
		public float floatValue() {
			return 0;
		}

		@Override
		public double doubleValue() {
			return 0;
		}
	}

	private class NumberImplementationSubclass extends NumberImplementation{}

	@ElementId("interface")
	public interface Interface {}

	@ElementId("class1")
	public static class Class1 {}

	@ElementId("class2")
	public static class Class2 extends Class1 implements Interface {}

	@ElementId("class3")
	public static class Class3 extends Class2 {}

	@ElementId("class4")
	public static class Class4 {}
}
