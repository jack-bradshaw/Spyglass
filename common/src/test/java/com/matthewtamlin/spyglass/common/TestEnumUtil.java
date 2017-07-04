package com.matthewtamlin.spyglass.common;

import com.matthewtamlin.spyglass.common.required_at_runtime.enum_util.EnumInstantiationException;
import com.matthewtamlin.spyglass.common.required_at_runtime.enum_util.EnumUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class TestEnumUtil {
	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_classAndOrdinalVariant_nullClass() {
		EnumUtil.getEnumConstant((Class) null, 0);
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classAndOrdinalVariant_ordinalIsNegative() {
		EnumUtil.getEnumConstant(Vehicle.class, -1);
	}

	@Test
	public void testGetEnumConstant_classAndOrdinalVariant_ordinalIsZero() {
		final Vehicle result = EnumUtil.getEnumConstant(Vehicle.class, 0);

		assertThat(result, is(Vehicle.values()[0]));
	}

	@Test
	public void testGetEnumConstant_classAndOrdinalVariant_ordinalIsMax() {
		final int max = Vehicle.values().length - 1;

		final Vehicle result = EnumUtil.getEnumConstant(Vehicle.class, max);

		assertThat(result, is(Vehicle.values()[max]));
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classAndOrdinalVariant_ordinalExceedsMax() {
		EnumUtil.getEnumConstant(Vehicle.class, Vehicle.values().length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_nullClassname() {
		EnumUtil.getEnumConstant((String) null, 0);
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_classDoesNotExist() {
		EnumUtil.getEnumConstant("not.a.class", 0);
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_classIsNotAnEnum() {
		EnumUtil.getEnumConstant(Object.class.getCanonicalName(), 0);
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_ordinalIsNegative() {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), -1);
	}

	@Test
	public void testGetEnumConstant_classnameAndOrdinalVariant_ordinalIsZero() {
		final Object result = EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), 0);

		assertThat(result, is((Object) Vehicle.values()[0]));
	}

	@Test
	public void testGetEnumConstant_classnameAndOrdinalVariant_ordinalIsMax() {
		final int maxOrdinal = Vehicle.values().length - 1;

		final Object result = EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), maxOrdinal);

		assertThat(result, is((Object) Vehicle.values()[maxOrdinal]));
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_ordinalExceedsMax() {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), Vehicle.values().length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_fullyQualifiedConstantNameVariant_constantIsNull() {
		EnumUtil.getEnumConstant(null);
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_fullyQualifiedConstantNameVariant_classDoesNotExist() {
		EnumUtil.getEnumConstant("not.a.class");
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_fullyQualifiedConstantNameVariant_classIsNotAnEnum() {
		EnumUtil.getEnumConstant(String.class.getCanonicalName());
	}

	@Test
	public void testGetEnumConstant_fullyQualifiedConstantNameVariant_validConstant() {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName() + ".BUS");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumClass_classIsNull() {
		EnumUtil.getEnumClass(null);
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumClass_classDoesNotExist() {
		EnumUtil.getEnumClass("not.a.class");
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumClass_classIsNotAnEnum() {
		EnumUtil.getEnumClass(String.class.getCanonicalName());
	}

	public void testGetEnumClass_classIsValid() {
		final Class<? extends Enum<?>> enumClass = EnumUtil.getEnumClass(Vehicle.class.getCanonicalName());

		assertThat(enumClass.getCanonicalName(), is(Vehicle.class.getCanonicalName()));
	}
}