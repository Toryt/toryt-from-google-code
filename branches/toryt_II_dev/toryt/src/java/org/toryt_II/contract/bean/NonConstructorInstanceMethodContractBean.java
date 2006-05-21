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


import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.toryt.patterns_I.Assertion;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.algebra.UnionSet;
import org.toryt.util_I.collections.lockable.LockableMap;
import org.toryt.util_I.collections.lockable.LockableSet;
import org.toryt.util_I.collections.lockable.SetBackedLockableSet;
import org.toryt_II.contract.ContractIsClosedException;
import org.toryt_II.contract.MethodContract;
import org.toryt_II.contract.NonConstructorInstanceMethodContract;
import org.toryt_II.contract.TypeContract;
import org.toryt_II.contract.condition.Condition;
import org.toryt_II.contract.condition.ExceptionCondition;


/**
 * Build method contracts JavaBean style. Construct an instance,
 * add conditions, and close.
 *
 * @author Jan Dockx
 *
 * @invar getSuperContracts() != null; but it might be empty
 * @invar ! getSuperContracts().contains(null);
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class NonConstructorInstanceMethodContractBean<_ImplicitArgument_,
                                                      _SuperContract_ extends NonConstructorInstanceMethodContract<?, ?>>
    extends InstanceMethodContractBean<_ImplicitArgument_, Method>
    implements NonConstructorInstanceMethodContract<_ImplicitArgument_, _SuperContract_> {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @param superContracts
   *        If this is <code>null</code> we treat it the same as the empty set.
   * @pre subject != null;
   * @pre Reflection.methodKind(subject).isInstanceMethod();
   * @pre typeContract != null;
   * @pre (superContracts != null) ? ! superContracts.contains(null);
   * @pre (forall _SuperContract_ : sc {sc.isClosed()});
   * @post new.getSubject() == subject;
   * @post new.getTypeContract() == typeContract;
   * @post (superContracts == null) ?
   *         getSuperContracts().isEmpty() :
   *         getSuperContracts().equals(superContracts);
   * @post new.getPostconditions() == Union over supercontracts;
   */
  public NonConstructorInstanceMethodContractBean(Method subject,
                                                  TypeContract<_ImplicitArgument_> typeContract,
                                                  Set<_SuperContract_> superContracts) {
    super(subject, typeContract);
    assert (superContracts != null) ? ! superContracts.contains(null) : true;
    assert org.toryt.patterns_I.Collections.forAll(superContracts,
                                                   new Assertion<_SuperContract_>() {
                                                          public boolean isTrueFor(_SuperContract_ sc) {
                                                            return sc.isClosed();
                                                          }
                                                       });
    if (superContracts == null) {
      @SuppressWarnings("unchecked")
      Set<_SuperContract_> empty = (Set<_SuperContract_>)Collections.EMPTY_SET;
      $directSuperContracts = empty;
    }
    else {
      $directSuperContracts = new HashSet<_SuperContract_>(superContracts);
    }
  }

  /*</construction>*/



  /*<property name="super contracts">*/
  //------------------------------------------------------------------

  /**
   * The contracts of specifications of this method in
   * super types.
   *
   * @basic
   *
   * @mudo is this direct, or all? rename: is direct
   */
  public Set<_SuperContract_> getDirectSuperContracts() {
    return Collections.unmodifiableSet($directSuperContracts);
  }

  /**
   * @invar $superContracts != null;
   */
  private final Set<_SuperContract_> $directSuperContracts;

  /*</property>*/



  /*<property name="preconditions">*/
  //------------------------------------------------------------------

  /**
   * <p>According to the contract paradigm, here we should return the
   *   intersection of preconditions that are {@link #addPrecondition(Condition) added}
   *   to this instance explicitly, and the preconditions of all {@link #getDirectSuperContracts()
   *   super contracts}. Since conditions are actually only code, equality
   *   for them is not defined semantically, and this is not possible.</p>
   * <p>Therefore, we use the following algorithm, that comes down to the fact
   *   that, if preconditions change when overriding a method, or there
   *   is the possibility for ambiguity, you should repeat all preconditions
   *   explicitly.</p>
   * <p>If there is at least one precondition that is {@link #addPrecondition(Condition) added}
   *   to this instance explicitly, we return only the preconditions that are
   *   {@link #addPrecondition(Condition) added} to this
   *   instance explicitly. If there are no preconditions added to this
   *   instance explicitly, we look at the {@link #getDirectSuperContracts() super contracts}.<br/ >
   *   If there is no {@link #getDirectSuperContracts() super contract}, we return the empty
   *   set. If there is more than 1 {@link #getDirectSuperContracts() super contract}, we
   *   return the empty set, because there is the possibility for conflict, and the
   *   user should have completely repeated all applicable preconditions. If there
   *   are no preconditions that were explicitly {@link #addPrecondition(Condition) added}
   *   to this, this implementation has no preconditions. If there is exactly
   *   1 {@link #getDirectSuperContracts() super contract}, we its preconditions.</p>
   *
   * @basic
   */
  @Override
  public final LockableSet<Condition> getPreconditions() {
    LockableSet<Condition> result = super.getPreconditions();  // is locked by super or cloned if needed
    if (result.isEmpty()) {
      if ($directSuperContracts.size() == 1) {
        for (_SuperContract_ sc : $directSuperContracts) {
          /* Since we only access the super contracts map if there is exactly 1 super contract,
           * this depends on the fact that super contracts are direct super contracts.
           * Since we only access the super contract if there is exactly 1 super contract,
           * there is no need for getting the transitive closure of super contracts.
           */
          result = sc.getPreconditions();
        }
      }
      else {
        SetBackedLockableSet<Condition> empty = new SetBackedLockableSet<Condition>(false);
        empty.lock();
        result = empty;
      }
    }
    return result;
  }

  /*</property>*/



  /*<property name="postconditions">*/
  //------------------------------------------------------------------

  /**
   * Returns the union of the postconditions explicitly {@link #addPostcondition(Condition) added}
   * to this instance, and the postconditions of all {@link #getDirectSuperContracts() super contracts}.
   *
   * @basic
   */
  @Override
  public final LockableSet<Condition> getPostconditions() {
    Set<NonConstructorInstanceMethodContract<?, ?>> transitiveClosure = new HashSet<NonConstructorInstanceMethodContract<?, ?>>();
    transitiveClosure(this, transitiveClosure);
    @SuppressWarnings("unchecked") LockableSet<? extends Condition>[] components =
        (LockableSet<? extends Condition>[])new LockableSet<?>[transitiveClosure.size() + 1];
    int i = 0;
    for (NonConstructorInstanceMethodContract<?, ?> c : transitiveClosure) {
      components[i] = c.getPostconditions();
      i++;
    }
    components[i] = super.getPostconditions(); // locked or cloned and locked by super
    return new UnionSet<Condition>(false, components);
  }

  private static void transitiveClosure(NonConstructorInstanceMethodContract<?, ?> c, Set<NonConstructorInstanceMethodContract<?, ?>> acc) {
    if (! acc.contains(c)) {
      acc.add(c);
      for (NonConstructorInstanceMethodContract<?, ?> sc : c.getDirectSuperContracts()) {
        transitiveClosure(sc, acc);
      }
    }
  }

  /*</property>*/



  /*<property name="exceptionConditions">*/
  //------------------------------------------------------------------

  /**
   * <p>According to the contract paradigm, here we should return the
   *   intersection of exception conditions that are {@link #addPrecondition(Condition) added}
   *   to this instance explicitly, and the exception conditions of all {@link #getDirectSuperContracts()
   *   super contracts}. Since conditions are actually only code, equality
   *   for them is not defined semantically, and this is not possible.</p>
   * <p>Therefore, we use the following algorithm, that comes down to the fact
   *   that, if exception conditions for a declared exception type change when overriding a
   *   method, or there is the possibility for ambiguity, you should repeat all exception
   *   conditions for that declared type explicitly.</p>
   * <p>The returned map will only contain a set of exception conditions for each
   *   exception type declared to be thrown in this implementation of this method. If there are
   *   exception types that are declared to be thrown in a supertype, and
   *   those types are not repeated in the signature of this implementation, they
   *   cannot (may not) be thrown by this implementation.<br />
   *   For each of these exception types, if any exception conditions were added explicitly
   *   to this contract for that type, that collection is returned. If there were no exception
   *   conditions added explicitly to this contract for that type, we look at the super contracts.<br />
   *   If there is no {@link #getDirectSuperContracts() super contract}, we return the empty
   *   set for this exception type. If there is more than 1 {@link #getDirectSuperContracts() super contract},
   *   we also return the empty set for this exception type, because there is the possibility for
   *   conflict, and the user should have completely repeated all applicable exception conditions.
   *   Note that an empty set of exception conditions means that the exception can never be thrown.
   *   This is equivalent with not having the exception condition in the method signature at all.
   *   If there is exactly 1 {@link #getDirectSuperContracts() super contract}, its exception
   *   conditions are used for the exception type at hand.</p>
   *
   * @basic
   */
  public final LockableMap<Class<? extends Throwable>, LockableSet<ExceptionCondition<?>>> getExceptionConditions() {
    LockableMap<Class<? extends Throwable>, LockableSet<ExceptionCondition<?>>> result =
        new HashMap<Class<? extends Throwable>, LockableSet<ExceptionCondition<?>>>();
    LockableMap<Class<? extends Throwable>, LockableSet<ExceptionCondition<?>>> superMap =
        super.getExceptionConditions();
    for (Class<? extends Throwable> et : getExceptionTypes()) {
      LockableSet<ExceptionCondition<?>> etConditions = getExceptionConditionsFor(superMap, et);
      result.put(et, etConditions);
    }
    result.lock();
    return result;
  }

  private LockableSet<ExceptionCondition<?>> getExceptionConditionsFor(LockableMap<Class<? extends Throwable>, LockableSet<ExceptionCondition<?>>> superMap,
                                                                       Class<? extends Throwable> exceptionType) {
    LockableSet<ExceptionCondition<?>> result = superMap.get(exceptionType);
        // is locked by super or cloned if needed
    if (result.isEmpty()) {
      if ($directSuperContracts.size() == 1) {
        for (_SuperContract_ sc : $directSuperContracts) {
          /* Since we only access the super contracts map if there is exactly 1 super contract,
           * this depends on the fact that super contracts are direct super contracts.
           * Since we only access the super contract if there is exactly 1 super contract,
           * there is no need for getting the transitive closure of super contracts.
           */
          result = sc.getExceptionConditions().get(exceptionType);
        }
      }
      else {
        SetBackedLockableSet<ExceptionCondition<?>> empty =
            new SetBackedLockableSet<ExceptionCondition<?>>(false);
        empty.lock();
        result = empty;
      }
    }
    return result;
  }

  @Override
  protected Class<? extends Throwable>[] getExceptionTypes() {
    @SuppressWarnings("unchecked") Class<? extends Throwable>[] exceptionTypes =
        (Class<? extends Throwable>[])getSubject().getExceptionTypes();
    return exceptionTypes;
  }

  /*</property>*/

}
