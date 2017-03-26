package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import java.lang.annotation.Annotation;

public interface DefaultProcessor<T, A extends Annotation> {
	public T process(A annotation, Context context);
}