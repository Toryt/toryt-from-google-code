package org.toryt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;




/**
 * @author Jan Dockx
 * 
 * @invar getMethod() != null;
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

  
  
  Method getMethod();
    
  public final static String SUBJECT_KEY = "SUBJECT";
  
  public abstract List getTestCases() throws TorytException;
  
  public abstract String[] getFormalParameters();

  boolean validatePreconditions(MethodTest test);

  void validatePostConditions(MethodTest test);

  void validateInertiaAxiom(MethodTest test);

  void validateTypeInvariants(MethodTest test);

  void validateExceptionCondition(MethodTest test, InvocationTargetException e);

  void recordOldState(MethodTest test, Map state);

}