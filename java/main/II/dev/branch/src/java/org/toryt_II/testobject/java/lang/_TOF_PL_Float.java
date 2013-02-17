package org.toryt_II.testobject.java.lang;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.testobject.ConstantTestObjectFactoryPriorityList;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Float}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _TOF_PL_Float extends ConstantTestObjectFactoryPriorityList<Float> {

  {
    addFloat(0, -3.4E-4d);
    addFloat(0, 5.3E2d);
    addFloat(1, 0.0);
    addFloat(1, Float.MAX_VALUE);
    addFloat(1, Float.MIN_VALUE);
    addFloat(1, Float.NEGATIVE_INFINITY);
    addFloat(1, Float.POSITIVE_INFINITY);
    addFloat(1, Float.NaN);
    addFloat(2, 733.0E-22d);
    addFloat(2, -8.0E32d);
    addFloat(3, -Float.MIN_VALUE);
    addFloat(3, -(Float.MAX_VALUE - Float.MIN_VALUE));
    addFloat(3, Float.MIN_VALUE + Float.MIN_VALUE);
    addFloat(4, Integer.MAX_VALUE);
    addFloat(4, Integer.MIN_VALUE);
    addFloat(4, Long.MAX_VALUE);
    addFloat(4, Long.MIN_VALUE);
    lock();
  }

  public void addFloat(int priority, double testObject) {
    addImmutableTestObject(priority, new Float(testObject));
  }

}