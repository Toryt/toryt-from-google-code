package org.toryt.util_I.collections.lockable;


import java.util.List;

import org.toryt.util_I.annotations.vcs.CvsInfo;



/**
 * <p>{@link List} that generates its elements fresh while
 *   iterating over them.</p>
 *
 * @see LazyCollection

 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface LazyList<_ElementType_> extends LazyCollection<_ElementType_>, LockableList<_ElementType_> {

  // NOP

}