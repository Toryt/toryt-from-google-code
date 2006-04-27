package org.toryt_II.contract;


import java.lang.reflect.Member;
import java.util.Map;
import java.util.Set;

import org.toryt.support.straightlist.StraightList;
import org.toryt_II.OLDTorytException;
import org.toryt_II.contract.condition.Condition;
import org.toryt_II.contract.condition.ExceptionCondition;
import org.toryt_II.test.MethodTest;


/**
 * A <dfn>method contract</dfn> instance describes the contract of a
 * <dfn>declared method</dfn> in an interface or a class. Only those
 * parts of the contract that are declared in this class, are described
 * in this instance. Parts of the contract that are inherited, are not
 * repeated.
 *
 * An instance knows for {@link #getMember() what method} it is a contract,
 * and in {@link #getTypeContract() what type} it is declared. It can be
 * asked to {@link #createMethodTest(Map) create a method test (factory method)}
 * for this contract with a given <code>testCases</code>.
 * The <code>testCases</code> for which a test is created, are generated by
 * {@link #getTestCases()}.
 * 
 * A method contract consists of
 * {@link #getPreconditions() preconditions},
 * {@link #getPostconditions() postconditions} and
 * {@link #getExceptionConditions() exception conditions}.
 * These are stored as sets of {@link Condition Conditions}.
 * 
 * During a test of this contract, the test needs to store it's old
 * state before actually invoking the method-under-test. How to do
 * this, is implemented {@link #recordState(MethodTest) in the contract}.
 * 
 * @author Jan Dockx
 *
 * @invar getTypeContract() != null; 
 * @invar getMember() != null;
 * @invar (getMember() instanceof Method) || (getMember() instanceof Constructor);
 * @invar getMember().getDeclaringClass() == getTypeContract().getType();
 */
public interface MethodContract extends Contract {

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
   * @basic
   */
  Member getMember();
    
  /**
   * The contract of the type in which this method is defined.
   */
  TypeContract getTypeContract();
  
  public final static String SUBJECT_KEY = MethodTest.SUBJECT_KEY;
  public final static String RESULT_KEY = MethodTest.RESULT_KEY;
  public final static String EXCEPTION_KEY = MethodTest.EXCEPTION_KEY;
  
  public abstract StraightList getTestCases() throws OLDTorytException;
  
  public abstract String[] getFormalParameters();

  /*<property name="preconditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  Set getPreconditions();

  /*</property>*/
    

    
  /*<property name="postconditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  Set getPostconditions();

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
  Map getExceptionConditions();

  /*</property>*/

  
  
  /**
   * Record the old state of the test case (that is the
   * current state of <code>test.getContext()</state> now)
   * also in <code>test.getContext()</code>.
   * The default implementation of this method does nothing.
   */
  void recordState(MethodTest test);
  
  /**
   * Factory method for the creation of method tests of the correct
   * type in the covariant test hierarchy.
   * 
   * @post result.getContract() == this;
   */
  MethodTest createMethodTest(Map testcase);
  
  /**
   * @result result.isEmpty();
   */
  Set getSubContracts();

}