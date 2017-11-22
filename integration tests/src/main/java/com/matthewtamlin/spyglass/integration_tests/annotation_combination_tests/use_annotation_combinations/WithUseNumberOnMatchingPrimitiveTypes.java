/*
 * Copyright 2017 Matthew David Tamlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.use_annotation_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.StringHandler;

import java.util.ArrayList;
import java.util.List;

public class WithUseNumberOnMatchingPrimitiveTypes extends UseAnnotationsTestTargetBase {
	public WithUseNumberOnMatchingPrimitiveTypes(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithUseNumberOnMatchingPrimitiveTypes(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithUseNumberOnMatchingPrimitiveTypes(final Context context, final AttributeSet attrs,
			final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public WithUseNumberOnMatchingPrimitiveTypes(
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
			@UseBoolean(true) final boolean arg1,
			@UseByte(30) final byte arg2,
			@UseChar('\u1003') final char arg3,
			@UseDouble(10.5) final double arg4,
			@UseFloat(40.8F) final float arg5,
			@UseInt(9) final int arg6,
			@UseLong(123456789123456789L) final long arg7,
			@UseShort(2) final short arg8) {

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

		setReceivedValue(ReceivedValue.of(invocationArgs));
	}

	@Override
	public ReceivedValue<List<Object>> getExpectedReceivedValues() {
		final List<Object> expectedArgs = new ArrayList<>();

		expectedArgs.add("default value");
		expectedArgs.add(true);
		expectedArgs.add(30);
		expectedArgs.add('\u1003');
		expectedArgs.add(10.5);
		expectedArgs.add(40.8F);
		expectedArgs.add(9);
		expectedArgs.add(123456789123456789L);
		expectedArgs.add(2);

		return ReceivedValue.of(expectedArgs);
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		WithUseNumberOnMatchingPrimitiveTypes_SpyglassCompanion
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