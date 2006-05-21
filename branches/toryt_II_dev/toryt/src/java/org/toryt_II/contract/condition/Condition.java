/*<license>
Copyright 2006 - $Date$ by the authors mentioned below.

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

package org.toryt_II.contract.condition;


import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>A <code>Condition</code> is used to validate boolean expressions on a
 *   method execution context (a {@link Map} that contains the
 *   test case, old values and the execution result) during a method test.
 *   Conditions are used as preconditions, postconditions, and type invariants.
 *   {@link ExceptionCondition Exception conditions} use a subtype.</p>
 * <p>The class of the implicit argument for which this
 *   condition applies is given as a generic parameter. The implicit argument is
 *   stored in the <code>context</code> with key {@link org.toryt_II.testcase.TestCaseLabels#THIS}.
// *   ,
// *   and can be retrieved with the correct static type using
// *   @link #implicitArgument(Map)}.
 *   For class methods, the implicit argument
 *   is the type instance. For instance methods, the implicit argument is the
 *   <code>this</code> reference.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface Condition {

  /**
   * Validate a context.
   *
   * @pre context != null;
   * @pre context.containsKey(org.toryt_II.testcase.Labels.THIS);
   * @pre context.get(org.toryt_II.testcase.Labels.THIS) != null;
   * @pre context.get(org.toryt_II.testcase.Labels.THIS) instanceof _ImplicitArgument_;
   */
  boolean validate(Map<String, Object> context);

//  /**
//   * Retrieve the implicit argument from the context.
//   *
//   * @pre context != null;
//   * @pre context.containsKey(org.toryt_II.testcase.Labels.THIS);
//   * @pre context.get(org.toryt_II.testcase.Labels.THIS) != null;
//   * @pre context.get(org.toryt_II.testcase.Labels.THIS) instanceof _ImplicitArgument_;
//   * @todo This might be a good reason for declaring a generic subtype of Map: to
//   *       have generic type control on the type of the implicit argument and the type
//   *       of the result. This isn't possible for the type of the exception, because
//   *       there can be many possible exceptions.
//   * @return context.get(org.toryt_II.testcase.Labels.THIS);
//   * @result result != null;
//   */
//  public final _ImplicitArgument_ implicitArgument(Map<String, Object> context) {
//    @SuppressWarnings("unchecked") _ImplicitArgument_ implicitArgument =
//        (_ImplicitArgument_)context.get(THIS);
//    return implicitArgument;
//  }

}