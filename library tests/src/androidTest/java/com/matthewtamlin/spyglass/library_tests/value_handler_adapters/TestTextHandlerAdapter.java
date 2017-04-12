package com.matthewtamlin.spyglass.library_tests.value_handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.value_handler_adapters.TextHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.TextHandler;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestTextHandlerAdapter extends TestValueHandlerAdapter<
		CharSequence,
		TextHandler,
		TextHandlerAdapter> {

	private static final int ATTRIBUTE_ID = 1593;

	private CharSequence expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private TextHandler withMandatoryFlag;

	private TextHandler missingMandatoryFlag;

	private TextHandlerAdapter adapter;

	@Before
	public void setup() {
		expectedValue = "something";

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingAttribute.getText(eq(ATTRIBUTE_ID))).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getText(eq(ATTRIBUTE_ID))).thenReturn(null);

		withMandatoryFlag = mock(TextHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(TextHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

		adapter = new TextHandlerAdapter();
	}

	@Override
	public CharSequence getExpectedValue() {
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
	public TextHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public TextHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public TextHandlerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public int getAttributeId() {
		return ATTRIBUTE_ID;
	}
}