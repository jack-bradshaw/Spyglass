package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.core.Supplier;

@Default(annotationClass = DefaultToColorSupplier.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DefaultToFractionSupplier {
	Class<? extends Supplier<Float>> value();
}