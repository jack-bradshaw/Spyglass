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

package com.matthewtamlin.spyglass.processor.core;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

public class CompanionNamer {
	public static String getCompanionNameFor(final TypeElement targetClass) {
		return getParentChain(targetClass) + "_SpyglassCompanion";
	}

	private static String getParentChain(final TypeElement targetClass) {
		// if input is top level class return it
		// otherwise return the parent chain plus it

		if (targetClass.getNestingKind() == NestingKind.TOP_LEVEL) {
			return targetClass.getSimpleName().toString();
		} else {
			final Element parent = targetClass.getEnclosingElement();

			if (parent.getKind() != ElementKind.CLASS) {
				throw new RuntimeException("Cannot create parent chain. Non-class parent found.");
			}

			return (getParentChain((TypeElement) parent)) + "_" + targetClass.getSimpleName().toString();
		}
	}
}