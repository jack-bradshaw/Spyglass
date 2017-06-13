package com.matthewtamlin.spyglass.integration_tests.inheritance_behaviour_test_target;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;

import java.util.ArrayList;
import java.util.List;

public class Subclass extends Superclass {
	private List<Object> invocationArgs = null;

	public Subclass(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public Subclass(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public Subclass(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public Subclass(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@StringHandler(attributeId = R.styleable.Subclass_TestAttr2)
	@DefaultToString("subclass default value")
	public void subclassHandlerMethod(final String s) {
		final List<Object> invocationRecord = new ArrayList<>();

		invocationRecord.add(s);

		this.invocationArgs = invocationRecord;
	}

	public List<Object> getSubclassActualInvocationArgs() {
		return invocationArgs;
	}

	public List<Object> getSubclassExpectedInvocationArgs() {
		final List<Object> expectedArgs = new ArrayList<>();

		expectedArgs.add("subclass default value");

		return expectedArgs;
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(Subclass.class)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.withStyleableResource(R.styleable.Subclass)
				.build()
				.passDataToMethods();
	}
}