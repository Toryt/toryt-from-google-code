package org.toryt;


import java.lang.reflect.Member;
import java.util.Map;
import java.util.Set;

import org.toryt.support.straightlist.StraightList;


/**
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
  
  public abstract StraightList getTestCases() throws TorytException;
  
  public abstract String[] getFormalParameters();

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
   * @basic
   */
  Set getExceptionConditions();

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

}