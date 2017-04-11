package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.EnumConstantHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.FlagHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.FlagHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getInt(eq(ATTRIBUTE_ID), anyInt())).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(final InvocationOnMock invocation) throws Throwable {
				// Always return the second argument since it's the default
				return invocation.getArgumentAt(1, Integer.class);
			}
		});
		when(missingAttribute.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since it's the default
						return invocation.getArgumentAt(1, Integer.class);
					}
				});

		containingFlag1 = mock(TypedArray.class);
		when(containingFlag1.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingFlag1.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_1);
		when(containingFlag1.getInteger(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_1);

		containingFlag2 = mock(TypedArray.class);
		when(containingFlag2.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingFlag2.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_2);
		when(containingFlag2.getInteger(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_2);

		containingFlags1And2 = mock(TypedArray.class);
		when(containingFlags1And2.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingFlags1And2.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_1 | FLAG_2);
		when(containingFlags1And2.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(FLAG_1 | FLAG_2);

		handlesFlag1 = mock(FlagHandler.class);
		when(handlesFlag1.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(handlesFlag1.handledFlags()).thenReturn(FLAG_1);

		handlesFlag1And2 = mock(FlagHandler.class);
		when(handlesFlag1And2.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(handlesFlag1And2.handledFlags()).thenReturn(FLAG_1 | FLAG_2);

		adapter = new FlagHandlerAdapter();
	}

	@Test
	public void testReflectiveInstantiation() throws Exception {
		FlagHandlerAdapter.class.newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_nullAnnotation() {
		adapter.getAccessor(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callValueExistsInArray_nullSupplied() {
		adapter.getAccessor(handlesFlag1).valueExistsInArray(null);
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_handlesSingleFlag_missingAttribute() {

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
	public void testGetAccessor_callValueExistsInArray_handlesMultipleFlags_missingAttribute() {

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

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_nullSupplied() {

	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_handlesSingleFlag_missingAttribute() {

	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_handlesSingleFlag_oneFlagNoMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesSingleFlag_oneFlagOneMatch() {

	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_handlesSingleFlag_twoFlagsNoMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesSingleFlag_twoFlagsOneMatch() {

	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_missingAttribute() {

	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_oneFlagNoMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_oneFlagOneMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_twoFlagsNoMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_twoFlagsOneMatch() {

	}

	@Test
	public void testGetAccessor_callGetValueFromArray_handlesMultipleFlags_twoFlagsTwoMatches() {

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