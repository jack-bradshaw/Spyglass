package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_adapters.BooleanHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.BooleanHandler;

import org.junit.Before;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
public class TestBooleanHandlerAdapter extends TestHandlerAdapter<Boolean, BooleanHandler,
		BooleanHandlerAdapter> {
	private Boolean expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private BooleanHandler withMandatoryFlag;

	private BooleanHandler missingMandatoryFlag;

	private BooleanHandlerAdapter adapter;

	@Before
	public void setup() {
		final int attributeId = new Random().nextInt(Integer.MAX_VALUE);

		expectedValue = Boolean.TRUE;

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(attributeId)).thenReturn(true);
		when(containingAttribute.getBoolean(eq(attributeId), anyBoolean()))
				.thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(attributeId)).thenReturn(false);
		when(missingAttribute.getBoolean(eq(attributeId), anyBoolean()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since is't the default
						return invocation.getArgumentAt(1, Boolean.class);
					}
				});

		withMandatoryFlag = mock(BooleanHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(BooleanHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

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
	public BooleanHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public BooleanHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public BooleanHandlerAdapter getAdapter() {
		return adapter;
	}
}