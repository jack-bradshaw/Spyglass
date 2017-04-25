package com.matthewtamlin.spyglass.annotations.supplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestSupplierInstantiator {
	@Test(expected = IllegalArgumentException.class)
	public void testInstantiateSupplier_nullClass() {
		SupplierInstantiator.instantiateSupplier(null);
	}

	@Test(expected = SupplierInstantiationException.class)
	public void testInstantiateSupplier_classHasPrivateConstructor() {
		SupplierInstantiator.instantiateSupplier(getNonWildcardSupplierWithPrivateConstructor());
	}

	@Test
	public void testInstantiateSupplier_classHasDefaultConstructor() {
		SupplierInstantiator.instantiateSupplier(getNonWildcardSupplierWithDefaultConstructor());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInstantiateWildcardSupplier_nullClass() {
		SupplierInstantiator.instantiateWildcardSupplier(null);
	}

	private Class<? extends Supplier<Object>> getNonWildcardSupplierWithPrivateConstructor() {
		return NonWildcardSupplierPrivateConstructor.class;
	}

	private Class<? extends Supplier<Object>> getNonWildcardSupplierWithDefaultConstructor() {
		return NonWildcardSupplierDefaultConstructor.class;
	}

	public static class NonWildcardSupplierPrivateConstructor implements Supplier<Object> {
		private NonWildcardSupplierPrivateConstructor() {}

		@Override
		public Object get() {
			return null;
		}
	}

	public static class NonWildcardSupplierDefaultConstructor implements Supplier<Object> {
		@Override
		public Object get() {
			return null;
		}
	}
}