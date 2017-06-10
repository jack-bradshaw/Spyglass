package com.matthewtamlin.spyglass.integration_tests.testing_utilities;

import android.app.Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class SynchronousUiThreadExecutor {
	private Activity activity;

	public SynchronousUiThreadExecutor(final Activity activity) {
		this.activity = checkNotNull(activity, "Argument \'activity\' cannot be null.");
	}

	public void execute(final Runnable runnable) {
		final RuntimeException[] exceptionHolder = new RuntimeException[1];

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					runnable.run();
				} catch (final RuntimeException e) {
					exceptionHolder[0] = e;
				}
			}
		});

		getInstrumentation().waitForIdleSync();

		if (exceptionHolder[0] != null) {
			throw exceptionHolder[0];
		}
	}
}