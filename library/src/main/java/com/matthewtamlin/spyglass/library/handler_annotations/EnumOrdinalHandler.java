package com.matthewtamlin.spyglass.library.handler_annotations;

import com.matthewtamlin.spyglass.library.handler_adapters.EnumOrdinalHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Handler;


@Handler(adapterClass = EnumOrdinalHandlerAdapter.class)
public @interface EnumOrdinalHandler {
	int attributeId();

	boolean mandatory() default false;
}