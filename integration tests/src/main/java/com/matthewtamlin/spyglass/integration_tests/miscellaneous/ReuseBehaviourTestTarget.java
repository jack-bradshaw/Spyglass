package com.matthewtamlin.spyglass.integration_tests.miscellaneous;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.StringHandler;

public class ReuseBehaviourTestTarget extends View {
	public ReuseBehaviourTestTarget(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public ReuseBehaviourTestTarget(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public ReuseBehaviourTestTarget(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public ReuseBehaviourTestTarget(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@StringHandler(attributeId = R.styleable.ReuseBehaviourTestTarget_reuseAttr)
	public void handlerMethodWithAnnotation(final String arg0) {
		// Should never be called
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		final ReuseBehaviourTestTarget_SpyglassCompanion companion = ReuseBehaviourTestTarget_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.ReuseBehaviourTestTarget)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build();

		companion.passDataToMethods();
		companion.passDataToMethods();
	}
}