package com.matthewtamlin.spyglass.processor.core;

import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToTextResource;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseString;
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
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.TextArrayHandler;

import java.lang.annotation.Annotation;
import java.util.Set;

import static com.matthewtamlin.spyglass.processor.util.SetUtil.unmodifiableSetOf;

public class AnnotationRegistry {
	public static final Set<Class<? extends Annotation>> CALL_HANDLER_ANNOTATIONS = unmodifiableSetOf(
			SpecificEnumHandler.class,
			SpecificFlagHandler.class
	);

	public static final Set<Class<? extends Annotation>> VALUE_HANDLER_ANNOTATIONS = unmodifiableSetOf(
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
			StringHandler.class,
			TextArrayHandler.class
	);

	public static final Set<Class<? extends Annotation>> DEFAULT_ANNOTATIONS = unmodifiableSetOf(
			DefaultToBoolean.class,
			DefaultToBooleanResource.class,
			DefaultToColorResource.class,
			DefaultToColorStateListResource.class,
			DefaultToDimension.class,
			DefaultToDimensionResource.class,
			DefaultToDrawableResource.class,
			DefaultToEnumConstant.class,
			DefaultToFloat.class,
			DefaultToFractionResource.class,
			DefaultToInteger.class,
			DefaultToIntegerResource.class,
			DefaultToNull.class,
			DefaultToString.class,
			DefaultToStringResource.class,
			DefaultToTextArrayResource.class,
			DefaultToTextResource.class
	);

	public static final Set<Class<? extends Annotation>> USE_ANNOTATIONS = unmodifiableSetOf(
			UseBoolean.class,
			UseByte.class,
			UseChar.class,
			UseDouble.class,
			UseFloat.class,
			UseInt.class,
			UseLong.class,
			UseNull.class,
			UseShort.class,
			UseString.class
	);

	private AnnotationRegistry() {
		throw new RuntimeException("Constants class. Do not instantiate.");
	}
}