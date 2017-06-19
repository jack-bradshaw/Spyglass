package com.matthewtamlin.spyglass.processor.code_generation.specific_value_is_available_method_generator;

import com.matthewtamlin.avatar.element_supplier.ElementId;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;

public class Data {
	@ElementId("no call handler")
	@DefaultToBoolean(true)
	public void noValueHandlerAnnotation() {}

	@ElementId("specific enum")
	@SpecificEnumHandler(attributeId = 1, ordinal = 0)
	public void withSpecificEnum() {}

	@ElementId("specific flag")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void withSpecificFlag() {}
}