package com.matthewtamlin.spyglass.library.handler_processors;

import android.content.res.TypedArray;

import java.lang.annotation.Annotation;

public interface AttributeProcessor<T, A extends Annotation> {
	public boolean attributeValueIsAvailable(TypedArray attrs, A annotation);

	public T getAttributeValue(TypedArray attrs, A annotation);

	public boolean attributeIsMandatory(A annotation);
}