package com.matthewtamlin.spyglass.library.use_adapters;

import java.lang.annotation.Annotation;

public interface UseAdapter<T, A extends Annotation> {
	public T getValue(A annotation);
}