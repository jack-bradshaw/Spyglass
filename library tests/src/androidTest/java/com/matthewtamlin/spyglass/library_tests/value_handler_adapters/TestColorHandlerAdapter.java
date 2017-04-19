package com.matthewtamlin.spyglass.library_tests.value_handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.value_handler_adapters.ColorHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.ColorHandler;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestColorHandlerAdapter extends TestValueHandlerAdapter<
		Integer,
		ColorHandler,
		ColorHandlerAdapter> {

	private static final int ATTRIBUTE_ID = 9422;

	private Integer expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private ColorHandler annotation;

	private ColorHandlerAdapter adapter;

	@Before
	public void setup() {
		expectedValue = new Random().nextInt(Integer.MAX_VALUE);

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingAttribute.getColor(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getColor(eq(ATTRIBUTE_ID), anyInt()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since it's the default
						return invocation.getArgumentAt(1, Integer.class);
					}
				});

		annotation = mock(ColorHandler.class);
		when(annotation.attributeId()).thenReturn(ATTRIBUTE_ID);

		adapter = new ColorHandlerAdapter();
	}

	@Override
	public Integer getExpectedValue() {
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
	public ColorHandler getAnnotation() {
		return annotation;
	}

	@Override
	public ColorHandlerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public int getAttributeId() {
		return ATTRIBUTE_ID;
	}
}