/*
 * Copyright 2018 Matthew David Tamlin
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

import dagger.BindsInstance;
import dagger.Component;

import javax.annotation.processing.ProcessingEnvironment;

@Component(modules = CoreModule.class)
public interface CoreComponent {
  public void inject(MainProcessor mainProcessor);
  
  @Component.Builder
  public interface Builder {
    @BindsInstance
    public Builder setProcessingEnvironment(ProcessingEnvironment processingEnvironment);
    
    public Builder setCoreModule(CoreModule coreModule);
    
    public CoreComponent build();
  }
}