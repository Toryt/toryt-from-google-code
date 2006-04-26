package org.toryt_II.testobject.java.util;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.toryt_II.testobject.AbstractTestObjectFactory;
import org.toryt_II.testobject.ArrayHashTofPl;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.util.Date}.
 *
 * @author Jan Dockx
 */
public class _TOF_PL_Date extends ArrayHashTofPl {

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
    addPriorityElement(0, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, 1444);
        gc.add(Calendar.HOUR_OF_DAY, -8);
        gc.add(Calendar.MINUTE, 43);
        gc.add(Calendar.SECOND, -15);
        gc.add(Calendar.MILLISECOND, 438);
        return gc.getTime();
      }
    });
    addPriorityElement(0, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, -234);
        gc.add(Calendar.HOUR_OF_DAY, 4);
        gc.add(Calendar.MINUTE, -32);
        gc.add(Calendar.SECOND, 23);
        gc.add(Calendar.MILLISECOND, -664);
        return gc.getTime();
      }
    });
    addPriorityElement(1, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        return new Date(); // now
      }
    });
    addPriorityElement(1, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        return new Date(0); // epoch
      }
    });
    addPriorityElement(1, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        return new Date(Long.MAX_VALUE);
      }
    });
    addPriorityElement(1, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        return new Date(Long.MIN_VALUE);
      }
    });
    addPriorityElement(2, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime(); // today
      }
    });
    addPriorityElement(2, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, 1);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime(); // tomorrow
      }
    });
    addPriorityElement(2, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, -1);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime(); // yesterday
      }
    });
    addPriorityElement(3, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, -1);
        return gc.getTime(); // yesterday
      }
    });
    addPriorityElement(3, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, 1);
        return gc.getTime(); // tomorrow
      }
    });
    addPriorityElement(3, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.MONTH, -1);
        return gc.getTime(); // last month
      }
    });
    addPriorityElement(3, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.MONTH, 1);
        return gc.getTime(); // next month
      }
    });
    addPriorityElement(3, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.YEAR, -1);
        return gc.getTime(); // last year
      }
    });
    addPriorityElement(3, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.YEAR, 1);
        return gc.getTime(); // next year
      }
    });
    addPriorityElement(4, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.WEEK_OF_YEAR, -1);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime(); // last week
      }
    });
    addPriorityElement(4, new AbstractTestObjectFactory(Date.class) {
      public Object generate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.WEEK_OF_YEAR, 1);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime(); // next week
      }
    });
    lock();
  }

}