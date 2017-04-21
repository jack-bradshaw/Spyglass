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
import com.matthewtamlin.spyglass.library.util.SpyglassValidationException;
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

/**
 * Translates view attributes into method calls using annotations. This class interacts with the
 * UI, so all method calls must be made on the UI thread.
 */
@Tested(testMethod = "automated")
public class Spyglass {
	/**
	 * The target to pass data to via reflective method calls.
	 */
	private View target;

	/**
	 * A context which provides access to system resources.
	 */
	private Context context;

	/**
	 * The source of the data to pass to the target.
	 */
	private TypedArray attrSource;

	/**
	 * Constructs a new Spyglass using a builder.
	 *
	 * @param builder
	 * 		the builder to use as the base for the spyglass
	 */
	private Spyglass(final Builder builder) {
		this.target = builder.target;

		this.context = builder.context;

		this.attrSource = context.obtainStyledAttributes(
				builder.attributeSet,
				builder.styleableRes,
				builder.defStyleAttr,
				builder.defStyleRes);
	}

	/**
	 * Passes data to the target view using its method annotations. Methods are validated prior to
	 * use, to ensure that annotations have been applied correctly. This method will fail if
	 * called on a non-UI thread.
	 *
	 * @throws IllegalThreadException
	 * 		if this method is called on any non-UI thread
	 * @throws SpyglassValidationException
	 * 		if a method in the target view is found to have invalid annotations
	 * @throws SpyglassMethodCallException
	 * 		if a method in the target view cannot be called or throws an exception when called
	 */
	public void passDataToMethods() {
		checkMainThread("Spyglass methods must be called on the UI thread.");

		for (final Method m : target.getClass().getDeclaredMethods()) {
			validateMethod(m);

			if (getValueHandlerAnnotation(m) != null) {
				processValueHandlerMethod(m);
			} else if (getCallHandlerAnnotation(m) != null) {
				processCallHandlerMethod(m);
			}
		}
	}

	/**
	 * Checks if the calling thread is the main thread.
	 *
	 * @throws IllegalThreadException
	 * 		if the calling thread is not the main thread
	 */
	private void checkMainThread(final String message) {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new IllegalThreadException(message);
		}
	}

	/**
	 * Reflectively invokes a call handler method using the arguments defined by the method's
	 * annotations.
	 *
	 * @param method
	 * 		the method to process, not null, must have a call handler annotation
	 */
	private void processCallHandlerMethod(final Method method) {
		final Annotation handlerAnnotation = getCallHandlerAnnotation(method);
		final CallHandlerAdapter<Annotation> handlerAdapter = getCallHandlerAdapter(method);

		if (handlerAdapter.shouldCallMethod(handlerAnnotation, attrSource)) {
			final TreeMap<Integer, Object> args = new TreeMap<>(getArgsFromUseAnnotations(method));
			callMethod(method, args.values().toArray());
		}
	}

	/**
	 * Reflectively invokes a value handler method using the arguments defined by the method's
	 * annotations. If the data source does not contain the desired data and the method specifies
	 * a default, then the default data is used.
	 *
	 * @param method
	 * 		the method to process, not null, must have a call handler annotation
	 */
	private void processValueHandlerMethod(final Method method) {
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

	/**
	 * Invokes the supplied method using the supplied arguments. The call may fail when executed,
	 * but no validation occurs prior to execution.
	 *
	 * @param method
	 * 		the method to invoke
	 * @param arguments
	 * 		the arguments to pass to the method
	 */
	private void callMethod(final Method method, Object[] arguments) {
		try {
			method.setAccessible(true);
			method.invoke(target, arguments);
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
		private View target;

		private Context context;

		private int styleableRes[];

		private AttributeSet attributeSet;

		private int defStyleAttr;

		private int defStyleRes;

		private Builder() {}

		public Builder withTarget(final View view) {
			this.target = view;
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
			checkNotNull(target, new InvalidBuilderStateException("Unable to build a Spyglass " +
					"without a target. Call method withTarget(View) before calling build()."));

			checkNotNull(context, new InvalidBuilderStateException("Unable to build a Spyglass " +
					"without a context. Call method withContext(Context) before calling build()."));

			checkNotNull(styleableRes, new InvalidBuilderStateException("Unable to build a " +
					"Spyglass without a styleable resource. Call method withStyleableRes(int[]) " +
					"before calling build()."));

			return new Spyglass(this);
		}
	}
}