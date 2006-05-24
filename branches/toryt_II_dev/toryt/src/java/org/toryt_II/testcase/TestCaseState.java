package org.toryt_II.testcase;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * States for test cases.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public enum TestCaseState {

  OPEN,

  PRE,

  READY,

  CLOSED

}