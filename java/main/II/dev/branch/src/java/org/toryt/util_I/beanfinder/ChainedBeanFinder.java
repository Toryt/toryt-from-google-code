/*<license>
Copyright 2006 - $Date$ by the authors mentioned below.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</license>*/

package org.toryt.util_I.beanfinder;


import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of {@link BeanFinder} tries
 *   a list of bean finders for finding a bean.
 *   If a bean finder from the list succeeds in
 *   returning a bean, it is returned. If it fails,
 *   the next bean finder from the list is tried.
 *   If the last bean finder fails, a {@link NoBeanFoundException}
 *   is thrown. If any tried bean finder throws
 *   a {@link BeanFinderConfigurationException}, it
 *   is propagated, and the search is stopped.</p>
 *
 * @author Jan Dockx
 *
 * @invar getActualBeanFinders() != null;
 * @invar Collections.noNull(getActualBeanFinders())
 * @invar (forall BeanFinder<_Argument_> bf : getActualBeanFinders();
 *           getBeanType().isAssignableFrom(bf.getBeanType()));
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ChainedBeanFinder<_Argument_>
    extends FixedBeanTypeBeanFinder<_Argument_> {


  private final static Log _LOG = LogFactory.getLog(ChainedBeanFinder.class);

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre beanType != null;
   * @post getBeanType() == beanType;
   * @post getActualBeanFinders().isEmpty;
   */
  public ChainedBeanFinder(final Class<?> beanType) {
    super(beanType);
  }

  /*</property>*/



  /*<property name="actual bean finder">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final List<BeanFinder<_Argument_>> getActualBeanFinders() {
    return Collections.unmodifiableList($actualBeanFinders);
  }

  /**
   * @pre actualBeanFinder != null;
   * @pre getBeanType().isAssignableFrom(actualBeanFinder.getBeanType()));
   * @post getActualBeanFinders().size() == getActualBeanFinders()'size() + 1;
   * @post getActualBeanFinders().get(getActualBeanFinders().size() - 1) == actualBeanFinder;
   */
  public final void addActualBeanFinder(BeanFinder<_Argument_> actualBeanFinder) {
    assert actualBeanFinder != null;
    assert getBeanType().isAssignableFrom(actualBeanFinder.getBeanType());
    $actualBeanFinders.add(actualBeanFinder);
  }

  /**
   * @invar $actualBeanFinders != null;
   * @invar Collections.noNull($actualBeanFinders);
   * @invar ; unmodifiable
   */
  private List<BeanFinder<_Argument_>> $actualBeanFinders;

  /*</property>*/



  public final Object findFor(_Argument_ argument)
      throws BeanFinderConfigurationException, NoBeanFoundException {
    Object bean = null;
    ListIterator<BeanFinder<_Argument_>> iter = $actualBeanFinders.listIterator();
    while ((bean == null) && iter.hasNext()) {
      BeanFinder<_Argument_> bf = iter.next();
      _LOG.debug("trying chained beanfinder " + iter.previousIndex() +
                 ": " + bf);
      try {
        bean = bf.findFor(argument);
          // throws BeanFinderConfigurationException, NoBeanFoundException
        _LOG.debug("bean found for argument \"" + argument + "\" by bean finder \"" +
                bf + "\": " + bean);
      }
      catch (NoBeanFoundException nbfExc) {
        _LOG.debug("no bean found for argument \"" + argument + "\" by bean finder \"" +
                   bf + "\"; continuing chain");
        assert bean == null;
        // continue the search
      }
    }
    if (bean == null) {
      _LOG.debug("no bean found for argument \"" + argument + "\" by any bean " +
            "finder in the chain; giving up");
      throw new NoBeanFoundException(this, argument);
    }
    return bean;
  }

}