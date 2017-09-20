package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.use_annotation_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.annotations.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import java.util.ArrayList;
import java.util.List;

public class WithUseNull extends UseAnnotationsTestTargetBase {
	public WithUseNull(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithUseNull(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithUseNull(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public WithUseNull(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@StringHandler(attributeId = R.styleable.UseAnnotationsTestTargetBase_useAnnotationsAttr)
	@DefaultToString("default value")
	public void handlerMethod(
			final String arg0,
			@UseNull final Character arg1,
			@UseNull final Byte arg2,
			@UseNull final Short arg3,
			@UseNull final Integer arg4,
			@UseNull final Long arg5,
			@UseNull final Float arg6,
			@UseNull final Double arg7,
			@UseNull final Object arg8,
			@UseNull final Number arg9,
			@UseNull final String arg10) {

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

		setReceivedValue(ReceivedValue.of(invocationArgs));
	}

	@Override
	public ReceivedValue<List<Object>> getExpectedReceivedValues() {
		final List<Object> expectedArgs = new ArrayList<>();

		expectedArgs.add("default value");

		for (int i = 1; i < 11; i++) {
			expectedArgs.add(null);
		}

		return ReceivedValue.of(expectedArgs);
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithUseNull.class)
				.withStyleableResource(R.styleable.UseAnnotationsTestTargetBase)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.build()
				.passDataToMethods();
	}
}