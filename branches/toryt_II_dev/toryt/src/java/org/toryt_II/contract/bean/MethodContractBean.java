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

package org.toryt_II.contract.bean;


import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.toryt.patterns_I.Assertion;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.lockable.LockableMap;
import org.toryt.util_I.collections.lockable.LockableSet;
import org.toryt.util_I.collections.lockable.SetBackedLockableSet;
import org.toryt_II.contract.AbstractMethodContract;
import org.toryt_II.contract.ContractIsClosedException;
import org.toryt_II.contract.condition.Condition;
import org.toryt_II.contract.condition.ExceptionCondition;


/**
 * Build method contracts JavaBean style. Construct an instance,
 * add conditions, and close.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class MethodContractBean<_Subject_ extends Member>
    extends AbstractMethodContract<_Subject_> {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre subject != null;
   * @post new.getSubject() == subject;
   */
  public MethodContractBean(_Subject_ subject) {
    super(subject);
  }

  /*</construction>*/



  @Override
  public void close() {
    super.close();
    $preconditions.lock();
    $postconditions.lock();
  }



  /*<property name="preconditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public LockableSet<Condition> getPreconditions() {
    if (isClosed()) {
      return $preconditions;
    }
    else {
      @SuppressWarnings("unchecked") LockableSet<Condition> result =
          (LockableSet<Condition>)$preconditions.clone();
      return result;
    }
  }

  /**
   * @pre condition != null;
   * @post getPreconditions().contains(condition);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addPrecondition(Condition condition)
      throws ContractIsClosedException {
    assert condition != null;
    if (isClosed()) {
      throw new ContractIsClosedException(this, condition, "preconditions");
    }
    $preconditions.add(condition);
  }

  /**
   * @invar $preconditions != null;
   * @invar ! $preconditions.isNullAllowed();
   */
  private final SetBackedLockableSet<Condition> $preconditions = new SetBackedLockableSet<Condition>(false);

  /*</property>*/



  /*<property name="postconditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public LockableSet<Condition> getPostconditions() {
    if (isClosed()) {
      return $postconditions;
    }
    else {
      @SuppressWarnings("unchecked") LockableSet<Condition> result =
          (LockableSet<Condition>)$postconditions.clone();
      return result;
    }
  }

  /**
   * @pre condition != null;
   * @post getPostconditions().contains(condition);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addPostcondition(Condition condition)
      throws ContractIsClosedException {
    assert condition != null;
    if (isClosed()) {
      throw new ContractIsClosedException(this, condition, "postconditions");
    }
    $postconditions.add(condition);
  }

  /**
   * @invar $postconditions != null;
   * @invar ! $postconditions.isNullAllowed();
   */
  private final SetBackedLockableSet<Condition> $postconditions = new SetBackedLockableSet<Condition>(false);

  /*</property>*/



  /*<property name="exceptionConditions">*/
  //------------------------------------------------------------------

  /**
   * Normally, according t
   *
   * @basic
   */
  public Map<Class<? extends Throwable>, LockableSet<ExceptionCondition<?>>> getExceptionConditions() {
    //unmodifiable map of unmodifiable sets of exception conditions
    Map<Class<? extends Throwable>, Set<ExceptionCondition<?>>> result =
        new HashMap<Class<? extends Throwable>, Set<ExceptionCondition<?>>>();
    Iterator<Map.Entry<Class<? extends Throwable>, Set<ExceptionCondition<?>>>> iter =
        $exceptionConditions.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry<Class<? extends Throwable>, Set<ExceptionCondition<?>>> entry = iter.next();
      result.put(entry.getKey(), Collections.unmodifiableSet(entry.getValue()));
    }
    return Collections.unmodifiableMap(result);
  }

  /**
   * You can only add {@link ExceptionCondition exception conditions} for exception types
   * that are declared in the signature of the {@link #getSubject() method for which this
   * is a contract} in the <code><strong>throws</strong></code> clause.
   *
   * @pre condition != null;
   * @post (getExceptionConditions().get(condition.getExceptionType()) != null) &&
   *       (getExceptionConditions().get(condition.getExceptionType()).contains(condition));
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addExceptionCondition(ExceptionCondition<?> condition)
      throws ContractIsClosedException, ExceptionTypeNotDeclaredInThrowsClauseException {
    assert condition != null;
    if (isClosed()) {
      throw new ContractIsClosedException(this, condition, "postconditions");
    }
    if (! exceptionTypeDeclaredInThrowsClause(condition.getExceptionType(), getSubject())) {
      throw new ExceptionTypeNotDeclaredInThrowsClauseException(this, condition.getExceptionType());
    }
    Set<ExceptionCondition<?>> exceptionConditionSet =
        $exceptionConditions.get(condition.getExceptionType());
    if (exceptionConditionSet == null) {
      exceptionConditionSet = new HashSet<ExceptionCondition<?>>();
      $exceptionConditions.put(condition.getExceptionType(), exceptionConditionSet);
    }
    exceptionConditionSet.add(condition);
  }

  // MUDO move to reflection
  private boolean exceptionTypeDeclaredInThrowsClause(final Class<? extends Throwable> exceptionType, Member subject) {
    Class<? extends Throwable>[] declaredExceptionType = getExceptionType();
    return isSubtypeOfOneOf(exceptionType, declaredExceptionType);
  }

  protected abstract Class<? extends Throwable>[] getExceptionTypes();

  // MUDO move to reflection
  private boolean isSubtypeOfOneOf(final Class<?> type, Class<?>[] types) {
    return org.toryt.patterns_I.Collections.exists(types,
                                                   new Assertion<Class<?>>() {
                                                        public boolean isTrueFor(Class<?> et) {
                                                          return et.isAssignableFrom(type);
                                                        }
                                                       });
  }

  /**
   * @invar $exceptionConditions != null;
   * @invar Collections.noNull($exceptionConditions);
   * @invar (forall Set s : $exceptionConditions.values() {Collections.noNull(s)});
   */
  private final Map<Class<? extends Throwable>, LockableSet<ExceptionCondition<?>>> $exceptionConditions =
        new Map<Class<? extends Throwable>, LockableSet<ExceptionCondition<?>>>;

  /*</property>*/

}