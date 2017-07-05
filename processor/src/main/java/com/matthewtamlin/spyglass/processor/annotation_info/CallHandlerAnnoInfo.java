package com.matthewtamlin.spyglass.processor.annotation_info;

import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.processor.util.SetUtil;

import java.lang.annotation.Annotation;
import java.util.Set;

public class CallHandlerAnnoInfo {
	public static final Set<Class<? extends Annotation>> ALL_ANNOS = SetUtil.unmodifiableSetOf(
			SpecificEnumHandler.class,
			SpecificFlagHandler.class);
}