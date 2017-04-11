package com.matthewtamlin.spyglass.library.handler_annotations;

import com.matthewtamlin.spyglass.library.handler_adapters.FlagHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Handler;

@Handler(adapterClass = FlagHandlerAdapter.class)
public @interface FlagHandler {
	int attributeId();

	int handledFlags();
}