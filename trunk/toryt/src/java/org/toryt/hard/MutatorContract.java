package org.toryt.hard;


import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;

import org.toryt.AbstractMethodContract;
import org.toryt.ConstructorTest;
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

  
  
  public MutatorContract(Class type, String signature) throws TorytException {
    this(ReflectionSupport.findMethod(type, signature, null));
  }

  public MutatorContract(Method method) {
    assert method != null;
    $method = method;
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