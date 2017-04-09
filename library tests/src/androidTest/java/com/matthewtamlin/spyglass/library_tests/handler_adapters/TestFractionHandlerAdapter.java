package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.FractionHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.FractionHandler;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestFractionHandlerAdapter extends TestHandlerAdapter<Float, FractionHandler,
		FractionHandlerAdapter> {
	private static final int ATTRIBUTE_ID = 9927;

	private Float expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private FractionHandler withMandatoryFlag;

	private FractionHandler missingMandatoryFlag;

	private FractionHandlerAdapter adapter;

	@Before
	public void setup() {
		final int baseValue = 10;
		final int multiplier = 2;
		final int parentMultiplier = 5;
		expectedValue = (float) baseValue * multiplier;

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingAttribute.getFraction(
				eq(ATTRIBUTE_ID),
				eq(multiplier),
				eq(parentMultiplier),
				anyFloat()))
				.thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getFraction(
				eq(ATTRIBUTE_ID),
				anyInt(),
				anyInt(),
				anyFloat()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since it's the default
						return invocation.getArgumentAt(3, Float.class);
					}
				});

		withMandatoryFlag = mock(FractionHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(withMandatoryFlag.baseMultiplier()).thenReturn(multiplier);
		when(withMandatoryFlag.parentMultiplier()).thenReturn(parentMultiplier);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(FractionHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(withMandatoryFlag.baseMultiplier()).thenReturn(multiplier);
		when(withMandatoryFlag.parentMultiplier()).thenReturn(parentMultiplier);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

		adapter = new FractionHandlerAdapter();
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
	public FractionHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public FractionHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public FractionHandlerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public int getAttributeId() {
		return ATTRIBUTE_ID;
	}
}