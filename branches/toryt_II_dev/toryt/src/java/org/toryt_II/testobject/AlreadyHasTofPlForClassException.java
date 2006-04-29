package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * Signals that there already is a
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for class {@link #getForClass()} in {@link #getTofPlFactory()}.
 *
 * @author Jan Dockx
 *
 * @invar getTofPl() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class AlreadyHasTofPlForClassException extends TofPlFactoryException {

  /**
   * @pre tofPlFactory != null;
   * @pre forClass != null;
   * @pre tofPl != null;
   * @post new.getTofPlFactory() == tofPlFactory;
   * @post new.getForClass() == forClass;
   * @post new.getTofPl() == tofPl;
   */
  public AlreadyHasTofPlForClassException(TofPlFactory tofPlFactory, Class forClass,
                                          PriorityList<TestObjectFactory<?>> tofPl) {
    super(tofPlFactory, forClass);
    assert tofPl != null;
    $tofPl = tofPl;
  }

  /**
   * @basic
   */
  public PriorityList<TestObjectFactory<?>> getTofPl() {
    return $tofPl;
  }

  /**
   * @invar $tofPl != null;
   */
  private final PriorityList<TestObjectFactory<?>> $tofPl;

}