package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.use_annotation_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.StringHandler;

import java.util.ArrayList;
import java.util.List;

public class WithUseNumberRequiringConversionToOtherPrimitiveNumberTypes extends UseAnnotationsTestTargetBase {
	public WithUseNumberRequiringConversionToOtherPrimitiveNumberTypes(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithUseNumberRequiringConversionToOtherPrimitiveNumberTypes(final Context context,
			final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithUseNumberRequiringConversionToOtherPrimitiveNumberTypes(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr) {

		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public WithUseNumberRequiringConversionToOtherPrimitiveNumberTypes(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@StringHandler(attributeId = R.styleable.UseAnnotationsTestTargetBase_useAnnotationsAttr)
	@DefaultToString("default value")
	public void handlerMethod(
			final String arg0,
			@UseByte(30) final double arg1,
			@UseDouble(10.5) final float arg2,
			@UseFloat(40.8F) final int arg3,
			@UseInt(9) final long arg4,
			@UseLong(123456789123456789L) final short arg5,
			@UseShort(2) final byte arg6) {

		final List<Object> invocationArgs = new ArrayList<>();

		invocationArgs.add(arg0);
		invocationArgs.add(arg1);
		invocationArgs.add(arg2);
		invocationArgs.add(arg3);
		invocationArgs.add(arg4);
		invocationArgs.add(arg5);
		invocationArgs.add(arg6);

		setReceivedValue(ReceivedValue.of(invocationArgs));
	}

	@Override
	public ReceivedValue<List<Object>> getExpectedReceivedValues() {
		final List<Object> expectedArgs = new ArrayList<>();

		expectedArgs.add("default value");
		expectedArgs.add(30);
		expectedArgs.add(10.5);
		expectedArgs.add(((Float) 40.8F).intValue());
		expectedArgs.add(9);
		expectedArgs.add((short) 123456789123456789L);
		expectedArgs.add(2);

		return ReceivedValue.of(expectedArgs);
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		WithUseNumberRequiringConversionToOtherPrimitiveNumberTypes_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.UseAnnotationsTestTargetBase)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build()
				.passDataToMethods();
	}
}