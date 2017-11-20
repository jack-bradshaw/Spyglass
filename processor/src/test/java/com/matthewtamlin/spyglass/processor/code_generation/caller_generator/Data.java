package com.matthewtamlin.spyglass.processor.code_generation.caller_generator;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.markers.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.StringHandler;

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
	@SpecificEnumHandler(attributeId = 1, handledOrdinal = 1)
	public void callHandler() {}
}