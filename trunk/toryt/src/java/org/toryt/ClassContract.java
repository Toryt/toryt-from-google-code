package org.toryt;

import java.util.Set;


/**
 * The contract of a class. A {@link TypeContract} for types that
 * are not interfaces.
 * 
 * @invar ! getType().isInterface();
 * @invar (getType() == Object.class) ? (getDirectSuperClassContract() == null);
 * @invar getConstructorContracts() != null;
 * @invar ! getConstructorContracts().contains(null);
 * @invar (forall Object o; getConstructorContracts().contains(o);
 *            o instanceof ConstructorContract);
 * @invar (forall ConstructorContract cc; getConstructorContracts().contains(cc);
 *            cc.getConstructor().getDeclaringClass() == getType());
 */
public interface ClassContract extends TypeContract {

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
   * The contract of the direct superclass.
   * All classes have a direct superclass, except {@link Object}.
   * Since overridden methods must also adhere to the contract of that method
   * in the superclass, we also need to test that contract. If there is no
   * contract for the superclass, this is logged as a test failure.
   * @mudo or a warning or something
   * 
   * @basic
   */
  ClassContract getDirectSuperClassContract();
  
  /**
   * Contracts for all instance (non-static) methods, except
   * basic inspectors.
   * 
   * @basic
   */
  Set getConstructorContracts();

}