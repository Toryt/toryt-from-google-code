package org.toryt_II.testobject.java.lang;


import org.toryt_II.testobject.AbstractTestObjectFactory;
import org.toryt_II.testobject.ArrayHashTofPl;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Object}.
 *
 * @author Jan Dockx
 */
public class _TOF_PL_Object extends ArrayHashTofPl {

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
    addPriorityElement(0, new AbstractTestObjectFactory(Object.class) {
                                public Object generate() {
                                  return new Object();
                                }
                              });
    lock();
  }

}