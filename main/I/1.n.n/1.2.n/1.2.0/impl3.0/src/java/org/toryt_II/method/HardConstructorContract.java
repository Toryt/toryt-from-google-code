package org.toryt_II.method;


import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.util.Map;

import org.toryt.support.Reflection;
import org.toryt_II.TorytException;
import org.toryt_II.type.HardTypeContract;


/**
 * @author Jan Dockx
 */
public abstract class HardConstructorContract
    extends AbstractMethodContract
    implements org.toryt_II.method.ConstructorContract {

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
  public HardConstructorContract(HardTypeContract typeContract, Constructor constructor) {
    super(typeContract);
    assert constructor != null;
    assert constructor.getDeclaringClass() == getTypeContract().getType();
    $constructor = constructor;
  }
  
  /**
   * @pre typeContract != null;
   */
  public HardConstructorContract(HardTypeContract typeContract, Class type, String signature) throws TorytException {
    this(typeContract, Reflection.findConstructor(type, signature, null));
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