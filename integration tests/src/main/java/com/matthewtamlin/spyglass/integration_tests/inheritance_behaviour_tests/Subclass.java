package com.matthewtamlin.spyglass.integration_tests.inheritance_behaviour_tests;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class Subclass extends Superclass {
	public static final String EXPECTED_VALUE = "subclass expected value";

	private ReceivedValue<String> receivedValue = ReceivedValue.none();

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

	@StringHandler(attributeId = R.styleable.Subclass_subclassAttr)
	@DefaultToString(EXPECTED_VALUE)
	public void subclassHandlerMethod(final String s) {
		receivedValue = ReceivedValue.of(s);
	}

	public ReceivedValue<String> getSubclassReceivedValue() {
		return receivedValue;
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(Subclass.class)
				.withStyleableResource(R.styleable.Subclass)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.build()
				.passDataToMethods();
	}
}