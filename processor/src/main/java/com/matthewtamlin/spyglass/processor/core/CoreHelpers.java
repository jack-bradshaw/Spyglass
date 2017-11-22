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

import com.matthewtamlin.spyglass.processor.mirror_helpers.AnnotationMirrorHelper;
import com.matthewtamlin.spyglass.processor.mirror_helpers.TypeMirrorHelper;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class CoreHelpers {
	private Elements elementHelper;

	private Types typeHelper;

	private TypeMirrorHelper typeMirrorHelper;

	private AnnotationMirrorHelper annotationMirrorHelper;

	public CoreHelpers(final Elements elementHelper, final Types typeHelper) {
		this.elementHelper = checkNotNull(elementHelper, "Argument \'elementHelper\' cannot be null.");
		this.typeHelper = checkNotNull(typeHelper, "Argument \'typeHelper\' cannot be null.");

		typeMirrorHelper = new TypeMirrorHelper(elementHelper, typeHelper);
		annotationMirrorHelper = new AnnotationMirrorHelper(elementHelper);
	}

	public Elements getElementHelper() {
		return elementHelper;
	}

	public Types getTypeHelper() {
		return typeHelper;
	}

	public TypeMirrorHelper getTypeMirrorHelper() {
		return typeMirrorHelper;
	}

	public AnnotationMirrorHelper getAnnotationMirrorHelper() {
		return annotationMirrorHelper;
	}
}