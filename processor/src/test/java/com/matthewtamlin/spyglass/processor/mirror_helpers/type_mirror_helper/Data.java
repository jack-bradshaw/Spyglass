package com.matthewtamlin.spyglass.processor.mirror_helpers.type_mirror_helper;

import com.matthewtamlin.avatar.rules.ElementId;

public class Data {
	public void method(@ElementId("primitive boolean") boolean b) {}
	
	public void method(@ElementId("primitive byte") byte b) {}
	
	public void method(@ElementId("primitive char") char c) {}
	
	public void method(@ElementId("primitive double") double d) {}
	
	public void method(@ElementId("primitive float") float f) {}
	
	public void method(@ElementId("primitive int") int i) {}
	
	public void method(@ElementId("primitive long") long l) {}
	
	public void method(@ElementId("primitive short") short s) {}
	
	public void method(@ElementId("boxed boolean") Boolean b) {}
	
	public void method(@ElementId("boxed byte") Byte b) {}
	
	public void method(@ElementId("boxed char") Character c) {}
	
	public void method(@ElementId("boxed double") Double d) {}
	
	public void method(@ElementId("boxed float") Float f) {}
	
	public void method(@ElementId("boxed int") Integer i) {}
	
	public void method(@ElementId("boxed long") Long l) {}
	
	public void method(@ElementId("boxed short") Short s) {}
	
	public void method(@ElementId("object") Object o) {}
	
	public void method(@ElementId("number") Number n) {}
	
	public void method(@ElementId("custom number implementation") NumberImplementation n) {}
	
	public void method(@ElementId("custom number implementation subclass") NumberImplementationSubclass n) {}

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

	private class NumberImplementationSubclass extends NumberImplementation {}

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
