package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.DrawableHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.DrawableHandler;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDrawableHandlerAdapter
		extends TestHandlerAdapter<Drawable, DrawableHandler, DrawableHandlerAdapter> {

	private Drawable expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private DrawableHandler withMandatoryFlag;

	private DrawableHandler missingMandatoryFlag;

	private DrawableHandlerAdapter adapter;

	@Before
	public void setup() {
		final int attributeId = new Random().nextInt(Integer.MAX_VALUE);

		expectedValue = mock(Drawable.class);

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(attributeId)).thenReturn(true);
		when(containingAttribute.getDrawable(eq(attributeId))).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(attributeId)).thenReturn(false);
		when(missingAttribute.getDrawable(eq(attributeId))).thenReturn(null);

		withMandatoryFlag = mock(DrawableHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(DrawableHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

		adapter = new DrawableHandlerAdapter();
	}

	@Override
	public Drawable getExpectedValue() {
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
	public DrawableHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public DrawableHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public DrawableHandlerAdapter getAdapter() {
		return adapter;
	}
}