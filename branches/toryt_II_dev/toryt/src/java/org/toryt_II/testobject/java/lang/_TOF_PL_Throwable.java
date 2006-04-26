package org.toryt_II.testobject.java.lang;


import org.toryt_II.testobject.AbstractTestObjectFactory;
import org.toryt_II.testobject.ArrayHashTofPl;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Throwable}.
 *
 * @author Jan Dockx
 */
public class _TOF_PL_Throwable extends ArrayHashTofPl {

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


  public final static String MESSAGE_1 = "dummy message 1";

  public final static String MESSAGE_2 = "dummy message 2";

  {
    addPriorityElement(0, new AbstractTestObjectFactory(Throwable.class) {
      public Object generate() {
        return new Throwable(MESSAGE_1,
                             new Throwable(MESSAGE_2));
      }
    });
    // no true border test objects: no level 1 test object factories
    addPriorityElement(2, new AbstractTestObjectFactory(Throwable.class) {
      public Object generate() {
        return new Throwable(MESSAGE_1);
      }
    });
    addPriorityElement(2, new AbstractTestObjectFactory(Throwable.class) {
      public Object generate() {
        return new Throwable(new Throwable(MESSAGE_2));
      }
    });
    addPriorityElement(3, new AbstractTestObjectFactory(Throwable.class) {
      public Object generate() {
        return new Throwable(new Error(MESSAGE_2));
      }
    });
    addPriorityElement(3, new AbstractTestObjectFactory(Throwable.class) {
      public Object generate() {
        return new Throwable(new Exception(MESSAGE_2));
      }
    });
    lock();
  }

}