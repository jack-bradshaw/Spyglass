package com.matthewtamlin.spyglass.library.call_handler_adapters;

import android.content.res.TypedArray;

import java.lang.annotation.Annotation;

public interface CallHandlerAdapter<A extends Annotation> {
	boolean shouldCallMethod(A annotation, TypedArray attrs);

	public int getAttributeId(A annotation);
}