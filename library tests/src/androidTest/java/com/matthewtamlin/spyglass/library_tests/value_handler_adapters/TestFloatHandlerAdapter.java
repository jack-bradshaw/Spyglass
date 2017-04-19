package com.matthewtamlin.spyglass.library_tests.value_handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.value_handler_adapters.FloatHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FloatHandler;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestFloatHandlerAdapter extends TestValueHandlerAdapter<
		Float,
		FloatHandler,
		FloatHandlerAdapter> {

	private static final int ATTRIBUTE_ID = 9928;

	private Float expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private FloatHandler annotation;

	private FloatHandlerAdapter adapter;

	@Before
	public void setup() {
		expectedValue = Float.MAX_VALUE;

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingAttribute.getFloat(eq(ATTRIBUTE_ID), anyFloat())).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getFloat(eq(ATTRIBUTE_ID), anyFloat()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since it's the default
						return invocation.getArgumentAt(1, Float.class);
					}
				});

		annotation = mock(FloatHandler.class);
		when(annotation.attributeId()).thenReturn(ATTRIBUTE_ID);

		adapter = new FloatHandlerAdapter();
	}

	@Override
	public Float getExpectedValue() {
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
	public FloatHandler getAnnotation() {
		return annotation;
	}

	@Override
	public FloatHandlerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public int getAttributeId() {
		return ATTRIBUTE_ID;
	}
}