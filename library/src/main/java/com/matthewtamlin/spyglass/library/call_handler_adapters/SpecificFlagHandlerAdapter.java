package com.matthewtamlin.spyglass.library.call_handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.call_handler_annotations.SpecificFlagHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with SpecificFlagHandler annotations.
 */
@Tested(testMethod = "automated")
public class SpecificFlagHandlerAdapter implements CallHandlerAdapter<SpecificFlagHandler> {
	@Override
	public boolean shouldCallMethod(final SpecificFlagHandler annotation, final TypedArray attrs) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");

		if (arrayContainsValue(attrs, annotation.attributeId())) {
			final int handledFlags = annotation.handledFlags();
			final int foundFlags = attrs.getInt(annotation.attributeId(), 0);

			// Return true if at least one flag bit matches
			return (foundFlags & handledFlags) > 0;
		} else {
			return false;
		}
	}

	/**
	 * Determines whether the supplied set of attributes contains an integer value for the
	 * supplied attribute.
	 *
	 * @param attrs
	 * 		a set of attribute to check, not null
	 * @param attrId
	 * 		the resource ID of the attribute to check for
	 *
	 * @return true if a value exists, false otherwise
	 */
	private static boolean arrayContainsValue(final TypedArray attrs, final int attrId) {
		// Compare two different results to see if the default is consistently returned
		final int reading1 = attrs.getInt(attrId, 0);
		final int reading2 = attrs.getInt(attrId, 1);

		return !((reading1 == 0) && (reading2 == 1));
	}
}