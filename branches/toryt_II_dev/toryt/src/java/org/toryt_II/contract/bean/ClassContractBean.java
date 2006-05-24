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


import static org.toryt.util_I.reflect.Reflection.MethodKind.CLASS_INSPECTOR;

import java.lang.reflect.Method;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.lockable.LockableSet;
import org.toryt.util_I.collections.lockable.SetBackedLockableSet;
import org.toryt.util_I.reflect.Reflection;
import org.toryt_II.contract.ClassContract;
import org.toryt_II.contract.ContractIsClosedException;
import org.toryt_II.contract.condition.Condition;


/**
 * Build type contracts JavaBean style. Construct an instance,
 * add conditions, and close.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ClassContractBean<_Class_> extends TypeContractBean<_Class_>
    implements ClassContract<_Class_> {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre subject != null;
   * @pre ! subject.isInterface();
   * @post new.getSubject() == subject;
   */
  public ClassContractBean(Class<_Class_> subject) {
    super(subject);
    assert ! subject.isInterface();
  }

  /*</construction>*/



  @Override
  public void close() {
    super.close();
    $classInvariantConditions.lock();
    $basicClassInspectors.lock();
  }



  /*<property name="direct super class contract">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final ClassContract<? super _Class_> getDeclaredSuperClassContract() {
    return $directSuperClassContract;
  }

  /**
   * @pre (getSubject() == Object.class) ?
   *        directSuperClassContract == null : directSuperClassContract != null;
   * @pre (getSubject() != Object.class) ?
   *         directSuperClassContract.getSubject() == getSubject().getSuperclass();
   * @pre directSuperClassContract != null ? directSuperClassContract.isClosed();
   * @post new.getDirectSuperClassContract() == directSuperClassContract;
   */
  public final void setDirectSuperClassContract(ClassContract<? super _Class_> directSuperClassContract) {
    assert (getSubject() == Object.class) ?
             directSuperClassContract == null : directSuperClassContract != null;
    assert (getSubject() != Object.class) ?
             directSuperClassContract.getSubject() == getSubject().getSuperclass() : true;
    assert directSuperClassContract != null ? directSuperClassContract.isClosed() : true;
    $directSuperClassContract = directSuperClassContract;
  }

  private ClassContract<? super _Class_> $directSuperClassContract;

  /*</property>*/



  /*<property name="class invariant conditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final LockableSet<Condition> getDeclaredClassInvariantConditions() {
    if (isClosed()) {
      return $classInvariantConditions;
    }
    else {
      @SuppressWarnings("unchecked") LockableSet<Condition> result =
          (LockableSet<Condition>)$classInvariantConditions.clone();
      return result;
    }
  }

  /**
   * @pre condition != null;
   * @post getClassInvariantConditions().contains(condition);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addClassInvariantCondition(Condition condition)
      throws ContractIsClosedException {
    assert condition != null;
    if (isClosed()) {
      throw new ContractIsClosedException(this, condition, "class invariant conditions");
    }
    $classInvariantConditions.add(condition);
  }

  /**
   * @invar $classInvariantConditions != null;
   * @invar ! $classInvariantConditions.isNullAllowed();
   */
  private final SetBackedLockableSet<Condition> $classInvariantConditions = new SetBackedLockableSet<Condition>(false);

  /*</property>*/



  /*<property name="basic class inspectors">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final LockableSet<Method> getBasicClassInspectors() {
    if (isClosed()) {
      return $basicClassInspectors;
    }
    else {
      @SuppressWarnings("unchecked") LockableSet<Method> result =
          (LockableSet<Method>)$basicClassInspectors.clone();
      return result;
    }
  }

  /**
   * @pre basicInspector != null;
   * @pre Reflection.methodKind(inspector) == INSTANCE_INSPECTOR;
   * @post getBasicClassInspectors().contains(basicInspector);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public final void addBasicClassInspector(Method basicInspector)
      throws ContractIsClosedException {
    assert basicInspector != null;
    assert Reflection.methodKind(basicInspector) == CLASS_INSPECTOR;
    if (isClosed()) {
      throw new ContractIsClosedException(this, basicInspector, "basic class inspectors");
    }
    $basicClassInspectors.add(basicInspector);
  }

  /**
   * @invar $basicClassInspectors != null;
   * @invar ! $basicClassInspectors.isNullAllowed();
   */
  private final SetBackedLockableSet<Method> $basicClassInspectors =
      new SetBackedLockableSet<Method>(false);

  /*</property>*/

}