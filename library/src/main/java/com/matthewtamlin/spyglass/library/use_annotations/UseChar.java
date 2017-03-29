package com.matthewtamlin.spyglass.library.use_annotations;

import com.matthewtamlin.spyglass.library.meta_annotations.Use;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Use(adapterClass = UseChar.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface UseChar {
	char value();
}