package com.matthewtamlin.spyglass.processor.core.companion_namer;

import com.matthewtamlin.avatar.rules.ElementId;

@ElementId("top level")
public class Data {
	@ElementId("nested one level")
	public class ClassA {
		public class ClassB {
			public class ClassC {
				@ElementId("nested multiple levels")
				public class ClassD {

				}
			}
		}
	}
}