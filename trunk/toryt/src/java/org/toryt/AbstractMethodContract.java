package org.toryt;


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.toryt.support.straightlist.ArrayStraightList;
import org.toryt.support.straightlist.LazyMappingStraightList;
import org.toryt.support.straightlist.StraightList;


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
  
  /**
   * If {@link #getTestCases()} is empty, there are no method tests.
   * THIS DOES NOT WORK FOR DEFAULT CONSTRUCTOR OR SOME NO_ARGS STATIC METHODS
   * INSTANCE METHODS ALWAYS HAVE AT LEAST 1 ARGUMENT (SUBJECT) THAT WILL
   * BE FOUND IN TESTCASES
   */
  public final StraightList getMethodTests() throws TorytException {
    // MUDO this order must become priority order
    // MUDO this must become a list of method test factories
    StraightList testCases = getTestCases();
    if (testCases.isEmpty()) {
      // create an empty map, 1 test case, to use as factory for 1 method test
      testCases = new ArrayStraightList(new Map[] {new HashMap()});
    }
    return new LazyMappingStraightList(testCases,
                                       new LazyMappingStraightList.Mapping() {
      
                                            public boolean isValid(Object o) {
                                              Map testCaseMap = (Map)o;
                                              return validatePreconditions(testCaseMap);
                                            }
      
                                             public Object map(Object o) {
                                               Map testCaseMap = (Map)o;
                                               return createMethodTest(testCaseMap);
                                             }
                                             
                                           });
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
  private boolean validatePreconditions(Map testCase) {
    Iterator iter = getPreconditions().iterator();
    while (iter.hasNext()) {
      Condition c = (Condition)iter.next();
      if (! c.validate(testCase)) {
        return false;
      }
    }
    return true;
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

  
  
  /*<property name="postconditions">*/
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
  public final Map getExceptionConditions() {
    //unmodifiable map of unmodifiable sets of exception conditions
    Map result = new HashMap();
    Iterator iter = $exceptionConditions.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry)iter.next();
      result.put(entry.getKey(), Collections.unmodifiableSet((Set)entry.getValue()));
    }
    return Collections.unmodifiableMap(result);
  }
  
  /**
   * @pre condition != null;
   * @post getExceptionConditions().contains(condition);
   * @throws TorytException
   *         isClosed();
   */
  public void addExceptionCondition(ExceptionCondition condition) throws TorytException {
    assert condition != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    Set exceptionConditionSet = (Set)$exceptionConditions.get(condition.getExceptionType());
    if (exceptionConditionSet == null) {
      exceptionConditionSet = new HashSet();
      $exceptionConditions.put(condition.getExceptionType(), exceptionConditionSet);
    }
    exceptionConditionSet.add(condition);
  }
  
  /**
   * @invar $exceptionConditions != null;
   * @invar ! $exceptionConditions.contains(null);
   * @invar (forall Object o; $exceptionConditions.contains(o); o instanceof Condition);
   */
  private Map $exceptionConditions = new HashMap();
  
  /*</property>*/

}