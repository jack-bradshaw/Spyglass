package com.matthewtamlin.spyglass.processor.mirror_helpers.type_mirror_helper;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processor.mirror_helpers.TypeMirrorHelper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class TestTypeMirrorHelper {
	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor" +
			"/mirror_helpers/type_mirror_helper/Data.java");

	private IdBasedElementSupplier elementSupplier;

	private TypeMirrorHelper helper;

	@BeforeClass
	public static void setupClass() {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
	}

	@Before
	public void setup() throws MalformedURLException {
		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		elementSupplier = new IdBasedElementSupplier(dataFileObject);

		helper = new TypeMirrorHelper(compilationRule.getElements(), compilationRule.getTypes());
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
		doIsPrimitiveTestFor("with primitive boolean", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveByte() {
		doIsPrimitiveTestFor("with primitive byte", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveChar() {
		doIsPrimitiveTestFor("with primitive char", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveDouble() {
		doIsPrimitiveTestFor("with primitive double", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveFloat() {
		doIsPrimitiveTestFor("with primitive float", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveInt() {
		doIsPrimitiveTestFor("with primitive int", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveLong() {
		doIsPrimitiveTestFor("with primitive long", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveShort() {
		doIsPrimitiveTestFor("with primitive short", true);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedBoolean() {
		doIsPrimitiveTestFor("with boxed boolean", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedByte() {
		doIsPrimitiveTestFor("with boxed byte", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedCharacter() {
		doIsPrimitiveTestFor("with boxed char", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedDouble() {
		doIsPrimitiveTestFor("with boxed double", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedFloat() {
		doIsPrimitiveTestFor("with boxed float", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedInteger() {
		doIsPrimitiveTestFor("with boxed int", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedLong() {
		doIsPrimitiveTestFor("with boxed long", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedShort() {
		doIsPrimitiveTestFor("with boxed short", false);
	}

	@Test
	public void testIsPrimitive_typeMirrorForObject() {
		doIsPrimitiveTestFor("with object", false);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveBoolean() {
		doIsNumberTestFor("with primitive boolean", false);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveByte() {
		doIsNumberTestFor("with primitive byte", true);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveChar() {
		doIsNumberTestFor("with primitive char", false);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveDouble() {
		doIsNumberTestFor("with primitive double", true);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveFloat() {
		doIsNumberTestFor("with primitive float", true);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveInt() {
		doIsNumberTestFor("with primitive int", true);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveLong() {
		doIsNumberTestFor("with primitive long", true);
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveShort() {
		doIsNumberTestFor("with primitive short", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedBoolean() {
		doIsNumberTestFor("with boxed boolean", false);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedByte() {
		doIsNumberTestFor("with boxed byte", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedChar() {
		doIsNumberTestFor("with boxed char", false);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedDouble() {
		doIsNumberTestFor("with boxed double", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedFloat() {
		doIsNumberTestFor("with boxed float", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedInt() {
		doIsNumberTestFor("with boxed int", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedLong() {
		doIsNumberTestFor("with boxed long", true);
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedShort() {
		doIsNumberTestFor("with boxed short", true);
	}

	@Test
	public void testIsNumber_typeMirrorForObject() {
		doIsNumberTestFor("with object", false);
	}

	@Test
	public void testIsNumber_typeMirrorForNumber() {
		doIsNumberTestFor("with number", true);
	}

	@Test
	public void testIsNumber_typeMirrorForOtherNumberImplementation() {
		doIsNumberTestFor("with custom number implementation", true);
	}

	@Test
	public void testIsNumber_typeMirrorForOtherNumberImplementationSubclass() {
		doIsNumberTestFor("with custom number implementation subclass", true);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveBoolean() {
		doIsCharacterTestFor("with primitive boolean", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveByte() {
		doIsCharacterTestFor("with primitive byte", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveChar() {
		doIsCharacterTestFor("with primitive char", true);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveDouble() {
		doIsCharacterTestFor("with primitive double", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveFloat() {
		doIsCharacterTestFor("with primitive float", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveInt() {
		doIsCharacterTestFor("with primitive int", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveLong() {
		doIsCharacterTestFor("with primitive long", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveShort() {
		doIsCharacterTestFor("with primitive short", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedBoolean() {
		doIsCharacterTestFor("with boxed boolean", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedByte() {
		doIsCharacterTestFor("with boxed byte", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedChar() {
		doIsCharacterTestFor("with boxed char", true);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedDouble() {
		doIsCharacterTestFor("with boxed double", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedFloat() {
		doIsCharacterTestFor("with boxed float", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedInt() {
		doIsCharacterTestFor("with boxed int", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedLong() {
		doIsCharacterTestFor("with boxed long", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedShort() {
		doIsCharacterTestFor("with boxed short", false);
	}

	@Test
	public void testIsCharacter_typeMirrorForObject() {
		doIsCharacterTestFor("with object", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveBoolean() {
		doIsBooleanTestFor("with primitive boolean", true);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveByte() {
		doIsBooleanTestFor("with primitive byte", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveChar() {
		doIsBooleanTestFor("with primitive char", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveDouble() {
		doIsBooleanTestFor("with primitive double", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveFloat() {
		doIsBooleanTestFor("with primitive float", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveInt() {
		doIsBooleanTestFor("with primitive int", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveLong() {
		doIsBooleanTestFor("with primitive long", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveShort() {
		doIsBooleanTestFor("with primitive short", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedBoolean() {
		doIsBooleanTestFor("with boxed boolean", true);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedByte() {
		doIsBooleanTestFor("with boxed byte", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedChar() {
		doIsBooleanTestFor("with boxed char", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedDouble() {
		doIsBooleanTestFor("with boxed double", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedFloat() {
		doIsBooleanTestFor("with boxed float", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedInt() {
		doIsBooleanTestFor("with boxed int", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedLong() {
		doIsBooleanTestFor("with boxed long", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedShort() {
		doIsBooleanTestFor("with boxed short", false);
	}

	@Test
	public void testIsBoolean_typeMirrorForObject() {
		doIsBooleanTestFor("with object", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveBoolean() {
		doIsEnumTestFor("with primitive boolean", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveByte() {
		doIsEnumTestFor("with primitive byte", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveChar() {
		doIsEnumTestFor("with primitive char", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveDouble() {
		doIsEnumTestFor("with primitive double", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveFloat() {
		doIsEnumTestFor("with primitive float", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveInt() {
		doIsEnumTestFor("with primitive int", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveLong() {
		doIsEnumTestFor("with primitive long", false);
	}

	@Test
	public void testIsEnum_typeMirrorForPrimitiveShort() {
		doIsEnumTestFor("with primitive short", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedBoolean() {
		doIsEnumTestFor("with boxed boolean", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedByte() {
		doIsEnumTestFor("with boxed byte", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedChar() {
		doIsEnumTestFor("with boxed char", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedDouble() {
		doIsEnumTestFor("with boxed double", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedFloat() {
		doIsEnumTestFor("with boxed float", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedInt() {
		doIsEnumTestFor("with boxed int", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedLong() {
		doIsEnumTestFor("with boxed long", false);
	}

	@Test
	public void testIsEnum_typeMirrorForBoxedShort() {
		doIsEnumTestFor("with boxed short", false);
	}

	@Test
	public void testIsEnum_typeMirrorForObject() {
		doIsEnumTestFor("with object", false);
	}

	@Test
	public void testIsEnum_typeMirrorForRawEnum() {
		doIsEnumTestFor("with raw enum", true);
	}

	@Test
	public void testIsEnum_typeMirrorForWildcardEnum() {
		doIsEnumTestFor("with wildcard enum", true);
	}

	@Test
	public void testIsEnum_typeMirrorForRegularEnum() {
		doIsEnumTestFor("with regular enum", true);
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveBoolean() {
		doBoxPrimitiveTestWithPassExpectedFor("with primitive boolean", Boolean.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveByte() {
		doBoxPrimitiveTestWithPassExpectedFor("with primitive byte", Byte.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveChar() {
		doBoxPrimitiveTestWithPassExpectedFor("with primitive char", Character.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveDouble() {
		doBoxPrimitiveTestWithPassExpectedFor("with primitive double", Double.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveFloat() {
		doBoxPrimitiveTestWithPassExpectedFor("with primitive float", Float.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveInt() {
		doBoxPrimitiveTestWithPassExpectedFor("with primitive int", Integer.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveLong() {
		doBoxPrimitiveTestWithPassExpectedFor("with primitive long", Long.class.getCanonicalName());
	}

	@Test
	public void testBoxPrimitive_typeMirrorForPrimitiveShort() {
		doBoxPrimitiveTestWithPassExpectedFor("with primitive short", Short.class.getCanonicalName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedBoolean() {
		doBoxPrimitiveTestWithFailExpectedFor("with boxed boolean");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedByte() {
		doBoxPrimitiveTestWithFailExpectedFor("with boxed byte");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedChar() {
		doBoxPrimitiveTestWithFailExpectedFor("with boxed char");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedDouble() {
		doBoxPrimitiveTestWithFailExpectedFor("with boxed double");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedFloat() {
		doBoxPrimitiveTestWithFailExpectedFor("with boxed float");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedInt() {
		doBoxPrimitiveTestWithFailExpectedFor("with boxed int");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedLong() {
		doBoxPrimitiveTestWithFailExpectedFor("with boxed long");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForBoxedShort() {
		doBoxPrimitiveTestWithFailExpectedFor("with boxed short");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxPrimitive_typeMirrorForObject() {
		doBoxPrimitiveTestWithPassExpectedFor("with object", Object.class.getCanonicalName());
	}

	private void doIsPrimitiveTestFor(final String elementId, final boolean expectedResult) {
		final ExecutableElement method = (ExecutableElement) elementSupplier.getUniqueElementWithId(elementId);
		final VariableElement parameter = method.getParameters().get(0);

		assertThat(helper.isPrimitive(parameter.asType()), is(expectedResult));
	}

	private void doIsNumberTestFor(final String elementId, final boolean expectedResult) {
		final ExecutableElement method = (ExecutableElement) elementSupplier.getUniqueElementWithId(elementId);
		final VariableElement parameter = method.getParameters().get(0);

		assertThat(helper.isNumber(parameter.asType()), is(expectedResult));
	}

	private void doIsCharacterTestFor(final String elementId, final boolean expectedResult) {
		final ExecutableElement method = (ExecutableElement) elementSupplier.getUniqueElementWithId(elementId);
		final VariableElement parameter = method.getParameters().get(0);

		assertThat(helper.isCharacter(parameter.asType()), is(expectedResult));
	}

	private void doIsBooleanTestFor(final String elementId, final boolean expectedResult) {
		final ExecutableElement method = (ExecutableElement) elementSupplier.getUniqueElementWithId(elementId);
		final VariableElement parameter = method.getParameters().get(0);

		assertThat(helper.isBoolean(parameter.asType()), is(expectedResult));
	}

	private void doIsEnumTestFor(final String elementId, final boolean expectedResult) {
		final ExecutableElement method = (ExecutableElement) elementSupplier.getUniqueElementWithId(elementId);
		final VariableElement parameter = method.getParameters().get(0);

		assertThat(helper.isEnum(parameter.asType()), is(expectedResult));
	}

	private void doBoxPrimitiveTestWithPassExpectedFor(final String elementId, final String expectedResultClassName) {
		final ExecutableElement method = (ExecutableElement) elementSupplier.getUniqueElementWithId(elementId);
		final VariableElement parameter = method.getParameters().get(0);

		final TypeMirror boxed = helper.boxPrimitive(parameter.asType());
		assertThat(boxed.toString(), is(expectedResultClassName));
	}

	private void doBoxPrimitiveTestWithFailExpectedFor(final String elementId) {
		final ExecutableElement method = (ExecutableElement) elementSupplier.getUniqueElementWithId(elementId);
		final VariableElement parameter = method.getParameters().get(0);

		helper.boxPrimitive(parameter.asType());
	}
}