package com.matthewtamlin.spyglass.processor.code_generation.specific_value_is_available_method_generator;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.markers.annotations.call_handler_annotations.SpecificBooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.call_handler_annotations.SpecificFlagHandler;

public class Data {
	@ElementId("specific boolean")
	@SpecificBooleanHandler(attributeId = 1, handledBoolean = true)
	public void withSpecificBoolean() {}

	@ElementId("specific enum")
	@SpecificEnumHandler(attributeId = 1, ordinal = 0)
	public void withSpecificEnum() {}

	@ElementId("specific flag")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void withSpecificFlag() {}
}