package com.matthewtamlin.spyglass.common;

import com.matthewtamlin.spyglass.common.enum_util.EnumUtil;

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

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_classAndOrdinalVariant_negativeOrdinal() {
		final Class<? extends Enum<?>> enumClass = Vehicle.class;

		EnumUtil.getEnumConstant(enumClass, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_classAndOrdinalVariant_excessiveOrdinal() {
		final Class<? extends Enum<?>> enumClass = Vehicle.class;

		EnumUtil.getEnumConstant(enumClass, Vehicle.values().length);
	}

	@Test
	public void testGetEnumConstant_classAndOrdinalVariant_validClassAndOrdinal() {
		final Class<? extends Enum<?>> enumClass = Vehicle.class;

		final Vehicle v = (Vehicle) EnumUtil.getEnumConstant(enumClass, 0);

		assertThat(v, is(Vehicle.values()[0]));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_nullClassname() throws ClassNotFoundException {
		EnumUtil.getEnumConstant((String) null, 0);
	}

	@Test(expected = ClassNotFoundException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_classDoesNotExist() throws ClassNotFoundException {
		EnumUtil.getEnumConstant("not.a.class", 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_classIsNotAnEnum() throws ClassNotFoundException {
		EnumUtil.getEnumConstant(Object.class.getCanonicalName(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_negativeOrdinal() throws ClassNotFoundException {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_excessiveOrdinal() throws ClassNotFoundException {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), Vehicle.values().length);
	}

	@Test
	public void testGetEnumConstant_classnameAndOrdinalVariant_validClassAndOrdinal() throws ClassNotFoundException {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_fullyQualifiedConstantNameVariant_constantIsNull() throws ClassNotFoundException {
		EnumUtil.getEnumConstant(null);
	}

	@Test(expected = ClassNotFoundException.class)
	public void testGetEnumConstant_fullyQualifiedConstantNameVariant_classDoesNotExist()
			throws ClassNotFoundException {

		EnumUtil.getEnumConstant("not.a.class");
	}

	@Test(expected = ClassNotFoundException.class)
	public void testGetEnumConstant_fullyQualifiedConstantNameVariant_classIsNotAnEnum() throws ClassNotFoundException {
		EnumUtil.getEnumConstant(String.class.getCanonicalName());
	}

	@Test
	public void testGetEnumConstant_fullyQualifiedConstantNameVariant_validConstant() throws ClassNotFoundException {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName() + ".BUS");
	}
}