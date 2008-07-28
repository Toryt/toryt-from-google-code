/*<license>
Copyright 2008 - $Date$ by Jan Dockx.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</license>*/

package org.toryt.util_I.reflect;


import java.lang.reflect.Method;


/**
 * The different kinds of methods that can be instances of
 * {@link Method}.
 *
 * @todo move to ppwcode util reflection
 */
public enum MethodKind {

  INSTANCE_MUTATOR,
  INSTANCE_INSPECTOR,
  CLASS_MUTATOR,
  CLASS_INSPECTOR;

  /**
   * @return (this == INSTANCE_INSPECTOR) || (this == INSTANCE_MUTATOR);
   */
  public final boolean isInstanceMethod() {
    return (this == INSTANCE_INSPECTOR) || (this == INSTANCE_MUTATOR);
  }

  /**
   * @return (this == CLASS_INSPECTOR) || (this == CLASS_MUTATOR);
   */
  public final boolean isClassMethod() {
    return (this == CLASS_INSPECTOR) || (this == CLASS_MUTATOR);
  }

  /**
   * @return (this == INSTANCE_MUTATOR) || (this == CLASS_MUTATOR);
   */
  public final boolean isMutator() {
    return (this == INSTANCE_MUTATOR) || (this == CLASS_MUTATOR);
  }

  /**
   * @return (this == INSTANCE_INSPECTOR) || (this == CLASS_INSPECTOR);
   */
  public final boolean isInspector() {
    return (this == INSTANCE_INSPECTOR) || (this == CLASS_INSPECTOR);
  }

}
