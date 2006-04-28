package org.toryt_II.testobject.java.lang;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.testobject.ConstantTofPl;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Long}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _TOF_PL_Long extends ConstantTofPl {

  {
    addLong(0, 77);
    addLong(0, -4);
    addLong(1, 0);
    addLong(1, Long.MAX_VALUE);
    addLong(1, Long.MIN_VALUE);
    addLong(2, 1);
    addLong(2, -1);
    addLong(2, -Long.MIN_VALUE);
    addLong(3, Long.MAX_VALUE - 1);
    addLong(3, Long.MIN_VALUE + 1);
    addLong(4, Integer.MAX_VALUE);
    addLong(4, Integer.MIN_VALUE);
    addLong(5, 1024);
    addLong(5, -1024);
    lock();
  }

  public void addLong(int priority, long testObject) {
    addImmutableTestObject(priority, new Long(testObject));
  }

}