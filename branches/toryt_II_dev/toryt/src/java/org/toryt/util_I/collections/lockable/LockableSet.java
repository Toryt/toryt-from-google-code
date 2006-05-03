package org.toryt.util_I.collections.lockable;


import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>{@link Set} that becomes unmodifiable once it is
 *   locked.</p>
 *
 * @see LockableCollection
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface LockableSet<_Element_>
    extends LockableCollection<_Element_>, Set<_Element_> {

  // NOP

}