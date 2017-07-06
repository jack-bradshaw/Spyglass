package com.matthewtamlin.spyglass.processor.core;

import com.matthewtamlin.spyglass.processor.mirror_utils.AnnotationMirrorHelper;
import com.matthewtamlin.spyglass.processor.mirror_utils.TypeMirrorHelper;

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