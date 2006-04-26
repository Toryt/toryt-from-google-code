package org.toryt_II.testobject.java.lang;


import org.toryt_II.testobject.ConstantTofPl;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Integer}.
 *
 * @author Jan Dockx
 */
public class _TOF_PL_Integer extends ConstantTofPl {

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
    addInteger(0, 5);
    addInteger(0, -3);
    addInteger(1, 0);
    addInteger(1, Integer.MAX_VALUE);
    addInteger(1, Integer.MIN_VALUE);
    addInteger(2, 1);
    addInteger(2, -1);
    addInteger(2, -Integer.MIN_VALUE);
    addInteger(3, Integer.MAX_VALUE - 1);
    addInteger(3, Integer.MIN_VALUE + 1);
    addInteger(4, 1024);
    addInteger(4, -1024);
    lock();
  }

  public void addInteger(int priority, int testObject) {
    addImmutableTestObject(priority, new Integer(testObject));
  }

}