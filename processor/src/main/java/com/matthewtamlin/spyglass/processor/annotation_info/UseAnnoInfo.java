package com.matthewtamlin.spyglass.processor.annotation_info;

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
import com.matthewtamlin.spyglass.processor.util.SetUtil;

import java.lang.annotation.Annotation;
import java.util.Set;

public class UseAnnoInfo {
	public static final Set<Class<? extends Annotation>> ALL_ANNOS = SetUtil.unmodifiableSetOf(
			UseBoolean.class,
			UseByte.class,
			UseChar.class,
			UseDouble.class,
			UseFloat.class,
			UseInt.class,
			UseLong.class,
			UseNull.class,
			UseShort.class,
			UseString.class);
}