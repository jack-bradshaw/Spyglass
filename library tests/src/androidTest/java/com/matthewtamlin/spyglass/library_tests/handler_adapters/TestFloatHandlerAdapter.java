package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.FloatHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.FloatHandler;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestFloatHandlerAdapter
		extends TestHandlerAdapter<Float, FloatHandler, FloatHandlerAdapter> {

	private Float expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private FloatHandler withMandatoryFlag;

	private FloatHandler missingMandatoryFlag;

	private FloatHandlerAdapter adapter;

	@Before
	public void setup() {
		final int attributeId = new Random().nextInt(Integer.MAX_VALUE);

		expectedValue = Float.MAX_VALUE;

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(attributeId)).thenReturn(true);
		when(containingAttribute.getFloat(eq(attributeId), anyFloat())).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(attributeId)).thenReturn(false);
		when(missingAttribute.getFloat(eq(attributeId), anyFloat()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since is't the default
						return invocation.getArgumentAt(1, Float.class);
					}
				});

		withMandatoryFlag = mock(FloatHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(FloatHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

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
	public FloatHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public FloatHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public FloatHandlerAdapter getAdapter() {
		return adapter;
	}
}