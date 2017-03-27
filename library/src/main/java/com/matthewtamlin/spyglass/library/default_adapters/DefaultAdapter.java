package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import java.lang.annotation.Annotation;

public interface DefaultAdapter<T, A extends Annotation> {
	public T process(A annotation, Context context);
}