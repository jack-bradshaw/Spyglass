package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.FlagHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.FlagHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestFlagHandlerAdapter {
	private static final int ATTRIBUTE_ID = 8527;

	private static final int FLAG_1 = 0b1;

	private static final int FLAG_2 = 0b10;

	private TypedArray missingAttribute;

	private TypedArray containingFlag1;

	private TypedArray containingFlag2;

	private TypedArray containingFlags1And2;

	private FlagHandler handlesFlag1;

	private FlagHandler handlesFlag1And2;

	private FlagHandlerAdapter adapter;

	@Before
	public void setup() {

	}

	@Test
	public void testReflectiveInstantiation() throws Exception {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_nullAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callValueExistsInArray_nullSupplied() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesSingleFlag_noFlags() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesSingleFlag_oneFlagNoMatch() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesSingleFlag_oneFlagOneMatch() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesSingleFlag_twoFlagsNoMatch() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesSingleFlag_twoFlagsOneMatch() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesMultipleFlags_noFlags() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesMultipleFlags_oneFlagNoMatch() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesMultipleFlags_oneFlagOneMatch() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesMultipleFlags_twoFlagsNoMatch() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesMultipleFlags_twoFlagsOneMatch() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesMultipleFlags_twoFlagsTwoMatches() {

	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueMissing() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_nullSupplied() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_handlesSingleFlag_noFlags() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_handlesSingleFlag_oneFlagNoMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesSingleFlag_oneFlagOneMatch() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_handlesSingleFlag_twoFlagsNoMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesSingleFlag_twoFlagsOneMatch() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_noFlags() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_oneFlagNoMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_oneFlagOneMatch() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_twoFlagsNoMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_twoFlagsOneMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_twoFlagsTwoMatches() {

	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_valueMissing() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeId_nullSupplied() {

	}

	@Test
	public void testGetAttributeId_nonNullSupplied() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsMandatory_nullAnnotation() {

	}

	@Test
	public void testIsMandatory_nonNullSupplied() {

	}
}