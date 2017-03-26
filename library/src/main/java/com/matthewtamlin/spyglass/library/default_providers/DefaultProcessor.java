package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import java.lang.annotation.Annotation;

public interface DefaultProcessor<T, A extends Annotation> {
	public T get(Context context, A annotation);
}