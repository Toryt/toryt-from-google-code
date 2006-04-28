package org.toryt.util_I.collections.typed;


import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Set that only allows elements of type
 *   {@link #getElementType()}.</p>
 *
 * @author Jan Dockx
 *
 * @note When moving to Java 5, replace this with a generics.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TypedSet extends TypedCollection, Set {

}