package com.matthewtamlin.spyglass.library.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.call_handler_adapters.CallHandlerAdapter;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.util.AdapterUtil;
import com.matthewtamlin.spyglass.library.util.AnnotationUtil;
import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter.TypedArrayAccessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.library.util.AdapterUtil.getCallHandlerAdapter;
import static com.matthewtamlin.spyglass.library.util.AdapterUtil.getDefaultAdapter;
import static com.matthewtamlin.spyglass.library.util.AdapterUtil.getValueHandlerAdapter;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getCallHandlerAnnotation;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getDefaultAnnotation;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getValueHandlerAnnotation;
import static com.matthewtamlin.spyglass.library.util.ValidationUtil.validateMethod;

@Tested(testMethod = "automated")
public class Spyglass {
	private View view;

	private Context context;

	private TypedArray attrSource;

	private Spyglass(final Builder builder) {
		this.view = builder.view;

		this.context = builder.context;

		this.attrSource = context.obtainStyledAttributes(
				builder.attributeSet,
				builder.styleableRes,
				builder.defStyleAttr,
				builder.defStyleRes);
	}

	public void passDataToMethods() {
		checkMainThread();

		for (final Method m : view.getClass().getDeclaredMethods()) {
			validateMethod(m);
			processMethod(m);
		}
	}

	private void checkMainThread() {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new IllegalThreadException("Spyglasses must only be touched by the UI thread.");
		}
	}

	private void processMethod(final Method method) {
		if (getValueHandlerAnnotation(method) != null) {
			processMethodWithValueHandler(method);
		} else if (getCallHandlerAnnotation(method) != null) {
			processMethodWithCallHandler(method);
		}
	}

	private void processMethodWithCallHandler(final Method method) {
		final Annotation handlerAnnotation = getCallHandlerAnnotation(method);
		final CallHandlerAdapter<Annotation> handlerAdapter = getCallHandlerAdapter(method);

		if (handlerAdapter.shouldCallMethod(handlerAnnotation, attrSource)) {
			final TreeMap<Integer, Object> args = new TreeMap<>(getArgsFromUseAnnotations(method));
			callMethod(method, args.values().toArray());
		}
	}

	private void processMethodWithValueHandler(final Method method) {
		final Annotation handlerAnnotation = getValueHandlerAnnotation(method);
		final ValueHandlerAdapter<?, Annotation> handlerAdapter = getValueHandlerAdapter(method);
		final TypedArrayAccessor<?> accessor = handlerAdapter.getAccessor(handlerAnnotation);

		if (accessor.valueExistsInArray(attrSource)) {
			final Object value = accessor.getValueFromArray(attrSource);
			final TreeMap<Integer, Object> args = new TreeMap<>(getArgsFromUseAnnotations(method));

			addValueAtFirstEmptyPosition(args, value);
			callMethod(method, args.values().toArray());

		} else if (getDefaultAnnotation(method) != null) {
			final DefaultAdapter<?, Annotation> defaultAdapter = getDefaultAdapter(method);
			final Object value = defaultAdapter.getDefault(getDefaultAnnotation(method), context);
			final TreeMap<Integer, Object> args = new TreeMap<>(getArgsFromUseAnnotations(method));

			addValueAtFirstEmptyPosition(args, value);
			callMethod(method, args.values().toArray());
		}
	}

	private void callMethod(final Method method, Object[] arguments) {
		try {
			method.setAccessible(true);
			method.invoke(view, arguments);
		} catch (final Exception e) {
			final String message = "Failed to call method %1$s with arguments %2$s.";
			throw new SpyglassMethodCallException(
					String.format(message, message, Arrays.toString(arguments)),
					e);
		}
	}

	private Map<Integer, Object> getArgsFromUseAnnotations(final Method method) {
		final Map<Integer, Object> args = new HashMap<>();

		final Map<Integer, Annotation> annotations = AnnotationUtil.getUseAnnotations(method);
		final Map<Integer, UseAdapter<?, Annotation>> adapters = AdapterUtil.getUseAdapters(method);

		for (final Integer i : annotations.keySet()) {
			final Object value = adapters.get(i).getValue(annotations.get(i));
			args.put(i, value);
		}

		return args;
	}

	private void addValueAtFirstEmptyPosition(final Map<Integer, Object> args, final Object value) {
		// Use size + 1 so to handle the case where the existing values have consecutive keys
		// For example, [1 = a, 2 = b, 3 = c] would become [1 = a, 2 = b, 3 = c, 4 = value]
		for (int i = 0; i < args.size() + 1; i++) {
			if (!args.containsKey(i)) {
				args.put(i, value);
				break;
			}
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private View view;

		private Context context;

		private int styleableRes[];

		private AttributeSet attributeSet;

		private int defStyleAttr;

		private int defStyleRes;

		private Builder() {}

		public Builder withView(final View view) {
			this.view = view;
			return this;
		}

		public Builder withContext(final Context context) {
			this.context = context;
			return this;
		}

		public Builder withStyleableResource(final int[] styleableRes) {
			this.styleableRes = styleableRes;
			return this;
		}

		public Builder withAttributeSet(final AttributeSet attributeSet) {
			this.attributeSet = attributeSet;
			return this;
		}

		public Builder withDefStyleAttr(final int defStyleAttr) {
			this.defStyleAttr = defStyleAttr;
			return this;
		}

		public Builder withDefStyleRes(final int defStyleRes) {
			this.defStyleRes = defStyleRes;
			return this;
		}

		public Spyglass build() {
			checkNotNull(view, new InvalidBuilderStateException("Unable to build a Spyglass " +
					"without a view. Call method forView(View) before calling build()."));

			checkNotNull(context, new InvalidBuilderStateException("Unable to build a Spyglass " +
					"without a context. Call method withContext(Context) before calling build()."));

			checkNotNull(styleableRes, new InvalidBuilderStateException("Unable to build a " +
					"Spyglass without a styleable resource. Call method withStyleableRes(int[]) " +
					"before calling build()."));

			return new Spyglass(this);
		}
	}
}