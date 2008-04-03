package org.toryt.patterns_I;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class Comparisons {

  private Comparisons() {
    // NOP
  }

  public static boolean equalsWithNull(Object one, Object other) {
    if (one == other) {
      return true;
    }
    else if (one == null) {
      assert other != null;
      return false;
    }
    else {
      assert one != null;
      assert other != null;
      return one.equals(other);
    }
  }

}