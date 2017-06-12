package com.matthewtamlin.spyglass.processor_activated;

import com.google.auto.service.AutoService;
import com.matthewtamlin.spyglass.processor.core.MainProcessor;

import javax.annotation.processing.Processor;

@AutoService(Processor.class)
public class SpyglassProcessor extends MainProcessor {}