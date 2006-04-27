package org.toryt_II.testmodel;

import org.toryt.util_I.reflect.Reflection.TypeKind;


/**
 * Instances represent an inner class to test.
 *
 * @author Jan Dockx
 *
 * @invar getTypeKind() == TypeKind.INNER;
 */
public class InnerClassTestModel extends ClassTestModel {

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


  public InnerClassTestModel() {
    super(TypeKind.INNER);
  }

}