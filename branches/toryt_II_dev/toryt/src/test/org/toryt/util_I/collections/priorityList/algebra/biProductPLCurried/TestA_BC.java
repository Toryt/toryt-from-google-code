package org.toryt.util_I.collections.priorityList.algebra.biProductPLCurried;


import java.util.HashMap;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.PriorityList;
import org.toryt.util_I.collections.priorityList.algebra.BiProductPriorityList;
import org.toryt.util_I.collections.priorityList.algebra.PriorityElementDummy;
import org.toryt.util_I.collections.priorityList.algebra.TestBiProductPriorityList;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestA_BC extends TestBiProductPriorityList {

  @Override
  public void setUp() {
    super.setUp();
    HashMap<String, PriorityList<?>> factors1 = new HashMap<String, PriorityList<?>>(2);
    factors1.put("A", $ahplA);
    HashMap<String, PriorityList<PriorityElementDummy>> factors2 = new HashMap<String, PriorityList<PriorityElementDummy>>(2);
    factors2.put("B", $ahplB);
    factors2.put("C", $ahplC);
    BiProductPriorityList<String, PriorityElementDummy> nested = new BiProductPriorityList<String, PriorityElementDummy>(factors2);
    factors1.put("BC", nested);
    $subject = new BiProductPriorityList<String, Object>(factors1);
  }

}

