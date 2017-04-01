package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.BooleanHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.DimensionHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.DimensionHandler;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestDimensionHandlerAdapter extends TestHandlerAdapter<
		Float,
		DimensionHandler,
		DimensionHandlerAdapter> {

	private Float expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private DimensionHandler withMandatoryFlag;

	private DimensionHandler missingMandatoryFlag;

	private DimensionHandlerAdapter adapter;

	@Before
	public void setup() {
		final int attributeId = new Random().nextInt(Integer.MAX_VALUE);

		expectedValue = Float.NEGATIVE_INFINITY;

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(attributeId)).thenReturn(true);
		when(containingAttribute.getDimension(eq(attributeId), anyFloat()))
				.thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(attributeId)).thenReturn(false);
		when(missingAttribute.getDimension(eq(attributeId), anyFloat()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since is't the default
						return invocation.getArgumentAt(1, Float.class);
					}
				});

		withMandatoryFlag = mock(DimensionHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(DimensionHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

		adapter = new DimensionHandlerAdapter();
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
	public DimensionHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public DimensionHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public DimensionHandlerAdapter getAdapter() {
		return adapter;
	}
}