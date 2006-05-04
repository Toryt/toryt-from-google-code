package org.toryt.util_I.collections.priorityList.algebra;


import org.toryt.util_I.annotations.vcs.CvsInfo;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestConcatPriorityList extends AbstractTestComponentPriorityList {

  @Override
  public void setUp() {
    super.setUp();
    $subject = new ConcatPriorityList<PriorityElementDummy>(false, $ahplA, $ahplB, $ahplC, $ahplD);
  }

}

