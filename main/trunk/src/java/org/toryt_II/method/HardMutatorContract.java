package org.toryt_II.method;


import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;

import org.toryt.support.Reflection;
import org.toryt_II.TorytException;
import org.toryt_II.type.HardTypeContract;


/**
 * @author Jan Dockx
 */
public abstract class HardMutatorContract
    extends AbstractMethodContract
    implements org.toryt_II.method.MutatorContract {

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
  public HardMutatorContract(HardTypeContract typeContract, Method method) {
    super(typeContract);
    assert method != null;
    assert method.getDeclaringClass() == getTypeContract().getType();
    $method = method;
  }
  
  /**
   * @pre typeContract != null;
   */
  public HardMutatorContract(HardTypeContract typeContract, Class type, String signature) throws TorytException {
    this(typeContract, Reflection.findMethod(type, signature, null));
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