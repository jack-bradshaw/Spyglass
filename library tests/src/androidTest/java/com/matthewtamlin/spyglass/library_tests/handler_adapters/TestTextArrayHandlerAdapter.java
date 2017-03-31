package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.TextArrayHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.TextHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.TextArrayHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.TextHandler;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestTextArrayHandlerAdapter
		extends TestHandlerAdapter<CharSequence[], TextArrayHandler, TextArrayHandlerAdapter> {
	private CharSequence[] expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private TextArrayHandler withMandatoryFlag;

	private TextArrayHandler missingMandatoryFlag;

	private TextArrayHandlerAdapter adapter;

	@Before
	public void setup() {
		final int attributeId = new Random().nextInt(Integer.MAX_VALUE);

		expectedValue = new CharSequence[]{"something", "something else", ""};

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(attributeId)).thenReturn(true);
		when(containingAttribute.getTextArray(eq(attributeId))).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(attributeId)).thenReturn(false);
		when(missingAttribute.getTextArray(eq(attributeId))).thenReturn(null);

		withMandatoryFlag = mock(TextArrayHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(TextArrayHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

		adapter = new TextArrayHandlerAdapter();
	}

	@Override
	public CharSequence[] getExpectedValue() {
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
	public TextArrayHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public TextArrayHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public TextArrayHandlerAdapter getAdapter() {
		return adapter;
	}
}
