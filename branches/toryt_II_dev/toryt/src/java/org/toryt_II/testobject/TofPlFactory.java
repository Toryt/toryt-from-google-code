package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Factory to retrieve a
 *   <acronym title="Test Object Generator Priority List">TOGPL</acronym>.
 *   <acronym title="Test Object Generator Priority List">TOGPL</acronym>s
 *   are cached.</p>
 * <p>Toryt offers <acronym title="Test Object Generator Priority List">TOGPL</acronym>s
 *   for primitive types and most interesting types provided by the standard Java API.
 *   <acronym title="Test Object Generator Priority List">TOGPL</acronym>s for other
 *   types are retrieved automatically via naming conventions, or can be registered
 *   by hand.</p>
 * <p>A default strategy is implemented by {@link DefaultTofPlFactory}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TofPlFactory {

  /**
   * <p>Return the <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *   for class <code>forClass</code>. Repeated calls of this method for the
   *   same <code>forClass</code> will return the same
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>.</p>
   *
   * @pre forClass != null;
   * @result result != null;
   * @result result instanceof PriorityListTestObjectFactoryPriorityList<forClass>;
   * @return (getCachedTofPl(Class forClass) != null) ? getCachedTofPl(Class forClass);
   * @throws NoTofPlFoundException
   *         No <acronym title="Test Object Generator Priority List">TOGPL</acronym>
   *         could be retrieved or created for <code>forClass</code>.
   */
  <_ForClassType_> TestObjectFactoryPriorityList<_ForClassType_> getTofPl(Class<_ForClassType_> forClass)
      throws NoTofPlFoundException;

  /**
   * Return the cached <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   * for class <code>forClass</code>, if there is a cached instance. Return <code>null</code>
   * if not.
   *
   * @basic
   */
  <_ForClassType_> TestObjectFactoryPriorityList<_ForClassType_> getCachedTofPl(Class<_ForClassType_> forClass);

  /**
   * Add a <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   * to the cached collection of <acronym title="Test Object Factory Priority List">TOF PL</acronym>s,
   * so that it can be retrieved by {@link #getTofPl(Class)}.
   *
   * @pre forClass != null;
   * @pre tofPl != null;
   * @pre tofPl instanceof TestObjectFactoryPriorityList<forClass>;
   * @post getCachedTofPl(forClass) == null;
   * @post new.getCachedTofPl(forClass) == tofPl;
   * @throws AlreadyHasTofPlForClassException
   *         getCachedTofPl(forClass) != null;
   */
  <_ForClassType_> void addCachedTofPl(final Class<_ForClassType_> forClass,
                                                final TestObjectFactoryPriorityList<_ForClassType_> tofPl)
      throws AlreadyHasTofPlForClassException;

}