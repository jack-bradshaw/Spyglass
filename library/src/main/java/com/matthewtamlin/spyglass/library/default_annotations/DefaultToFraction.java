package com.matthewtamlin.spyglass.library.default_annotations;

@Default(annotationClass = DefaultToFraction.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DefaultToFraction {
	float value();
}