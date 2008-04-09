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
public class TestAB_CD extends TestBiProductPriorityList {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    HashMap<String, PriorityList<PriorityElementDummy>> factorsAB = new HashMap<String, PriorityList<PriorityElementDummy>>(2);
    factorsAB.put("A", $ahplA);
    factorsAB.put("B", $ahplB);
    BiProductPriorityList<String, PriorityElementDummy> ab = new BiProductPriorityList<String, PriorityElementDummy>(factorsAB);
    HashMap<String, PriorityList<PriorityElementDummy>> factorsCD = new HashMap<String, PriorityList<PriorityElementDummy>>(2);
    factorsCD.put("C", $ahplC);
    factorsCD.put("D", $ahplD);
    BiProductPriorityList<String, PriorityElementDummy> cd = new BiProductPriorityList<String, PriorityElementDummy>(factorsCD);
    HashMap<String, PriorityList<?>> factors = new HashMap<String, PriorityList<?>>(2);
    factors.put("AB", ab);
    factors.put("CD", cd);
    $subject = new BiProductPriorityList<String, Object>(factors);
  }

}

