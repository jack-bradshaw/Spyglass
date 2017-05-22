package com.matthewtamlin.spyglass.processors.code_generation.invocation_literal_generator;

import com.matthewtamlin.java_compiler_utilities.element_supplier.ElementId;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseString;

public class Data {
	public static final String PLACEHOLDER = "value";

	public static final String ELEMENT_ID_WITH_PLACEHOLDER = "with placeholder";

	public static final String ELEMENT_ID_WITHOUT_PLACEHOLDER = "without placeholder";

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(value = "method()", failureMessage = "Failed test for zero-param method.")
	public void method() {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(" + PLACEHOLDER + ")",
			failureMessage = "Failed test for placeholder only method.")
	public void method(Object o1) {}

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(value = "method(true)", failureMessage = "Failed @UseBoolean test without placeholder.")
	public void method(@UseBoolean(true) boolean b1) {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(false, " + PLACEHOLDER + ")",
			failureMessage = "Failed @Boolean test with placeholder.")
	public void method(@UseBoolean(false) boolean b1, Object o1) {}

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(
			value = "method(100)",
			failureMessage = "Failed @UseByte test without placeholder.")
	public void method(@UseByte(100) byte b1) {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(-100, " + PLACEHOLDER + ")",
			failureMessage = "Failed @UseByte test with placeholder.")
	public void method(@UseByte(-100) byte b1, Object o1) {}

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(
			value = "method(\'\\u1D11\')",
			failureMessage = "Failed @UseChar test without placeholder.")
	public void method(@UseChar('\u1D11') char c1) {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(" + PLACEHOLDER + ", \'\\uF2FF\')",
			failureMessage = "Failed @UseChar test with placeholder.")
	public void method(Object o1, @UseChar('\uF2FF') char c1) {}

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(
			value = "method(100.1)",
			failureMessage = "Failed @UseDouble test without placeholder.")
	public void method(@UseDouble(100.100) double d1) {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(-100.1, " + PLACEHOLDER + ")",
			failureMessage = "Failed @UseDouble test with placeholder.")
	public void method(@UseDouble(-100.100) double d1, Object o1) {}

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(value = "method(100.1F)", failureMessage = "Failed @UseFloat test without placeholder.")
	public void method(@UseFloat(100.100F) float f1) {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(-100.1F, " + PLACEHOLDER + ")",
			failureMessage = "Failed @UseFloat test with placeholder.")
	public void method(@UseFloat(-100.100F) float f1, Object o1) {}

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(value = "method(100)", failureMessage = "Failed @UseInt test without placeholder.")
	public void method(@UseInt(100) int i1) {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(-100, " + PLACEHOLDER + ")",
			failureMessage = "Failed @UseInt test with placeholder.")
	public void method(@UseInt(-100) int i1, Object o1) {}

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(value = "method(100L)", failureMessage = "Failed @UseLong test without placeholder.")
	public void method(@UseLong(100L) long l1) {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(-100L, " + PLACEHOLDER + ")",
			failureMessage = "Failed @UseLong test with placeholder.")
	public void method(@UseLong(-100L) long l1, Object o1) {}

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(value = "method(null)", failureMessage = "Failed @UseNull test without placeholder.")
	public void method(@UseNull Boolean b1) {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(null, " + PLACEHOLDER + ")",
			failureMessage = "Failed @UseNull test with placeholder.")
	public void method(@UseNull Boolean b1, Object o1) {}

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(value = "method(100)", failureMessage = "Failed @UseShort test without placeholder.")
	public void method(@UseShort(100) short s1) {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(-100, " + PLACEHOLDER + ")",
			failureMessage = "Failed @UseShort test with placeholder.")
	public void method(@UseShort(-100) short s1, Object o1) {}

	@ElementId(ELEMENT_ID_WITHOUT_PLACEHOLDER)
	@ExpectedResult(
			value = "method(\"hello \\\" world \\\\\\\\//\\\\\\\\ !! © some text__ \\\\\")",
			failureMessage = "Failed @UseString test without placeholder.")
	public void method(@UseString("hello \" world \\\\//\\\\ !! © some text__ \\") String s1) {}

	@ElementId(ELEMENT_ID_WITH_PLACEHOLDER)
	@ExpectedResult(
			value = "method(\"hello \\\" world \\\\\\\\//\\\\\\\\ !! © some text__ \\\\\", " + PLACEHOLDER + ")",
			failureMessage = "Failed @UseString test with placeholder.")
	public void method(@UseString("hello \" world \\\\//\\\\ !! © some text__ \\") String s1, Object o1) {}
}