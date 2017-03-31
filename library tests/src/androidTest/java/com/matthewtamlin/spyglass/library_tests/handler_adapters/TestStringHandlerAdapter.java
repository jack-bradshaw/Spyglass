package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.StringHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.StringHandler;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestStringHandlerAdapter extends TestHandlerAdapter<String, StringHandler,
		StringHandlerAdapter> {
	private String expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private StringHandler withMandatoryFlag;

	private StringHandler missingMandatoryFlag;

	private StringHandlerAdapter adapter;

	@Before
	public void setup() {
		final int attributeId = new Random().nextInt(Integer.MAX_VALUE);

		expectedValue = "Some String";

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(attributeId)).thenReturn(true);
		when(containingAttribute.getString(eq(attributeId))).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(attributeId)).thenReturn(false);
		when(missingAttribute.getString(eq(attributeId))).thenReturn(null);

		withMandatoryFlag = mock(StringHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(StringHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

		adapter = new StringHandlerAdapter();
	}

	@Override
	public String getExpectedValue() {
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
	public StringHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public StringHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public StringHandlerAdapter getAdapter() {
		return adapter;
	}
}