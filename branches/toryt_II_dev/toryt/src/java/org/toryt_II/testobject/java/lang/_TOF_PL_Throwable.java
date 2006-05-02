package org.toryt_II.testobject.java.lang;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.testobject.ArrayHashTofPl;
import org.toryt_II.testobject.TestObjectFactory;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Throwable}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _TOF_PL_Throwable extends ArrayHashTofPl<Throwable> {

  public final static String MESSAGE_1 = "dummy message 1";

  public final static String MESSAGE_2 = "dummy message 2";

  {
    addPriorityElement(0, new TestObjectFactory<Throwable>() {
      public Throwable generate() {
        return new Throwable(MESSAGE_1,
                             new Throwable(MESSAGE_2));
      }
    });
    // no true border test objects: no level 1 test object factories
    addPriorityElement(2, new TestObjectFactory<Throwable>() {
      public Throwable generate() {
        return new Throwable(MESSAGE_1);
      }
    });
    addPriorityElement(2, new TestObjectFactory<Throwable>() {
      public Throwable generate() {
        return new Throwable(new Throwable(MESSAGE_2));
      }
    });
    addPriorityElement(3, new TestObjectFactory<Throwable>() {
      public Throwable generate() {
        return new Throwable(new Error(MESSAGE_2));
      }
    });
    addPriorityElement(3, new TestObjectFactory<Throwable>() {
      public Throwable generate() {
        return new Throwable(new Exception(MESSAGE_2));
      }
    });
    lock();
  }

}