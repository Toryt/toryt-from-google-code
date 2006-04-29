package org.toryt_II.testobject.java.lang;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.ArrayHashPriorityList;
import org.toryt_II.testobject.TestObjectFactory;
import org.toryt_II.testobject.TestObjectFactoryPriorityList;


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
public class _TOF_PL_Object
    extends ArrayHashPriorityList<TestObjectFactory<Object>>
    implements TestObjectFactoryPriorityList<Object> {

  public _TOF_PL_Object() {
    super(TestObjectFactory.class);
  }

  {
    addPriorityElement(0, new TestObjectFactory<Object>() {
                                public Object generate() {
                                  return new Object();
                                }
                              });
    lock();
  }

}