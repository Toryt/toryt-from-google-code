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


import static org.toryt.util_I.reflect.Reflection.MethodKind.INSTANCE_INSPECTOR;

import java.lang.reflect.Method;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.lockable.LockableSet;
import org.toryt.util_I.collections.lockable.SetBackedLockableSet;
import org.toryt.util_I.reflect.Reflection;
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
    $instanceInvariantConditions.lock();
    $directSuperInterfaceContracts.lock();
  }



  /*<property name="postconditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final LockableSet<InterfaceContract<? super _Type_>> getDirectSuperInterfaceContracts() {
    if (isClosed()) {
      return $directSuperInterfaceContracts;
    }
    else {
      @SuppressWarnings("unchecked") LockableSet<InterfaceContract<? super _Type_>> result =
          (LockableSet<InterfaceContract<? super _Type_>>)$directSuperInterfaceContracts.clone();
      return result;
    }
  }

  /**
   * @pre condition != null;
   * @post getDirectSuperInterfaceContracts().contains(condition);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addDirectSuperInterfaceContract(InterfaceContract<? super _Type_> interfaceContract)
      throws ContractIsClosedException {
    assert interfaceContract != null;
    if (isClosed()) {
      throw new ContractIsClosedException(this, interfaceContract, "direct super interface contracts");
    }
    $directSuperInterfaceContracts.add(interfaceContract);
  }

  /**
   * @invar $directSuperInterfaceContracts != null;
   * @invar ! $directSuperInterfaceContracts.isNullAllowed();
   */
  private final SetBackedLockableSet<InterfaceContract<? super _Type_>> $directSuperInterfaceContracts =
      new SetBackedLockableSet<InterfaceContract<? super _Type_>>(false);

  /*</property>*/



  /*<property name="instance invariant conditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final LockableSet<Condition> getInstanceInvariantConditions() {
    if (isClosed()) {
      return $instanceInvariantConditions;
    }
    else {
      @SuppressWarnings("unchecked") LockableSet<Condition> result =
          (LockableSet<Condition>)$instanceInvariantConditions.clone();
      return result;
    }
  }

  /**
   * @pre condition != null;
   * @post getInstanceInvariantConditions().contains(condition);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addInstanceInvariantCondition(Condition condition)
      throws ContractIsClosedException {
    assert condition != null;
    if (isClosed()) {
      throw new ContractIsClosedException(this, condition, "instance invariant conditions");
    }
    $instanceInvariantConditions.add(condition);
  }

  /**
   * @invar $instanceInvariantConditions != null;
   * @invar ! $instanceInvariantConditions.isNullAllowed();
   */
  private final SetBackedLockableSet<Condition> $instanceInvariantConditions = new SetBackedLockableSet<Condition>(false);

  /*</property>*/



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
   * @pre Reflection.methodKind(inspector) == INSTANCE_INSPECTOR;
   * @post getBasicInstanceInspectors().contains(basicInspector);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addBasicInstanceInspector(Method basicInspector)
      throws ContractIsClosedException {
    assert basicInspector != null;
    assert Reflection.methodKind(basicInspector) == INSTANCE_INSPECTOR;
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