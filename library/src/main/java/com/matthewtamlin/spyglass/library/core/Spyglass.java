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
 * Translates view attributes into method calls. This class interacts with the UI, so all method
 * calls must be made on the main thread. This class uses the builder pattern for instantiation,
 * see {@link #builder()}.
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
	 * Supplies the data to pass to the target.
	 */
	private TypedArray attrSource;

	/**
	 * Constructs a new Spyglass from a builder.
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
	 * 		if this method is called on a non-UI thread
	 * @throws SpyglassValidationException
	 * 		if a method in the target view has invalid annotations
	 * @throws SpyglassMethodCallException
	 * 		if a method in the target view cannot be called or throws an exception when called
	 */
	public void passDataToMethods() {
		// The spyglass interacts with the UI, so make sure this call is on the main thread
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new IllegalThreadException("Spyglass methods must be called on the UI thread.");
		}

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
	 * Reflectively invokes a call-handler method in the target view using the arguments defined by
	 * the method's annotations.
	 *
	 * @param method
	 * 		the method to invoke, not null, must have a call handler annotation
	 */
	private void processCallHandlerMethod(final Method method) {
		final Annotation handlerAnnotation = getCallHandlerAnnotation(method);
		final CallHandlerAdapter<Annotation> handlerAdapter = getCallHandlerAdapter(method);

		if (handlerAdapter.shouldCallMethod(handlerAnnotation, attrSource)) {
			final TreeMap<Integer, Object> args = new TreeMap<>(getUseAnnotationsValues(method));
			callMethod(method, args.values().toArray());
		}
	}

	/**
	 * Reflectively invokes a value handler method in the target view using the arguments defined
	 * by the method's annotations. If the data source does not contain the desired data and the
	 * method has a default annotation, then the default data is used in the call.
	 *
	 * @param method
	 * 		the method to invoke, not null, must have a call handler annotation
	 */
	private void processValueHandlerMethod(final Method method) {
		final Annotation handlerAnnotation = getValueHandlerAnnotation(method);
		final ValueHandlerAdapter<?, Annotation> handlerAdapter = getValueHandlerAdapter(method);
		final TypedArrayAccessor<?> accessor = handlerAdapter.getAccessor(handlerAnnotation);

		if (accessor.valueExistsInArray(attrSource)) {
			final Object value = accessor.getValueFromArray(attrSource);
			final TreeMap<Integer, Object> args = new TreeMap<>(getUseAnnotationsValues(method));

			addValueAtFirstEmptyPosition(args, value);
			callMethod(method, args.values().toArray());

		} else if (getDefaultAnnotation(method) != null) {
			final DefaultAdapter<?, Annotation> defaultAdapter = getDefaultAdapter(method);
			final Object value = defaultAdapter.getDefault(getDefaultAnnotation(method), context);
			final TreeMap<Integer, Object> args = new TreeMap<>(getUseAnnotationsValues(method));

			addValueAtFirstEmptyPosition(args, value);
			callMethod(method, args.values().toArray());
		}
	}

	/**
	 * Reflectively calls the supplied method in the target view.
	 *
	 * @param method
	 * 		the method to invoke
	 * @param arguments
	 * 		the arguments to pass to the method when invoked, may be null
	 *
	 * @throws SpyglassMethodCallException
	 * 		if the method cannot be called or throws an exception when called
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

	/**
	 * Gets the values specified by the use annotations of a method. The values are returned in a
	 * map, where each value is mapped to the index of the parameter it applies to.
	 *
	 * @param method
	 * 		the method to get the values from
	 *
	 * @return the values
	 */
	private Map<Integer, Object> getUseAnnotationsValues(final Method method) {
		final Map<Integer, Object> args = new HashMap<>();

		final Map<Integer, Annotation> annotations = AnnotationUtil.getUseAnnotations(method);
		final Map<Integer, UseAdapter<?, Annotation>> adapters = AdapterUtil.getUseAdapters(method);

		for (final Integer i : annotations.keySet()) {
			final Object value = adapters.get(i).getValue(annotations.get(i));
			args.put(i, value);
		}

		return args;
	}

	/**
	 * Adds the supplied value to the supplied map. The first index which does not already have a
	 * value (counting from zero) is used as the key.
	 * <p>
	 * For example, if the existing map is [0 = "hello", 2 = "world"], then the value will be
	 * inserted with a key of 1. Alternatively if the existing map is [0 = "hello", 1 = "world"],
	 * then the value will be inserted with a key of 2.
	 *
	 * @param map
	 * 		a map of values
	 * @param value
	 * 		the value to add to the map
	 */
	private void addValueAtFirstEmptyPosition(final Map<Integer, Object> map, final Object value) {
		// Use size + 1 so to handle the case where the existing values have consecutive keys
		for (int i = 0; i < map.size() + 1; i++) {
			if (!map.containsKey(i)) {
				map.put(i, value);
				break;
			}
		}
	}

	/**
	 * @return a new spyglass builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builds new instances of the spyglass tool. Attempting to call {@link #build()} without
	 * first setting the target, the context and the styleable resource will result in an exception
	 * being thrown.
	 */
	public static class Builder {
		/**
		 * The target to use in the spyglass when built. This property is mandatory and must be
		 * non-null prior to calling {@link #build()}.
		 */
		private View target;

		/**
		 * The context to use in the spyglass when built. This property is mandatory and must be
		 * non-null prior to calling {@link #build()}.
		 */
		private Context context;

		/**
		 * The styleable resource to use in the spyglass when built. This property is mandatory
		 * and must be non-null prior to calling {@link #build()}.
		 */
		private int styleableRes[];

		/**
		 * The attribute set to use in the spyglass when built. This property is optional and
		 * does not need to be changed prior to calling {@link #build()}.
		 */
		private AttributeSet attributeSet;

		/**
		 * The resource ID of the attribute set to use in the spyglass when built. This property is
		 * optional and does not need to be changed prior to calling {@link #build()}.
		 */
		private int defStyleAttr;

		private int defStyleRes;

		private Builder() {}

		/**
		 * Sets the target to pass data to when the spyglass is used. If this method is called more
		 * than once, only the most recent value is used. This method must be called with a
		 * non-null value prior to calling {@link #build()}.
		 *
		 * @param view
		 * 		the target to pass data to
		 *
		 * @return this builder
		 */
		public Builder withTarget(final View view) {
			this.target = view;
			return this;
		}

		/**
		 * Sets the context to source resource information from. If this method is called more
		 * than once, only the most recent value is used. This method must be called with a
		 * non-null value prior to calling {@link #build()}.
		 *
		 * @param context
		 * 		the context to source resource information from
		 *
		 * @return this builder
		 */
		public Builder withContext(final Context context) {
			this.context = context;
			return this;
		}

		/**
		 * Sets the styleable resource to use when interpreting attribute data. The behaviour of the
		 * spyglass is undefined if the styleable resource is not applicable to the target
		 * view. If this method is called more than once, only the most recent value is used.
		 * This method must be called with a non-null value prior to calling {@link #build()}.
		 *
		 * @param styleableRes
		 * 		the styleable resource to use when interpreting attribute data
		 *
		 * @return this builder
		 */
		public Builder withStyleableResource(final int[] styleableRes) {
			this.styleableRes = styleableRes;
			return this;
		}

		/**
		 * Sets the attribute set to source data from. If this method is called more than once,
		 * only the most recent value is used. An attribute set is not mandatory, and
		 * {@link #build()} can safely be called without ever calling this method.
		 *
		 * @param attributeSet
		 * 		the attribute set to source data from, may be null
		 *
		 * @return this builder
		 */
		public Builder withAttributeSet(final AttributeSet attributeSet) {
			this.attributeSet = attributeSet;
			return this;
		}

		/**
		 * Sets the style to source defaults from if the attribute set is missing data. The data
		 * in the attribute set takes precedence, so the data in the default style is only used if
		 * the attribute set is missing data for a particular attribute. If this method is called
		 * more than once, only the most recent value is used. This value is not mandatory, and
		 * {@link #build()} can safely be called without ever calling this method.
		 *
		 * @param defStyleAttr
		 * 		an attribute in the current theme which references the default style, 0 to use no
		 * 		default style
		 *
		 * @return this builder
		 */
		public Builder withDefStyleAttr(final int defStyleAttr) {
			this.defStyleAttr = defStyleAttr;
			return this;
		}

		/**
		 * Sets the style to source defaults from if the attribute set is missing data. The data
		 * in the attribute set takes precedence, so the data in the default style is only used if
		 * the attribute set is missing data for a particular attribute. If this method is called
		 * more than once, only the most recent value is used. This value is not mandatory, and
		 * {@link #build()} can safely be called without ever calling this method.
		 *
		 * @param defStyleRes
		 * 		the resource ID of the default style, 0 to use no default style
		 *
		 * @return this builder
		 */
		public Builder withDefStyleRes(final int defStyleRes) {
			this.defStyleRes = defStyleRes;
			return this;
		}

		/**
		 * Constructs a new spyglass using this builder. Attempting to call this method without
		 * first setting the target, the context and the styleable resource will result in an
		 * exception being thrown.
		 *
		 * @return the new spyglass
		 *
		 * @throws InvalidBuilderStateException
		 * 		if no target has been set
		 * @throws InvalidBuilderStateException
		 * 		if no context has been set
		 * @throws InvalidBuilderStateException
		 * 		if no styleable resource has been set
		 */
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