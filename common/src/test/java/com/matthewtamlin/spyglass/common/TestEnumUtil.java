package com.matthewtamlin.spyglass.common;

import com.matthewtamlin.spyglass.common.enum_util.EnumInstantiationException;
import com.matthewtamlin.spyglass.common.enum_util.EnumUtil;

import org.junit.Before;
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

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classAndOrdinalVariant_ordinalIsZero() {
		EnumUtil.getEnumConstant(Vehicle.class, 0);
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classAndOrdinalVariant_ordinalIsMax() {
		EnumUtil.getEnumConstant(Vehicle.class, Vehicle.values().length - 1);
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classAndOrdinalVariant_ordinalExceedsMax() {
		EnumUtil.getEnumConstant(Vehicle.class, Vehicle.values().length);
	}

	@Test
	public void testGetEnumConstant_classAndOrdinalVariant_validClassAndOrdinal() {
		final Vehicle v = EnumUtil.getEnumConstant(Vehicle.class, 0);

		assertThat(v, is(Vehicle.values()[0]));
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

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_ordinalIsZero() {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), 0);
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_ordinalIsMax() {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), Vehicle.values().length - 1);
	}

	@Test(expected = EnumInstantiationException.class)
	public void testGetEnumConstant_classnameAndOrdinalVariant_ordinalExceedsMax() {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), Vehicle.values().length);
	}

	@Test
	public void testGetEnumConstant_classnameAndOrdinalVariant_validClassAndOrdinal() {
		EnumUtil.getEnumConstant(Vehicle.class.getCanonicalName(), 0);
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
}