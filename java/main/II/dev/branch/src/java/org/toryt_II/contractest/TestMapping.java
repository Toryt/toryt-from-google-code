package org.toryt_II.contractest;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.toryt.util_I.collections.algebra.Mapping;
import org.toryt_II.test.Test;
import org.toryt_II.testobject.TestObjectFactory;


/**
 * Maps a {@link Map} of {@link TestObjectFactory TestObjectFactories}
 * to a {@link Test} for a given method.
 *
 * @author Jan Dockx
 */
public abstract class TestMapping<_Test_ extends Test>
    implements Mapping<Map<String, TestObjectFactory<?>>, _Test_> {

  public _Test_ map(Map<String, TestObjectFactory<?>> element) {
    _Test_ test = createNewTest();
    // create the test case from labeled test object factories
    Map<String, Object> testCase = new HashMap<String, Object>();
    Iterator<Map.Entry<String, TestObjectFactory<?>>> iter = element.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry<String, TestObjectFactory<?>> entry = iter.next();
      try {
        testCase.put(entry.getKey(), entry.getValue().generate());
      }
      catch (Throwable t) {
        _LOG.debug("generation of test object for argument " + entry.getKey() +
                   " failed with an exception", t);
        ???? return null?????
      }
    }
    test.setTestCase(testCase);
    return test;
  }

  public abstract _Test_ createNewTest();
  // MUDO set subject, set contract

}