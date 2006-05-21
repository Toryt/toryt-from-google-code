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

package org.toryt_II.contract;


import java.lang.reflect.Member;
import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.lockable.LockableSet;
import org.toryt_II.contract.condition.Condition;
import org.toryt_II.contract.condition.ExceptionCondition;


/**
 * <p>A <dfn>method contract</dfn> instance describes the contract of a
 *   <dfn>declared method</dfn> in an interface or a class. Only those
 *   parts of the contract that are declared in this class, are described
 *   in this instance. Parts of the contract that are inherited, are not
 *   repeated.</p>
 * <p>An instance knows for {@link #getSubject() what method} it is a contract.</p>
 * <p>A method contract consists of
 *   {@link #getPreconditions() preconditions},
 *   {@link #getPostconditions() postconditions} and
 *   {@link #getExceptionConditions() exception conditions}.
 *   These are stored as sets of {@link Condition Conditions}.</p>
 *
 * @mudo legacy
 * During a test of this contract, the test needs to store it's old
 * state before actually invoking the method-under-test. How to do
 * this, is implemented {@link #recordState(Map) in the contract}.
 *
 * @author Jan Dockx
 *
 * @param <_Subject_>
 *        The type of the member that this is a contract for.
 *
 * @invar (getSubjet() instanceof Method) || (getSubject() instanceof Constructor);
 * @invar getSubject().getDeclaringClass() == getTypeContract().getType();
 * @invar getPreconditions() != null;
 * @invar ! getPreconditions().isNullAllowed();
 * @invar getPreconditions().isLocked();
 * @invar getPostconditions() != null;
 * @invar ! getPostconditions().isNullAllowed();
 * @invar getPostconditions().isLocked();
 * @invar getExceptionConditions() != null;
 * @invar ! getExceptionconditions().isNullAllowed();
 * @invar (forall Set s : getExceptionConditions().values() {! s.contains(null)});
 * @invar getExceptionContidions().isLocked();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface MethodContract<_Subject_ extends Member>
    extends Contract<_Subject_> {

  public abstract String[] getFormalParameters();



  /*<property name="preconditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  LockableSet<Condition> getPreconditions();

  /*</property>*/



  /*<property name="postconditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  LockableSet<Condition> getPostconditions();

  /*</property>*/



  /*<property name="exceptionConditions">*/
  //------------------------------------------------------------------

  /**
   * The exception conditions as a map. The map contains sets of
   * {@link ExceptionCondition} instances, with the
   * {@link ExceptionCondition#getExceptionType() exception type}
   * as key.
   * One of the conditions in the set for a given exception type
   * must validate <code>true</code>, not all of them.
   *
   * @basic
   */
  Map<Class<? extends Throwable>, LockableSet<ExceptionCondition<?>>> getExceptionConditions();

  /*</property>*/



  /**
   * Record the old state of the test case (that is the
   * current state of <code>test.getContext()</state> now)
   * also in <code>test.getContext()</code>.
   * The default implementation of this method does nothing.
   *
   * @mudo legacy; move to case
   */
  void recordState(Map<String, Object> context);

}