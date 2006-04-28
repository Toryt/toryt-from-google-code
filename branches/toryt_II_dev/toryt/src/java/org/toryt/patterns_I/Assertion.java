package org.toryt.patterns_I;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Boolean assertion for an object.
 *
 * @author    Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface Assertion {

  boolean isTrueFor(Object o);

}
