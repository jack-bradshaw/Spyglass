package com.matthewtamlin.spyglass.processor.mirror_helpers.type_mirror_helper;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.processor.mirror_helpers.TypeMirrorHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class TestTypeMirrorHelper {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/mirror_helpers/" +
					"type_mirror_helper/Data.java")
			.build();

	private Elements elementUtils;

	private TypeMirrorHelper helper;

	@Before
	public void setup() {
		elementUtils = avatarRule.getElementUtils();
		helper = new TypeMirrorHelper(avatarRule.getElementUtils(), avatarRule.getTypeUtils());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullElementUtil() {
		new TypeMirrorHelper(null, mock(Types.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullTypeUtil() {
		new TypeMirrorHelper(mock(Elements.class), null);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveBoolean() {
		doIsPrimitiveTestFor("primitive boolean", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveByte() {
		doIsPrimitiveTestFor("primitive byte", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveChar() {
		doIsPrimitiveTestFor("primitive char", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveDouble() {
		doIsPrimitiveTestFor("primitive double", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveFloat() {
		doIsPrimitiveTestFor("primitive float", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveInt() {
		doIsPrimitiveTestFor("primitive int", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveLong() {
		doIsPrimitiveTestFor("primitive long", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveShort() {
		doIsPrimitiveTestFor("primitive short", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedBoolean() {
		doIsPrimitiveTestFor("boxed boolean", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedByte() {
		doIsPrimitiveTestFor("boxed byte", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedCharacter() {
		doIsPrimitiveTestFor("boxed char", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedDouble() {
		doIsPrimitiveTestFor("boxed double", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedFloat() {
		doIsPrimitiveTestFor("boxed float", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedInteger() {
		doIsPrimitiveTestFor("boxed int", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedLong() {
		doIsPrimitiveTestFor("boxed long", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedShort() {
		doIsPrimitiveTestFor("boxed short", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForObject() {
		doIsPrimitiveTestFor("object", false);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveBoolean() {
		doIsNumberTestFor("primitive boolean", false);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveByte() {
		doIsNumberTestFor("primitive byte", true);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveChar() {
		doIsNumberTestFor("primitive char", false);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveDouble() {
		doIsNumberTestFor("primitive double", true);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveFloat() {
		doIsNumberTestFor("primitive float", true);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveInt() {
		doIsNumberTestFor("primitive int", true);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveLong() {
		doIsNumberTestFor("primitive long", true);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveShort() {
		doIsNumberTestFor("primitive short", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedBoolean() {
		doIsNumberTestFor("boxed boolean", false);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedByte() {
		doIsNumberTestFor("boxed byte", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedChar() {
		doIsNumberTestFor("boxed char", false);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedDouble() {
		doIsNumberTestFor("boxed double", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedFloat() {
		doIsNumberTestFor("boxed float", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedInt() {
		doIsNumberTestFor("boxed int", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedLong() {
		doIsNumberTestFor("boxed long", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedShort() {
		doIsNumberTestFor("boxed short", true);
	}

	@Test
	public void testIsNumber_typeMirrorForObject() {
		doIsNumberTestFor("object", false);
	}

	@Test
	public void testIsNumber_typeMirrorForNumber() {
		doIsNumberTestFor("number", true);
	}

	@Test
	public void testIsNumber_typeMirrorForOtherNumberImplementation() {
		doIsNumberTestFor("custom number implementation", true);
	}

	@Test
	public void testIsNumber_typeMirrorForOtherNumberImplementationSubclass() {
		doIsNumberTestFor("custom number implementation subclass", true);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveBoolean() {
		doIsCharacterTestFor("primitive boolean", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveByte() {
		doIsCharacterTestFor("primitive byte", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveChar() {
		doIsCharacterTestFor("primitive char", true);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveDouble() {
		doIsCharacterTestFor("primitive double", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveFloat() {
		doIsCharacterTestFor("primitive float", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveInt() {
		doIsCharacterTestFor("primitive int", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveLong() {
		doIsCharacterTestFor("primitive long", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveShort() {
		doIsCharacterTestFor("primitive short", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedBoolean() {
		doIsCharacterTestFor("boxed boolean", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedByte() {
		doIsCharacterTestFor("boxed byte", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedChar() {
		doIsCharacterTestFor("boxed char", true);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedDouble() {
		doIsCharacterTestFor("boxed double", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedFloat() {
		doIsCharacterTestFor("boxed float", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedInt() {
		doIsCharacterTestFor("boxed int", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedLong() {
		doIsCharacterTestFor("boxed long", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedShort() {
		doIsCharacterTestFor("boxed short", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForObject() {
		doIsCharacterTestFor("object", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveBoolean() {
		doIsBooleanTestFor("primitive boolean", true);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveByte() {
		doIsBooleanTestFor("primitive byte", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveChar() {
		doIsBooleanTestFor("primitive char", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveDouble() {
		doIsBooleanTestFor("primitive double", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveFloat() {
		doIsBooleanTestFor("primitive float", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveInt() {
		doIsBooleanTestFor("primitive int", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveLong() {
		doIsBooleanTestFor("primitive long", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveShort() {
		doIsBooleanTestFor("primitive short", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedBoolean() {
		doIsBooleanTestFor("boxed boolean", true);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedByte() {
		doIsBooleanTestFor("boxed byte", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedChar() {
		doIsBooleanTestFor("boxed char", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedDouble() {
		doIsBooleanTestFor("boxed double", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedFloat() {
		doIsBooleanTestFor("boxed float", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedInt() {
		doIsBooleanTestFor("boxed int", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedLong() {
		doIsBooleanTestFor("boxed long", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedShort() {
		doIsBooleanTestFor("boxed short", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForObject() {
		doIsBooleanTestFor("object", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveBoolean() {
		doIsEnumTestFor("primitive boolean", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveByte() {
		doIsEnumTestFor("primitive byte", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveChar() {
		doIsEnumTestFor("primitive char", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveDouble() {
		doIsEnumTestFor("primitive double", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveFloat() {
		doIsEnumTestFor("primitive float", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveInt() {
		doIsEnumTestFor("primitive int", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveLong() {
		doIsEnumTestFor("primitive long", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveShort() {
		doIsEnumTestFor("primitive short", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedBoolean() {
		doIsEnumTestFor("boxed boolean", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedByte() {
		doIsEnumTestFor("boxed byte", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedChar() {
		doIsEnumTestFor("boxed char", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedDouble() {
		doIsEnumTestFor("boxed double", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedFloat() {
		doIsEnumTestFor("boxed float", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedInt() {
		doIsEnumTestFor("boxed int", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedLong() {
		doIsEnumTestFor("boxed long", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedShort() {
		doIsEnumTestFor("boxed short", false);
	}

	@Test
	public void testIsEnum_typeMirrorForObject() {
		doIsEnumTestFor("object", false);
	}

	@Test
	public void testIsEnum_typeMirrorForRawEnum() {
		doIsEnumTestFor("raw enum", true);
	}

	@Test
	public void testIsEnum_typeMirrorForWildcardEnum() {
		doIsEnumTestFor("wildcard enum", true);
	}

	@Test
	public void testIsEnum_typeMirrorForRegularEnum() {
		doIsEnumTestFor("regular enum", true);
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveBoolean() {
		doBoxPrimitiveTestWithPassExpectedFor("primitive boolean", Boolean.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveByte() {
		doBoxPrimitiveTestWithPassExpectedFor("primitive byte", Byte.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveChar() {
		doBoxPrimitiveTestWithPassExpectedFor("primitive char", Character.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveDouble() {
		doBoxPrimitiveTestWithPassExpectedFor("primitive double", Double.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveFloat() {
		doBoxPrimitiveTestWithPassExpectedFor("primitive float", Float.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveInt() {
		doBoxPrimitiveTestWithPassExpectedFor("primitive int", Integer.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveLong() {
		doBoxPrimitiveTestWithPassExpectedFor("primitive long", Long.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveShort() {
		doBoxPrimitiveTestWithPassExpectedFor("primitive short", Short.class.getCanonicalName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedBoolean() {
		doBoxPrimitiveTestWithFailExpectedFor("boxed boolean");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedByte() {
		doBoxPrimitiveTestWithFailExpectedFor("boxed byte");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedChar() {
		doBoxPrimitiveTestWithFailExpectedFor("boxed char");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedDouble() {
		doBoxPrimitiveTestWithFailExpectedFor("boxed double");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedFloat() {
		doBoxPrimitiveTestWithFailExpectedFor("boxed float");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedInt() {
		doBoxPrimitiveTestWithFailExpectedFor("boxed int");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedLong() {
		doBoxPrimitiveTestWithFailExpectedFor("boxed long");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedShort() {
		doBoxPrimitiveTestWithFailExpectedFor("boxed short");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForObject() {
		doBoxPrimitiveTestWithPassExpectedFor("object", Object.class.getCanonicalName());
	}

	private void doIsPrimitiveTestFor(final String elementId, final boolean expectedResult) {
		final VariableElement parameter = avatarRule.getElementWithUniqueId(elementId);

		assertThat(helper.isPrimitive(parameter.asType()), is(expectedResult));
	}

	private void doIsNumberTestFor(final String elementId, final boolean expectedResult) {
		final VariableElement parameter = avatarRule.getElementWithUniqueId(elementId);
		helper.isNumber(parameter.asType());
		assertThat(helper.isNumber(parameter.asType()), is(expectedResult));
	}

	private void doIsCharacterTestFor(final String elementId, final boolean expectedResult) {
		final VariableElement parameter = avatarRule.getElementWithUniqueId(elementId);

		assertThat(helper.isCharacter(parameter.asType()), is(expectedResult));
	}

	private void doIsBooleanTestFor(final String elementId, final boolean expectedResult) {
		final VariableElement parameter = avatarRule.getElementWithUniqueId(elementId);

		assertThat(helper.isBoolean(parameter.asType()), is(expectedResult));
	}

	private void doIsEnumTestFor(final String elementId, final boolean expectedResult) {
		final VariableElement parameter = avatarRule.getElementWithUniqueId(elementId);

		assertThat(helper.isEnum(parameter.asType()), is(expectedResult));
	}

	private void doBoxPrimitiveTestWithPassExpectedFor(final String elementId, final String expectedResultClassName) {
		final VariableElement parameter = avatarRule.getElementWithUniqueId(elementId);

		final TypeMirror boxed = helper.boxPrimitive(parameter.asType());
		assertThat(boxed.toString(), is(expectedResultClassName));
	}

	private void doBoxPrimitiveTestWithFailExpectedFor(final String elementId) {
		final VariableElement parameter = avatarRule.getElementWithUniqueId(elementId);

		helper.boxPrimitive(parameter.asType());
	}
}