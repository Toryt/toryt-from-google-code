package org.toryt.hard;


import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;

import org.toryt.AbstractMethodContract;
import org.toryt.MethodTest;
import org.toryt.NonConstructorMethodTest;
import org.toryt.ReflectionSupport;
import org.toryt.TorytException;


/**
 * @author Jan Dockx
 */
public abstract class MutatorContract
    extends AbstractMethodContract
    implements org.toryt.MutatorContract {

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
   * @pre constructor != null;
   * @pre constructor.getDeclaringClass() == getTypeContract().getType();
   */
  public MutatorContract(TypeContract typeContract, Method method) {
    super(typeContract);
    assert method != null;
    assert method.getDeclaringClass() == getTypeContract().getType();
    $method = method;
  }
  
  /**
   * @pre typeContract != null;
   */
  public MutatorContract(TypeContract typeContract, Class type, String signature) throws TorytException {
    this(typeContract, ReflectionSupport.findMethod(type, signature, null));
  }
  
  public final Member getMember() {
    return getMethod();
  }
  
  public final Method getMethod() {
    return $method;
  }
  
  private Method $method;
  
  public final MethodTest createMethodTest(Map testcase) {
    return new NonConstructorMethodTest(this, testcase);
  }

}