package org.toryt.util_I.collections.priorityList.algebra;


import org.toryt.util_I.annotations.vcs.CvsInfo;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class PriorityElementDummy {

  public PriorityElementDummy(int priorityListNumber, int priority, int inBucket) {
    $priorityListNumber = priorityListNumber;
    $priority = priority;
    $inBucket = inBucket;
  }

  public final int getPriorityListNumber() {
    return $priorityListNumber;
  }

  private int $priorityListNumber;

  public final int getPriority() {
    return $priority;
  }

  private int $priority;

  public final int getInBucket() {
    return $inBucket;
  }

  private int $inBucket;

  @Override
  public String toString() {
    return "[list: " + $priorityListNumber +
           " , priority: " + $priority +
           " , in bucket: " + $inBucket +
           "]";
  }

}

