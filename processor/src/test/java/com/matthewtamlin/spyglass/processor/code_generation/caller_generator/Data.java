package com.matthewtamlin.spyglass.processor.code_generation.caller_generator;

import com.matthewtamlin.avatar.element_supplier.ElementId;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.StringHandler;

public class Data {
	@ElementId("no handler")
	public void noHandler() {}

	@ElementId("value handler with default")
	@BooleanHandler(attributeId = 1)
	@DefaultToBoolean(false)
	public void valueHandlerAndDefault(boolean b) {}

	@ElementId("value handler no default")
	@StringHandler(attributeId = 1)
	public void valueHandlerOnly(String s) {}

	@ElementId("call handler")
	@SpecificEnumHandler(attributeId = 1, ordinal = 1)
	public void callHandler() {}
}