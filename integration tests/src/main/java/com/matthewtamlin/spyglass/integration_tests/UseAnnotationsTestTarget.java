package com.matthewtamlin.spyglass.integration_tests;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseString;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import java.util.ArrayList;
import java.util.List;

public class UseAnnotationsTestTarget extends View {
	private ReceivedValue<List<Object>> receivedValue = ReceivedValue.none();

	public UseAnnotationsTestTarget(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public UseAnnotationsTestTarget(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public UseAnnotationsTestTarget(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public UseAnnotationsTestTarget(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@StringHandler(attributeId = R.styleable.UseAnnotationsTestTarget_useAnnotationsAttr)
	@DefaultToString("default value")
	public void handlerMethod(
			final String arg0,
			@UseBoolean(true) final boolean arg1,
			@UseByte(30) final byte arg2,
			@UseChar('\u1003') final char arg3,
			@UseDouble(10.5) final double arg4,
			@UseFloat(40.8F) final float arg5,
			@UseInt(9) final int arg6,
			@UseLong(123456789123456789L) final long arg7,
			@UseNull final Object arg8,
			@UseShort(2) final short arg9,
			@UseString("used string") final String arg10) {

		final List<Object> invocationArgs = new ArrayList<>();

		invocationArgs.add(arg0);
		invocationArgs.add(arg1);
		invocationArgs.add(arg2);
		invocationArgs.add(arg3);
		invocationArgs.add(arg4);
		invocationArgs.add(arg5);
		invocationArgs.add(arg6);
		invocationArgs.add(arg7);
		invocationArgs.add(arg8);
		invocationArgs.add(arg9);
		invocationArgs.add(arg10);

		receivedValue = ReceivedValue.of(invocationArgs);
	}

	public ReceivedValue<List<Object>> getReceivedValue() {
		return receivedValue;
	}

	public ReceivedValue<List<Object>> getUseAnnotationValues() {
		final List<Object> expectedArgs = new ArrayList<>();

		expectedArgs.add("default value");
		expectedArgs.add(true);
		expectedArgs.add((byte) 30);
		expectedArgs.add('\u1003');
		expectedArgs.add(10.5);
		expectedArgs.add(40.8F);
		expectedArgs.add(9);
		expectedArgs.add(123456789123456789L);
		expectedArgs.add(null);
		expectedArgs.add((short) 2);
		expectedArgs.add("used string");

		return ReceivedValue.of(expectedArgs);
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(UseAnnotationsTestTarget.class)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.withStyleableResource(R.styleable.UseAnnotationsTestTarget)
				.build()
				.passDataToMethods();
	}
}