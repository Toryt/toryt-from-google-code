package org.toryt.hard;


import java.lang.reflect.Method;

import org.toryt.AbstractMethodContract;
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
    super(type, signature);
  }

  public MutatorContract(Method method) {
    super(method);
  }

}