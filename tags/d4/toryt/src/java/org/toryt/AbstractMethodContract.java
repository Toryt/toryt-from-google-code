package org.toryt;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;


/**
 * Implementation of most methods of {@link MethodContract}.
 * 
 * @author Jan Dockx
 */
public abstract class AbstractMethodContract extends AbstractContract
  implements MethodContract {

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
   * @pre typeContract != null;
   */
  public AbstractMethodContract(TypeContract typeContract) {
    assert typeContract != null;
    $typeContract = typeContract;
  }
  
  public TypeContract getTypeContract() {
    return $typeContract;
  }

  /**
   * @invar $typeContract != null;
   */
  private TypeContract $typeContract;
  
  public final List getMethodTests() throws TorytException {
    // MUDO this order must become priority order
    List testCases = getTestCases();
    List result = new ArrayList(testCases.size());
    ListIterator iterCases = testCases.listIterator();
    while (iterCases.hasNext()) {
      Map testCase = (Map)iterCases.next();
      MethodTest test = createMethodTest(testCase);
      result.add(test);
    }
    return result;
  }

  public String[] getFormalParameters() {
    return new String[0];
  }
  
  /**
   * {@inheritDoc}
   * The default implementation of this method does nothing.
   */
  public void recordState(MethodTest test) {
    // NOP
  }
  
  /**
   * {@inheritDoc}
   * The default implementation of this method returns
   * <code>true</code>. This means that all test cases
   * will be used in the test.
   */
  public final boolean validatePreconditions(MethodTest test) {
    Iterator iter = getPreconditions().iterator();
    while (iter.hasNext()) {
      Condition c = (Condition)iter.next();
      if (! c.validate(test.getContext())) {
        return false;
      }
    }
    return true;
  }

  /**
   * {@inheritDoc}
   * The default implementation of this method does
   * nothing.
   */
  public final void validatePostConditions(MethodTest test) {
    validateConditionSet(test, getPostconditions());
  }

  /**
   * {@inheritDoc}
   * The default implementation of this method does
   * nothing.
   */
  public void validateInertiaAxiom(MethodTest test) {
    // MUDO
  }

  /**
   * {@inheritDoc}
   * The default implementation of this method does
   * nothing.
   */
  public final void validateExceptionCondition(MethodTest test, Throwable exc) {
    test.getContext().put(EXCEPTION_KEY, exc);
    validateConditionSet(test, getExceptionConditions());
  }
  
  private final static String EXCEPTION_KEY = "EXCEPTION";
  
  private void validateConditionSet(MethodTest test, Set conditionSet) {
    Iterator iter = conditionSet.iterator();
    while (iter.hasNext()) {
      Condition c = (Condition)iter.next();
      test.validate(c.validate(test.getContext()));
    }
  }

  
  
  /*<property name="preconditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final Set getPreconditions() {
    return Collections.unmodifiableSet($preconditions);
  }
  
  /**
   * @pre condition != null;
   * @post getPreconditions().contains(condition);
   * @throws TorytException
   *         isClosed();
   */
  public void addPrecondition(Condition condition) throws TorytException {
    assert condition != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    $preconditions.add(condition);
  }
  
  /**
   * @invar $preconditions != null;
   * @invar ! $preconditions.contains(null);
   * @invar (forall Object o; $preconditions.contains(o); o instanceof Condition);
   */
  private Set $preconditions = new HashSet();
  
  /*</property>*/

  
  
  /*<property name="preconditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final Set getPostconditions() {
    return Collections.unmodifiableSet($postconditions);
  }
  
  /**
   * @pre condition != null;
   * @post getPostconditions().contains(condition);
   * @throws TorytException
   *         isClosed();
   */
  public void addPostcondition(Condition condition) throws TorytException {
    assert condition != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    $postconditions.add(condition);
  }
  
  /**
   * @invar $postconditions != null;
   * @invar ! $postconditions.contains(null);
   * @invar (forall Object o; $postconditions.contains(o); o instanceof Condition);
   */
  private Set $postconditions = new HashSet();
  
  /*</property>*/

  
  
  /*<property name="exceptionConditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final Set getExceptionConditions() {
    return Collections.unmodifiableSet($exceptionConditions);
  }
  
  /**
   * @pre condition != null;
   * @post getExceptionConditions().contains(condition);
   * @throws TorytException
   *         isClosed();
   */
  public void addExceptionCondition(Condition condition) throws TorytException {
    assert condition != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    $exceptionConditions.add(condition);
  }
  
  /**
   * @invar $exceptionConditions != null;
   * @invar ! $exceptionConditions.contains(null);
   * @invar (forall Object o; $exceptionConditions.contains(o); o instanceof Condition);
   */
  private Set $exceptionConditions = new HashSet();
  
  /*</property>*/

}