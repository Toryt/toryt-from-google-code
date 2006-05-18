package org.toryt.util_I.collections.algebra;


import java.util.Iterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Used in some collections or visitors to map
 *   elements.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface Mapping<_From_, _To_> {

  /**
   * This method cannot throw any exceptions, since it is used
   * in {@link Iterator#next()}, which cannot throw any exceptions.
   */
  _To_ map(_From_ element);

}