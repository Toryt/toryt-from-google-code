package org.toryt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;




/**
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
  

  protected AbstractMethodContract(Method method) {
    assert method != null;
    $method = method;
  }
  
  protected AbstractMethodContract(Class type, String signature) throws TorytException {
    this(ReflectionSupport.findMethod(type, signature, null));
  }
  
  public Method getMethod() {
    return $method;
  }
  
  private Method $method;
  
  public final List getMethodTests() throws TorytException {
    // MUDO this order must become priority order
    List result = new ArrayList(getTestCases().size());
    ListIterator iterCases = getTestCases().listIterator();
    while (iterCases.hasNext()) {
      Map testCase = (Map)iterCases.next();
      MethodTest test = new MethodTest(this, testCase);
      result.add(test);
    }
    return result;
  }

  public String[] getFormalParameters() {
    return new String[0];
  }
  
  public void recordOldState(MethodTest test, Map state) {
    // NOP
  }
  
  public boolean validatePreconditions(MethodTest test) {
    return true;
  }

  public void validatePostConditions(MethodTest test) {
    // NOP
  }

  public void validateInertiaAxiom(MethodTest test) {
    // MUDO
  }

  public void validateTypeInvariants(MethodTest test) {
    // MUDO
  }

  public void validateExceptionCondition(MethodTest test, InvocationTargetException e) {
    // NOP
  }
    
}