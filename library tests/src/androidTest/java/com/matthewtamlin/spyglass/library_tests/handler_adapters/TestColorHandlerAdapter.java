package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_adapters.ColorHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.ColorHandler;

import org.junit.Before;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
public class TestColorHandlerAdapter extends TestHandlerAdapter<Integer, ColorHandler,
		ColorHandlerAdapter> {
	private Integer expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private ColorHandler withMandatoryFlag;

	private ColorHandler missingMandatoryFlag;

	private ColorHandlerAdapter adapter;

	@Before
	public void setup() {
		final int attributeId = new Random().nextInt(Integer.MAX_VALUE);

		expectedValue = new Random().nextInt(Integer.MAX_VALUE);

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.getColor(eq(attributeId), anyInt()))
				.thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.getColor(eq(attributeId), anyInt()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since is't the default
						return invocation.getArgumentAt(1, Integer.class);
					}
				});

		withMandatoryFlag = mock(ColorHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(ColorHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

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
	public ColorHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public ColorHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public ColorHandlerAdapter getAdapter() {
		return adapter;
	}
}