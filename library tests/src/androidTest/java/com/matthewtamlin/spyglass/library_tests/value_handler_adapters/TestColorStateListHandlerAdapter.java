package com.matthewtamlin.spyglass.library_tests.value_handler_adapters;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.value_handler_adapters.ColorStateListHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.ColorStateListHandler;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestColorStateListHandlerAdapter extends TestValueHandlerAdapter<
		ColorStateList,
		ColorStateListHandler,
		ColorStateListHandlerAdapter> {

	private static final int ATTRIBUTE_ID = 4626;

	private ColorStateList expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private ColorStateListHandler withMandatoryFlag;

	private ColorStateListHandler missingMandatoryFlag;

	private ColorStateListHandlerAdapter adapter;

	@Before
	public void setup() {
		expectedValue = mock(ColorStateList.class);

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingAttribute.getColorStateList(eq(ATTRIBUTE_ID))).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getColorStateList(eq(ATTRIBUTE_ID))).thenReturn(null);

		withMandatoryFlag = mock(ColorStateListHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(ColorStateListHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

		adapter = new ColorStateListHandlerAdapter();
	}

	@Override
	public ColorStateList getExpectedValue() {
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
	public ColorStateListHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public ColorStateListHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public ColorStateListHandlerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public int getAttributeId() {
		return ATTRIBUTE_ID;
	}
}