package org.toryt;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 */
public class ReflectionSupport {

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
  

  
  public static Method findMethod(Class type, String signature, Contract excSource)
      throws TorytException {
    assert type != null;
    assert signature != null;
    String search = type.getName() + "." + signature;
    Method[] methods = type.getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
      if (methods[i].toString().indexOf(signature) > -1) {
        return methods[i];
      }
    }
    throw new TorytException(excSource, null);
  }
  
  public static Constructor findConstructor(Class type, String signature, Contract excSource)
  throws TorytException {
    assert type != null;
    assert signature != null;
    Constructor[] constructors = type.getConstructors();
    for (int i = 0; i < constructors.length; i++) {
      if (constructors[i].toString().indexOf(signature) > -1) {
        return constructors[i];
      }
    }
    throw new TorytException(excSource, null);
  }

}