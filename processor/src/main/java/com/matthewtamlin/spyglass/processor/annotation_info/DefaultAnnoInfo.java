package com.matthewtamlin.spyglass.processor.annotation_info;

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
import com.matthewtamlin.spyglass.processor.util.SetUtil;

import java.lang.annotation.Annotation;
import java.util.Set;

public class DefaultAnnoInfo {
	public static final Set<Class<? extends Annotation>> ALL_ANNOS = SetUtil.unmodifiableSetOf(
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
			DefaultToTextResource.class);
}