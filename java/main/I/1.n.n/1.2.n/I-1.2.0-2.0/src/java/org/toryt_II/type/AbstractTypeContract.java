package org.toryt_II.type;


import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.toryt.support.Reflection;
import org.toryt.support.straightlist.ConcatStraightList;
import org.toryt.support.straightlist.StraightList;
import org.toryt_II.AbstractContract;
import org.toryt_II.Condition;
import org.toryt_II.TorytException;
import org.toryt_II.method.ClassMethodContract;
import org.toryt_II.method.InstanceMethodContract;


/**
 * The contract of a method. This features preconditions, postconditions
 * and exception conditions.
 */
public abstract class AbstractTypeContract
    extends AbstractContract
    implements TypeContract {

  /*<section name="Meta Information">*/
  //  ------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /*</section>*/

  
  
  /**
   * @pre type != null;
   * @post new.getType() == type;
   */
  protected AbstractTypeContract(Class type) {
    try {
      addExtraTests(new AllMembersCoveredTest(this));
    }
    catch (TorytException e) {
      assert false : "we should not be closed";
    }
    assert type != null;
    $type = type;
  }
  
  /**
   * @throws TorytException
   * @pre fqn != null;
   * @post new.getType().getName().equals(fqn);
   */
  protected AbstractTypeContract(String fqn) throws TorytException {
    this(Reflection.loadForName(fqn));
  }
  
  public final Class getType() {
    return $type;
  }
  
  private Class $type;

  
  
  /*<property name="type invariant conditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final Set getTypeInvariantConditions() {
    return Collections.unmodifiableSet($typeInvariantConditions);
  }
  
  /**
   * @pre condition != null;
   * @post getTypeInvariantConditions().contains(condition);
   * @throws TorytException
   *         isClosed();
   */
  public void addTypeInvariantCondition(Condition condition) throws TorytException {
    assert condition != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    $typeInvariantConditions.add(condition);
  }
  
  /**
   * @invar $typeInvariantConditions != null;
   * @invar ! $typeInvariantConditions.contains(null);
   * @invar (forall Object o; $typeInvariantConditions.contains(o); o instanceof Condition);
   */
  private Set $typeInvariantConditions = new HashSet();
  
  /*</property>*/

  
  public StraightList getMethodTests() throws TorytException {
    StraightList[] lists
        = new StraightList[getInstanceMethodContracts().size()
                           + getClassMethodContracts().size()
                           + getNestedClassContracts().size()];
    Iterator iter = getInstanceMethodContracts().iterator();
    int i = 0;
    while (iter.hasNext()) {
      InstanceMethodContract imc = (InstanceMethodContract)iter.next();
      lists[i] = imc.getMethodTests();
      i++;
    }
    iter = getClassMethodContracts().iterator();
    while (iter.hasNext()) {
      ClassMethodContract cmc = (ClassMethodContract)iter.next();
      lists[i] = cmc.getMethodTests();
      i++;
    }
    iter = getNestedClassContracts().iterator();
    while (iter.hasNext()) {
      ClassContract ncc = (ClassContract)iter.next();
      lists[i] = ncc.getMethodTests();
      i++;
    }
    return new ConcatStraightList(lists);
  }

  public Set getSubContracts() {
    return Collections.unmodifiableSet(typeSubContracts());
  }
  
  protected final Set typeSubContracts() {
    Set result = new HashSet();
    result.addAll(getClassMethodContracts());
    result.addAll(getInstanceMethodContracts());
    result.addAll(getNestedClassContracts());
    return result;
  }
  
}