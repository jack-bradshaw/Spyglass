package com.matthewtamlin.spyglass.library.default_annotations;

@Default(annotationClass = DefaultToFractionResource.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DefaultToFractionResource {
	float resId();

	int baseMultiplier() default 1;

	int parentMultiplier() default 1;
}