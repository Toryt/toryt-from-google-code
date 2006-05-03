package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;


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
public class AlreadyHasTofPlForClassException /*<_ForClass_>*/ extends TofPlFactoryException {

  /**
   * @pre tofPlFactory != null;
   * @pre forClass != null;
   * @pre tofPl != null;
   * @post new.getTofPlFactory() == tofPlFactory;
   * @post new.getForClass() == forClass;
   * @post new.getTofPl() == tofPl;
   */
  public AlreadyHasTofPlForClassException(TofPlFactory tofPlFactory,
                                          Class<?> forClass,
                                          TestObjectFactoryPriorityList<?> tofPl) {
    super(tofPlFactory, forClass);
    assert tofPl != null;
    $tofPl = tofPl;
  }

  /**
   * @basic
   */
  public TestObjectFactoryPriorityList<?> getTofPl() {
    return $tofPl;
  }

  /**
   * @invar $tofPl != null;
   */
  private final TestObjectFactoryPriorityList<?> $tofPl;

}