package org.toryt_II.testobject.java.lang;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.testobject.ConstantTestObjectFactoryPriorityList;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Double}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _TOF_PL_Double extends ConstantTestObjectFactoryPriorityList<Double> {

  {
    addDouble(0, -7.0E-2d);
    addDouble(0, 2.0E5d);
    addDouble(1, 0.0);
    addDouble(1, Double.MAX_VALUE);
    addDouble(1, Double.MIN_VALUE);
    addDouble(1, Double.NEGATIVE_INFINITY);
    addDouble(1, Double.POSITIVE_INFINITY);
    addDouble(1, Double.NaN);
    addDouble(2, -Double.MIN_VALUE);
    addDouble(2, -(Double.MAX_VALUE - Double.MIN_VALUE));
    addDouble(2, Double.MIN_VALUE + Double.MIN_VALUE);
    addDouble(3, Float.MAX_VALUE);
    addDouble(3, Float.MIN_VALUE);
    addDouble(3, Float.NEGATIVE_INFINITY);
    addDouble(3, Float.POSITIVE_INFINITY);
    addDouble(3, Float.NaN);
    addDouble(4, Integer.MAX_VALUE);
    addDouble(4, Integer.MIN_VALUE);
    addDouble(4, Long.MAX_VALUE);
    addDouble(4, Long.MIN_VALUE);
    lock();
  }

  public void addDouble(int priority, double testObject) {
    addImmutableTestObject(priority, new Double(testObject));
  }

}