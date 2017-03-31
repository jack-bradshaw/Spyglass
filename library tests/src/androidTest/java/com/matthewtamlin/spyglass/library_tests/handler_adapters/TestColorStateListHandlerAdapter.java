package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.ColorStateListHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.ColorStateListHandler;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestColorStateListHandlerAdapter extends TestHandlerAdapter<
		ColorStateList,
		ColorStateListHandler,
		ColorStateListHandlerAdapter> {

	private ColorStateList expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private ColorStateListHandler withMandatoryFlag;

	private ColorStateListHandler missingMandatoryFlag;

	private ColorStateListHandlerAdapter adapter;

	@Before
	public void setup() {
		final int attributeId = new Random().nextInt(Integer.MAX_VALUE);

		expectedValue = mock(ColorStateList.class);

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(attributeId)).thenReturn(true);
		when(containingAttribute.getColorStateList(eq(attributeId))).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(attributeId)).thenReturn(false);
		when(missingAttribute.getColorStateList(eq(attributeId))).thenReturn(null);

		withMandatoryFlag = mock(ColorStateListHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(ColorStateListHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(attributeId);
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
}