package com.matthewtamlin.spyglass.processors.grouper.type_grouper;

import com.matthewtamlin.avatar.element_supplier.ElementId;

@ElementId("primary class")
public class Data {
	@ElementId("primary class method")
	public void outerMethod() {}

	@ElementId("primary class field")
	public String outerField;

	@ElementId("inner class")
	public static class InnerClass {
		@ElementId("inner class method")
		public void innerMethod() {}

		@ElementId("inner class field")
		public boolean innerField;
	}

	public static class SomeClass {
		public static class SomeOtherClass {
			@ElementId("very nested class")
			public static class NestedClass {
				@ElementId("very nested class method")
				public void innerInnerInnerMethod() {
					@ElementId("element inside method")
					String s;
				}

				@ElementId("very nested class field")
				public int innerField;
			}
		}
	}
}

@ElementId("secondary class")
class SecondaryClass {
	@ElementId("secondary class method")
	public void secondaryMethod() {}

	@ElementId("secondary class field")
	public int secondaryField;
}