package org.toryt_II.testobject.java.lang;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.testobject.ConstantTofPl;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Boolean}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _TOF_PL_Boolean extends ConstantTofPl<Boolean> {

  {
    addImmutableTestObject(0, Boolean.TRUE);
    addImmutableTestObject(0, Boolean.FALSE);
    lock();
  }

}