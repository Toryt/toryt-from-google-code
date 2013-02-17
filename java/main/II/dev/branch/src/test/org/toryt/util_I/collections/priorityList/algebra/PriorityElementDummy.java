package org.toryt.util_I.collections.priorityList.algebra;


import org.toryt.util_I.annotations.vcs.CvsInfo;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class PriorityElementDummy {

  public PriorityElementDummy(String priorityListName, int priority) { //, String inBucketId) {
    $priorityListName = priorityListName;
    $priority = priority;
//    $inBucketId = inBucketId;
  }

  public final String getPriorityListName() {
    return $priorityListName;
  }

  private String $priorityListName;

  public final int getPriority() {
    return $priority;
  }

  private int $priority;

//  public final String getInBucketId() {
//    return $inBucketId;
//  }
//
//  private String $inBucketId;

  @Override
  public String toString() {
    return "[" + $priorityListName + + $priority + "(" + Integer.toHexString(hashCode()) + ")]";
  }

}

