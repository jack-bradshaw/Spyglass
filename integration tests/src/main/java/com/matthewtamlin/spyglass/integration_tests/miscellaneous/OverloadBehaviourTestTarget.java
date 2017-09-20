package com.matthewtamlin.spyglass.integration_tests.miscellaneous;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.annotations.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class OverloadBehaviourTestTarget extends View {
	private ReceivedValue<String> positiveReceivedValue = ReceivedValue.none();

	private ReceivedValue<String> negativeReceivedValue = ReceivedValue.none();

	public OverloadBehaviourTestTarget(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public OverloadBehaviourTestTarget(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public OverloadBehaviourTestTarget(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public OverloadBehaviourTestTarget(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@StringHandler(attributeId = R.styleable.OverloadBehaviourTestTarget_overloadAttr)
	public void handlerMethodWithAnnotation(final String arg0) {
		positiveReceivedValue = ReceivedValue.of(arg0);
	}

	public void handlerMethodWithoutAnnoation(final String arg0) {
		negativeReceivedValue = ReceivedValue.of(arg0);
	}

	public ReceivedValue<String> getPositiveReceivedValue() {
		return positiveReceivedValue;
	}

	public ReceivedValue<String> getNegativeReceivedValue() {
		return negativeReceivedValue;
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(OverloadBehaviourTestTarget.class)
				.withStyleableResource(R.styleable.OverloadBehaviourTestTarget)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.build()
				.passDataToMethods();
	}
}