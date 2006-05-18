package org.toryt_II.OLDcontract.hard;


import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.util.Map;

import org.toryt.util_I.reflect.Reflection;
import org.toryt_II.OLDTorytException;
import org.toryt_II.OLDcontract.AbstractMethodContract;
import org.toryt_II.contractest.ConstructorTest;
import org.toryt_II.contractest.MethodContractTest;


/**
 * @author Jan Dockx
 */
public abstract class HardConstructorContract
    extends AbstractMethodContract
    implements org.toryt_II.OLDcontract.ConstructorContract {

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
  public HardConstructorContract(HardTypeContract typeContract, Class type, String signature) throws OLDTorytException {
    this(typeContract, Reflection.findConstructor(type, signature, null));
  }

  public final Member getMember() {
    return $constructor;
  }
  
  public final Constructor getConstructor() {
    return $constructor;
  }
  
  private Constructor $constructor;
  
  public final MethodContractTest createMethodTest(Map testcase) {
    return new ConstructorTest(this, testcase);
  }
  
}