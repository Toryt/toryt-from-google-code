package org.toryt_II.testobject.java.lang;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.testobject.AbstractTestObjectFactory;
import org.toryt_II.testobject.ArrayHashTofPl;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.Object}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _TOF_PL_Object extends ArrayHashTofPl {

  {
    addPriorityElement(0, new AbstractTestObjectFactory(Object.class) {
                                public Object generate() {
                                  return new Object();
                                }
                              });
    lock();
  }

}