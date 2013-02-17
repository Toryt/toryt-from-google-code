package org.toryt;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
      addExtraTests(new AllMembersCoveredTest());
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
    this(loadForName(fqn));
  }
  
  private static Class loadForName(String fqn) throws TorytException {
    assert fqn != null;
    try {
      return Class.forName(fqn);
    }
    catch (ClassNotFoundException e) {
      throw new TorytException(null, e);
    }
  }
  
  public final Class getType() {
    return $type;
  }
  
  private Class $type;

  
  
  /*<property name="preconditions">*/
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

  
  public void validateTypeInvariants(Object subject, MethodTest test) {
    Map subjectContext = new HashMap();
    subjectContext.put(MethodContract.SUBJECT_KEY, subject);
    Iterator iter = getTypeInvariantConditions().iterator();
    while (iter.hasNext()) {
      Condition c = (Condition)iter.next();
      test.validate(c.validate(subjectContext));
    }
  }

  public final List getMethodTests() throws TorytException {
    // MUDO optimize list?
    List result = new ArrayList();
    Iterator i = getInstanceMethodContracts().iterator();
    while (i.hasNext()) {
      InstanceMethodContract imc = (InstanceMethodContract)i.next();
      result.addAll(imc.getMethodTests());
    }
    i = getClassMethodContracts().iterator();
    while (i.hasNext()) {
      ClassMethodContract cmc = (ClassMethodContract)i.next();
      result.addAll(cmc.getMethodTests());
    }
    i = getNestedClassContracts().iterator();
    while (i.hasNext()) {
      ClassContract cc = (ClassContract)i.next();
      result.addAll(cc.getMethodTests());
    }
    return result;
  }
  
}