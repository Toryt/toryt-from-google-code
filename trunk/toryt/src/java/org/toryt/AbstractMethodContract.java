package org.toryt;


import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


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
  public boolean validatePreconditions(MethodTest test) {
    return true;
  }

  /**
   * {@inheritDoc}
   * The default implementation of this method does
   * nothing.
   */
  public void validatePostConditions(MethodTest test) {
    // NOP
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
  public void validateTypeInvariants(MethodTest test) {
    // MUDO
  }

  /**
   * {@inheritDoc}
   * The default implementation of this method does
   * nothing.
   */
  public void validateExceptionCondition(MethodTest test, Throwable exc) {
    // NOP
  }
  
  public String toString() {
    return getMember().toString();
  }
    
}