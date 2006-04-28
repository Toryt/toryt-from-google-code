package org.toryt.util_I.collections.bigSet.lockable;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.BigSet;
import org.toryt.util_I.collections.lockable.LazySet;


/**
 * <p>{@link BigSet} that becomes unmodifiable once it is
 *   locked. The set could be locked at construction.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface LazyBigSet extends LockableBigSet, LazySet {

}