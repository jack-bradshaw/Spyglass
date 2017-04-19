package com.matthewtamlin.spyglass.library_tests.value_handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.value_handler_adapters.TextArrayHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.TextArrayHandler;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestTextArrayHandlerAdapter extends TestValueHandlerAdapter<
		CharSequence[],
		TextArrayHandler,
		TextArrayHandlerAdapter> {

	private static final int ATTRIBUTE_ID = 7626;

	private CharSequence[] expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private TextArrayHandler withMandatoryFlag;

	private TextArrayHandler missingMandatoryFlag;

	private TextArrayHandlerAdapter adapter;

	@Before
	public void setup() {
		expectedValue = new CharSequence[]{"something", "something else", ""};

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingAttribute.getTextArray(eq(ATTRIBUTE_ID))).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getTextArray(eq(ATTRIBUTE_ID))).thenReturn(null);

		withMandatoryFlag = mock(TextArrayHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(TextArrayHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
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
	public TextArrayHandlerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public int getAttributeId() {
		return ATTRIBUTE_ID;
	}
}