package org.toryt.util_I.collections.lockable;


import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;



/**
 * <p>{@link Set} that generates its elements fresh while
 *   iterating over them.</p>
 *
 * @see LazyCollection
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface LazySet<_ElementType_> extends LazyCollection<_ElementType_>, LockableSet<_ElementType_> {

  // NOP

}