package com.matthewtamlin.spyglass.library_tests.value_handler_adapters;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.value_handler_adapters.DrawableHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.DrawableHandler;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestDrawableHandlerAdapter extends TestValueHandlerAdapter<
		Drawable,
		DrawableHandler,
		DrawableHandlerAdapter> {

	private static final int ATTRIBUTE_ID = 5728;

	private Drawable expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private DrawableHandler annotation;

	private DrawableHandlerAdapter adapter;

	@Before
	public void setup() {
		expectedValue = mock(Drawable.class);

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingAttribute.getDrawable(eq(ATTRIBUTE_ID))).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getDrawable(eq(ATTRIBUTE_ID))).thenReturn(null);

		annotation = mock(DrawableHandler.class);
		when(annotation.attributeId()).thenReturn(ATTRIBUTE_ID);

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
	public DrawableHandler getAnnotation() {
		return annotation;
	}

	@Override
	public DrawableHandlerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public int getAttributeId() {
		return ATTRIBUTE_ID;
	}
}