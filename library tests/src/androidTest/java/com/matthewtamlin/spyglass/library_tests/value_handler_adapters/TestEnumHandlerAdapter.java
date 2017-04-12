package com.matthewtamlin.spyglass.library_tests.value_handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.value_handler_adapters.EnumHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.EnumHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestEnumHandlerAdapter {
	private static final int ATTRIBUTE_ID = 3329;

	private Enum expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private EnumHandler withMandatoryFlag;

	private EnumHandler missingMandatoryFlag;

	private EnumHandlerAdapter adapter;

	@Before
	public void setup() {
		expectedValue = TestEnum.ITEM_3;

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingAttribute.getInt(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(expectedValue.ordinal());
		when(containingAttribute.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(expectedValue.ordinal());

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getInt(eq(ATTRIBUTE_ID), anyInt()))
				.thenAnswer(new Answer<Object>() {
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

		withMandatoryFlag = mock(EnumHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		doReturn(TestEnum.class).when(withMandatoryFlag).enumClass();
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(EnumHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		doReturn(TestEnum.class).when(withMandatoryFlag).enumClass();
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

		adapter = new EnumHandlerAdapter();
	}

	@Test
	public void testReflectiveInstantiation() throws Exception {
		adapter.getClass().newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_nullAnnotation() {
		adapter.getAccessor(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callValueExistsInArray_nullSupplied() {
		adapter.getAccessor(withMandatoryFlag).valueExistsInArray(null);
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueAvailable() {
		final boolean result = adapter.getAccessor(withMandatoryFlag)
				.valueExistsInArray(containingAttribute);

		assertThat(result, is(true));
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueMissing() {
		final boolean result = adapter.getAccessor(withMandatoryFlag)
				.valueExistsInArray(missingAttribute);

		assertThat(result, is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_nullSupplied() {
		adapter.getAccessor(withMandatoryFlag).getValueFromArray(null);
	}

	@Test
	public void testGetAccessor_callGetValueFromArray_valueAvailable() {
		final Enum value = adapter.getAccessor(withMandatoryFlag)
				.getValueFromArray(containingAttribute);

		assertThat(value, is(expectedValue));
	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_valueMissing() {
		adapter.getAccessor(withMandatoryFlag).getValueFromArray(missingAttribute);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeId_nullSupplied() {
		adapter.getAttributeId(null);
	}

	@Test
	public void testGetAttributeId_nonNullSupplied() {
		final int attributeId = adapter.getAttributeId(withMandatoryFlag);

		assertThat(attributeId, is(ATTRIBUTE_ID));
	}

	@Test
	public void testAttributeIsMandatory_mandatoryFlagPresent() {
		final boolean mandatory = adapter.isMandatory(withMandatoryFlag);

		assertThat(mandatory, is(true));
	}

	@Test
	public void testAttributeIsMandatory_mandatoryFlagMissing() {
		final boolean mandatory = adapter.isMandatory(missingMandatoryFlag);

		assertThat(mandatory, is(false));
	}

	@SuppressWarnings("unused")
	public enum TestEnum {
		ITEM_1,
		ITEM_2,
		ITEM_3,
		ITEM_4
	}
}