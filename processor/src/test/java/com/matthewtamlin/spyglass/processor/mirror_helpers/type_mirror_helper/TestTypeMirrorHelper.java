package com.matthewtamlin.spyglass.processor.mirror_helpers.type_mirror_helper;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.processor.mirror_helpers.TypeMirrorHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class TestTypeMirrorHelper {
	@Rule
	public final AvatarRule avatarRule = AvatarRule.withoutSources();

	private Elements elementUtils;

	private Types typeUtils;

	private TypeMirrorHelper helper;

	@Before
	public void setup() {
		elementUtils = avatarRule.getElementUtils();
		typeUtils = avatarRule.getTypeUtils();

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
		final TypeMirror typeMirror = elementUtils.getTypeElement(Boolean.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveByte() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Byte.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveChar() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Character.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveDouble() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Double.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveFloat() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Float.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveInt() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Integer.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveLong() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Long.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsPrimitive_typeMirrorForPrimitiveShort() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Short.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedBoolean() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Boolean.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedByte() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Byte.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedCharacter() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Character.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedDouble() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Double.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedFloat() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Float.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedInteger() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Integer.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedLong() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Long.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

	@Test
	public void testIsPrimitive_typeMirrorForBoxedShort() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Short.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

	@Test
	public void testIsPrimitive_typeMirrorForObject() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Object.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveBoolean() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Boolean.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveByte() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Byte.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveChar() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Character.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveDouble() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Double.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveFloat() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Float.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveInt() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Integer.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveLong() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Long.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveShort() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Short.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedBoolean() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Boolean.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeMirror), is(false));
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedByte() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Byte.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeMirror), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedCharacter() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Character.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeMirror), is(false));
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedDouble() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Double.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeMirror), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedFloat() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Float.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeMirror), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedInteger() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Integer.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeMirror), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedLong() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Long.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeMirror), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForBoxedShort() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Short.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeMirror), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForObject() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Object.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeMirror), is(false));
	}

	@Test
	public void testIsNumber_typeMirrorForNumber() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Number.class.getCanonicalName()).asType();

		assertThat(helper.isNumber(typeMirror), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForOtherNumberImplementation() {
		final TypeMirror typeMirror = elementUtils
				.getTypeElement(NumberImplementation.class.getCanonicalName())
				.asType();

		assertThat(helper.isNumber(typeMirror), is(true));
	}

	@Test
	public void testIsNumber_typeMirrorForPrimitiveImplementationSubclass() {
		final TypeMirror typeMirror = elementUtils
				.getTypeElement(NumberImplementationSubclass.class.getCanonicalName())
				.asType();

		assertThat(helper.isNumber(typeMirror), is(true));
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveBoolean() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Boolean.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveByte() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Byte.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveChar() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Character.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveDouble() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Double.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveFloat() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Float.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveInt() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Integer.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveLong() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Long.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForPrimitiveShort() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Short.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedBoolean() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Boolean.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeMirror), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedByte() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Byte.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeMirror), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedCharacter() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Character.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeMirror), is(true));
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedDouble() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Double.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeMirror), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedFloat() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Float.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeMirror), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedInteger() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Integer.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeMirror), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedLong() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Long.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeMirror), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForBoxedShort() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Short.class.getCanonicalName()).asType();

		assertThat(helper.isCharacter(typeMirror), is(false));
	}

	@Test
	public void testIsCharacter_typeMirrorForObject() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Object.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveBoolean() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Boolean.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeUtils.unboxedType(typeMirror)), is(true));
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveByte() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Byte.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveChar() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Character.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveDouble() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Double.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveFloat() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Float.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveInt() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Integer.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveLong() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Long.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForPrimitiveShort() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Short.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeUtils.unboxedType(typeMirror)), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedBoolean() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Boolean.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeMirror), is(true));
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedByte() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Byte.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeMirror), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedCharacter() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Character.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeMirror), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedDouble() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Double.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeMirror), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedFloat() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Float.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeMirror), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedInteger() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Integer.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeMirror), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedLong() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Long.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeMirror), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForBoxedShort() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Short.class.getCanonicalName()).asType();

		assertThat(helper.isBoolean(typeMirror), is(false));
	}

	@Test
	public void testIsBoolean_typeMirrorForObject() {
		final TypeMirror typeMirror = elementUtils.getTypeElement(Object.class.getCanonicalName()).asType();

		assertThat(helper.isPrimitive(typeMirror), is(false));
	}

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
}