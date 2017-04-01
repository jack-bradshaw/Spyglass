package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToFloatAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Default(adapterClass = DefaultToFloatAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DefaultToFloat {
	float value();
}