package org.toryt.hard;


import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.util.Map;

import org.toryt.AbstractMethodContract;
import org.toryt.ConstructorTest;
import org.toryt.MethodTest;
import org.toryt.ReflectionSupport;
import org.toryt.TorytException;


/**
 * @author Jan Dockx
 */
public abstract class ConstructorContract
    extends AbstractMethodContract
    implements org.toryt.ConstructorContract {

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
  public ConstructorContract(TypeContract typeContract, Constructor constructor) {
    super(typeContract);
    assert constructor != null;
    assert constructor.getDeclaringClass() == getTypeContract().getType();
    $constructor = constructor;
  }
  
  /**
   * @pre typeContract != null;
   */
  public ConstructorContract(TypeContract typeContract, Class type, String signature) throws TorytException {
    this(typeContract, ReflectionSupport.findConstructor(type, signature, null));
  }

  public final Member getMember() {
    return $constructor;
  }
  
  public final Constructor getConstructor() {
    return $constructor;
  }
  
  private Constructor $constructor;
  
  public final MethodTest createMethodTest(Map testcase) {
    return new ConstructorTest(this, testcase);
  }
  
}