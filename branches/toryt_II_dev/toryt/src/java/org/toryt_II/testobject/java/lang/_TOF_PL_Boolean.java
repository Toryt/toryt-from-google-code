package org.toryt_II.testobject.java.lang;


import org.toryt_II.testobject.ConstantTofPl;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Boolean}.
 *
 * @author Jan Dockx
 */
public class _TOF_PL_Boolean extends ConstantTofPl {

  /* <section name="Meta Information"> */
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /* </section> */


  {
    addImmutableTestObject(0, Boolean.TRUE);
    addImmutableTestObject(0, Boolean.FALSE);
    lock();
  }

}