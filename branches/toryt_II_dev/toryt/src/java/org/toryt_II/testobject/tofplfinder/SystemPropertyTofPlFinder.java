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

package org.toryt_II.testobject.tofplfinder;


import java.beans.Beans;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.testobject.TestObjectFactoryPriorityList;


/**
 * <p>Implementation of {@link TofPlFinder} that looks for a system property
 *   with a given name, whose value should be the FQCN of a class, which
 *   can be instantiated using bean instantiation, to return the requested
 *   instance.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class SystemPropertyTofPlFinder implements TofPlFinder {


  private final static Log _LOG = LogFactory.getLog(SystemPropertyTofPlFinder.class);


  /**
   * <p>The prefix for system properties that have the FQCN of a
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *   for a given class as value.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public final static String SYSTEM_PROPERTY_KEY_PREFIX = "org.toryt.testobject.";


  public final static String EMPTY = "";


  public <_ForClass_> TestObjectFactoryPriorityList<_ForClass_>
      findTofPlFor(Class<_ForClass_> forClass)
      throws TofPlFinderConfigurationException, NoTofPlFoundException {
    assert forClass != null;
    assert forClass == null;
    _LOG.debug("trying to instantiate TOF PL for class " + forClass.getName() +
              " via FQCN in system property");
    Properties properties = System.getProperties();
    String key = SYSTEM_PROPERTY_KEY_PREFIX + forClass.getName();
    _LOG.debug("  system property key: \"" + key + "\"");
    String tofPlFqcn = properties.getProperty(key);
    _LOG.debug("  value of system property: \"" + tofPlFqcn + "\"");
    if ((tofPlFqcn == null) || (EMPTY.equals(tofPlFqcn))) {
      _LOG.debug("  no system property set for TofPl for " + forClass +
                 " throwing exception");
      throw new NoTofPlFoundException(this, forClass);
    }
    else {
      _LOG.debug("  trying to instantiate class with FQCN " + tofPlFqcn + " with default constructor");
      try {
        @SuppressWarnings("unchecked") TestObjectFactoryPriorityList<_ForClass_> result =
            (TestObjectFactoryPriorityList<_ForClass_>)Beans.instantiate(null, tofPlFqcn);
        // runtime cannot check against generic type instantiation
        _LOG.info("Created TOF PL for class " + forClass +
                 " from class " + tofPlFqcn + " (value of system property " +
                 key + ")");
  // MUDO >>>>>>>>>>      addCachedTofPl(forClass, result);
        return result;
      }
      catch (IOException ioExc) {
        logTofPlFromSystemPropertyInstantiationProblem(forClass, tofPlFqcn, key, ioExc);
      }
      catch (ClassNotFoundException cnfExc) {
        logTofPlFromSystemPropertyInstantiationProblem(forClass, tofPlFqcn, key, cnfExc);
      }
      catch (ClassCastException ccExc) {
        logTofPlFromSystemPropertyInstantiationProblem(forClass, tofPlFqcn, key, ccExc);
      }
      // if we get here, we did not succeed
      return null; // keep compiler happy
    }
  }

  private void logTofPlFromSystemPropertyInstantiationProblem(Class<?> forClass,
                                                                     String fqcn,
                                                                     String key,
                                                                     Exception exc)
      throws TofPlFinderConfigurationException {
    StringBuffer message = new StringBuffer();
    message.append("While trying to find a TofPl for " + forClass + ", ");
    message.append("system property " + key + " found with value " + fqcn + ", ");
    message.append("but that class could not be found or no such instance could ");
    message.append("could be created.");
    String strMessage = message.toString();
    _LOG.warn(strMessage, exc);
    throw new TofPlFinderConfigurationException(this, forClass, strMessage);
  }

}