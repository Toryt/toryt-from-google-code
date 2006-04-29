package org.toryt_II.testobject.java.lang;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.ArrayHashPriorityList;
import org.toryt_II.testobject.TestObjectFactory;
import org.toryt_II.testobject.TestObjectFactoryPriorityList;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Exception}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _TOF_PL_Exception
    extends ArrayHashPriorityList<TestObjectFactory<Exception>>
    implements TestObjectFactoryPriorityList<Exception> {

  public _TOF_PL_Exception() {
    super(TestObjectFactory.class);
  }

  public final static String MESSAGE_1 = "dummy message 1";

  public final static String MESSAGE_2 = "dummy message 2";

  {
    addPriorityElement(0, new TestObjectFactory<Exception>() {
      public Exception generate() {
        return new Exception(MESSAGE_1,
                             new Exception(MESSAGE_2));
      }
    });
    // no true border test objects: no level 1 test object factories
    addPriorityElement(2, new TestObjectFactory<Exception>() {
      public Exception generate() {
        return new Exception(MESSAGE_1);
      }
    });
    addPriorityElement(2, new TestObjectFactory<Exception>() {
      public Exception generate() {
        return new Exception(new Exception(MESSAGE_2));
      }
    });
    addPriorityElement(3, new TestObjectFactory<Exception>() {
      public Exception generate() {
        return new Exception(new Error(MESSAGE_2));
      }
    });
    addPriorityElement(3, new TestObjectFactory<Exception>() {
      public Exception generate() {
        return new Exception(new Throwable(MESSAGE_2));
      }
    });
    lock();
  }

}