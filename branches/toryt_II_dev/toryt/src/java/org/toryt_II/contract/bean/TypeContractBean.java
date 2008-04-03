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


import static org.toryt.util_I.reflect.MethodKind.INSTANCE_INSPECTOR;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.lockable.LockableSet;
import org.toryt.util_I.collections.lockable.SetBackedLockableSet;
import org.toryt.util_I.reflect.Methods;
import org.toryt_II.contract.AbstractContract;
import org.toryt_II.contract.ContractIsClosedException;
import org.toryt_II.contract.InterfaceContract;
import org.toryt_II.contract.TypeContract;
import org.toryt_II.contract.condition.Condition;


/**
 * Build type contracts JavaBean style. Construct an instance,
 * add conditions, and close.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class TypeContractBean<_Type_>
    extends AbstractContract<Class<_Type_>>
    implements TypeContract<_Type_> {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre subject != null;
   * @post new.getSubject() == subject;
   */
  public TypeContractBean(Class<_Type_> subject) {
    super(subject);
  }

  /*</construction>*/



  @Override
  public void close() {
    super.close();
    $declaredInstanceInvariantConditions.lock();
    $declaredSuperInterfaceContracts.lock();
  }



  /*<section name="super type contracts">*/
  //------------------------------------------------------------------

  public final LockableSet<InterfaceContract<? super _Type_>> getDeclaredSuperInterfaceContracts() {
    if (isClosed()) {
      return $declaredSuperInterfaceContracts;
    }
    else {
      @SuppressWarnings("unchecked") LockableSet<InterfaceContract<? super _Type_>> result =
          (LockableSet<InterfaceContract<? super _Type_>>)$declaredSuperInterfaceContracts.clone();
      return result;
    }
  }

  public final Set<InterfaceContract<? super _Type_>> getSuperInterfaceContracts() {
    Set<InterfaceContract<? super _Type_>> result = $superInterfaceContractsCache;
    if (result == null) {
      result = new HashSet<InterfaceContract<? super _Type_>>();
      for (InterfaceContract<? super _Type_> ic : $declaredSuperInterfaceContracts) {
        result.add(ic);
        result.addAll(ic.getSuperInterfaceContracts());
      }
      if (isClosed()) { // cache
        $superInterfaceContractsCache = result;
      }
    }
    return result;
  }

  /**
   * @pre interfaceContract != null;
   * @pre interfaceContract.isClosed();
   * @post getDirectSuperInterfaceContracts().contains(interfaceContract);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addDeclaredSuperInterfaceContract(InterfaceContract<? super _Type_> interfaceContract)
      throws ContractIsClosedException {
    assert interfaceContract != null;
    assert interfaceContract.isClosed();
    if (isClosed()) {
      throw new ContractIsClosedException(this, interfaceContract, "direct super interface contracts");
    }
    $declaredSuperInterfaceContracts.add(interfaceContract);
  }

  /**
   * @invar $declaredSuperInterfaceContracts != null;
   * @invar ! $declaredSuperInterfaceContracts.isNullAllowed();
   */
  private final SetBackedLockableSet<InterfaceContract<? super _Type_>> $declaredSuperInterfaceContracts =
      new SetBackedLockableSet<InterfaceContract<? super _Type_>>(false);

  /**
   * @invar ! isClosed() ? $superInterfaceContractsCache == null;
   */
  private Set<InterfaceContract<? super _Type_>> $superInterfaceContractsCache;

  /*</section>*/



  /*<section name="instance invariant conditions">*/
  //------------------------------------------------------------------

  public final LockableSet<Condition> getDeclaredInstanceInvariantConditions() {
    if (isClosed()) {
      return $declaredInstanceInvariantConditions;
    }
    else {
      @SuppressWarnings("unchecked") LockableSet<Condition> result =
          (LockableSet<Condition>)$declaredInstanceInvariantConditions.clone();
      return result;
    }
  }

  public final Set<Condition> getInstanceInvariantConditions() {
    Set<Condition> result = $instanceInvariantConditionsCache;
    if (result == null) {
      result = new HashSet<Condition>();
      result.addAll(getDeclaredInstanceInvariantConditions());
      for (InterfaceContract<? super _Type_> ic : $declaredSuperInterfaceContracts) {
        result.addAll(ic.getInstanceInvariantConditions());
      }
      if (isClosed()) { // cache
        $instanceInvariantConditionsCache = result;
      }
    }
    return result;

  }

  /**
   * @pre condition != null;
   * @post getInstanceInvariantConditions().contains(condition);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addDeclaredInstanceInvariantCondition(Condition condition)
      throws ContractIsClosedException {
    assert condition != null;
    if (isClosed()) {
      throw new ContractIsClosedException(this, condition, "instance invariant conditions");
    }
    $declaredInstanceInvariantConditions.add(condition);
  }

  /**
   * @invar $declaredInstanceInvariantConditions != null;
   * @invar ! $declaredInstanceInvariantConditions.isNullAllowed();
   */
  private final SetBackedLockableSet<Condition> $declaredInstanceInvariantConditions =
      new SetBackedLockableSet<Condition>(false);

  /**
   * @invar ! isClosed() ? $instanceInvariantConditionsCache == null;
   */
  private Set<Condition> $instanceInvariantConditionsCache;

  /*</section>*/



  /*<property name="basic instance inspectors">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final LockableSet<Method> getBasicInstanceInspectors() {
    if (isClosed()) {
      return $basicInstanceInspectors;
    }
    else {
      @SuppressWarnings("unchecked") LockableSet<Method> result =
          (LockableSet<Method>)$basicInstanceInspectors.clone();
      return result;
    }
  }

  /**
   * @pre basicInspector != null;
   * @pre Methods.methodKind(inspector) == INSTANCE_INSPECTOR;
   * @post getBasicInstanceInspectors().contains(basicInspector);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addBasicInstanceInspector(Method basicInspector)
      throws ContractIsClosedException {
    assert basicInspector != null;
    assert Methods.methodKind(basicInspector) == INSTANCE_INSPECTOR;
    if (isClosed()) {
      throw new ContractIsClosedException(this, basicInspector, "basic instance inspectors");
    }
    $basicInstanceInspectors.add(basicInspector);
  }

  /**
   * @invar $basicInstanceInspectors != null;
   * @invar ! $basicInstanceInspectors.isNullAllowed();
   */
  private final SetBackedLockableSet<Method> $basicInstanceInspectors =
      new SetBackedLockableSet<Method>(false);

  /*</property>*/

}