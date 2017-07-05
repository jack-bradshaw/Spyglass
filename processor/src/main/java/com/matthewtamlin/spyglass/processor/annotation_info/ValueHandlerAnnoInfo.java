package com.matthewtamlin.spyglass.processor.annotation_info;

import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.processor.util.SetUtil;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ValueHandlerAnnoInfo {
	public static final Set<Class<? extends Annotation>> ALL_ANNOS = SetUtil.unmodifiableSetOf(
			BooleanHandler.class,
			ColorHandler.class,
			ColorStateListHandler.class,
			DimensionHandler.class,
			DrawableHandler.class,
			EnumConstantHandler.class,
			EnumOrdinalHandler.class,
			FloatHandler.class,
			FractionHandler.class,
			IntegerHandler.class,
			StringHandler.class);
}