package org.toryt.util_I.collections.priorityList.algebra.biProductPLCurried;


import java.util.HashMap;

import org.junit.Before;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.PriorityList;
import org.toryt.util_I.collections.priorityList.algebra.BiProductPriorityList;
import org.toryt.util_I.collections.priorityList.algebra.PriorityElementDummy;
import org.toryt.util_I.collections.priorityList.algebra.TestBiProductPriorityList;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestAB_C extends TestBiProductPriorityList {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    HashMap<String, PriorityList<PriorityElementDummy>> factors2 = new HashMap<String, PriorityList<PriorityElementDummy>>(2);
    factors2.put("A", $ahplA);
    factors2.put("B", $ahplB);
    BiProductPriorityList<String, PriorityElementDummy> nested = new BiProductPriorityList<String, PriorityElementDummy>(factors2);
    HashMap<String, PriorityList<?>> factors1 = new HashMap<String, PriorityList<?>>(2);
    factors1.put("AB", nested);
    factors1.put("C", $ahplC);
    $subject = new BiProductPriorityList<String, Object>(factors1);
  }

}

