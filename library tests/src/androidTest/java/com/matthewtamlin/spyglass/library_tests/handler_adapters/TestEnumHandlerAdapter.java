package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.EnumHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.EnumHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

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
	private Enum expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private EnumHandler withMandatoryFlag;

	private EnumHandler missingMandatoryFlag;

	private EnumHandlerAdapter adapter;

	@Before
	public void setup() {
		final int attributeId = new Random().nextInt(Integer.MAX_VALUE);

		expectedValue = TestEnum.ITEM_3;

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(attributeId)).thenReturn(true);
		when(containingAttribute.getInt(eq(attributeId), anyInt()))
				.thenReturn(expectedValue.ordinal());
		when(containingAttribute.getInteger(eq(attributeId), anyInt()))
				.thenReturn(expectedValue.ordinal());

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(attributeId)).thenReturn(false);
		when(missingAttribute.getInt(eq(attributeId), anyInt()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since it's the default
						return invocation.getArgumentAt(1, Integer.class);
					}
				});
		when(missingAttribute.getInteger(eq(attributeId), anyInt()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since it's the default
						return invocation.getArgumentAt(1, Integer.class);
					}
				});

		withMandatoryFlag = mock(EnumHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(attributeId);
		doReturn(TestEnum.class).when(withMandatoryFlag).enumClass();
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(EnumHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(attributeId);
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

	public enum TestEnum {
		ITEM_1,
		ITEM_2,
		ITEM_3,
		ITEM_4
	}
}