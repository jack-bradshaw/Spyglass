package com.matthewtamlin.spyglass.integration_tests.test_inheritance_behaviour;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;

import java.util.HashMap;
import java.util.Map;

public class Superclass extends View {
	public static final String DEFAULT_VALUE = "superclass value";

	private Map<Integer, Object> invocationRecord = null;

	public Superclass(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public Superclass(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public Superclass(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public Superclass(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@StringHandler(attributeId = R.styleable.Superclass_TestAttr)
	@DefaultToString(DEFAULT_VALUE)
	public void handlerMethod(final String s) {
		final Map<Integer, Object> invocationRecord = new HashMap<>();

		invocationRecord.put(0, s);

		this.invocationRecord = invocationRecord;
	}

	public Map<Integer, Object> getSuperclassInvocationRecord() {
		return invocationRecord;
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(Superclass.class)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.withStyleableResource(R.styleable.Superclass)
				.build()
				.passDataToMethods();
	}
}