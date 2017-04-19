package com.matthewtamlin.spyglass.library_tests.value_handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.value_handler_adapters.BooleanHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.BooleanHandler;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestBooleanHandlerAdapter extends TestValueHandlerAdapter<
		Boolean,
		BooleanHandler,
		BooleanHandlerAdapter> {

	private static final int ATTRIBUTE_ID = 1829; // random

	private Boolean expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private BooleanHandler annotation;

	private BooleanHandlerAdapter adapter;

	@Before
	public void setup() {
		expectedValue = Boolean.TRUE;

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingAttribute.getBoolean(eq(ATTRIBUTE_ID), anyBoolean()))
				.thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getBoolean(eq(ATTRIBUTE_ID), anyBoolean()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since it's the default
						return invocation.getArgumentAt(1, Boolean.class);
					}
				});

		annotation = mock(BooleanHandler.class);
		when(annotation.attributeId()).thenReturn(ATTRIBUTE_ID);

		adapter = new BooleanHandlerAdapter();
	}

	@Override
	public Boolean getExpectedValue() {
		return expectedValue;
	}

	@Override
	public TypedArray getTypedArrayContainingAttribute() {
		return containingAttribute;
	}

	@Override
	public TypedArray getTypedArrayMissingAttribute() {
		return missingAttribute;
	}

	@Override
	public BooleanHandler getAnnotation() {
		return annotation;
	}

	@Override
	public BooleanHandlerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public int getAttributeId() {
		return ATTRIBUTE_ID;
	}
}