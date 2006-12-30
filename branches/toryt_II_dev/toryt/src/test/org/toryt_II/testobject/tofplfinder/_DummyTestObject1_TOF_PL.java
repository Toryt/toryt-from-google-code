package org.toryt_II.testobject.tofplfinder;


import org.toryt_II.testobject.ArrayHashTofPl;
import org.toryt_II.testobject.TestObjectFactory;


/**
 * Dummy TOF PL for unit testing.
 *
 * @author Jan Dockx
 */
public class _DummyTestObject1_TOF_PL extends ArrayHashTofPl<_DummyTestObject1> {

  {
    addPriorityElement(0, new TestObjectFactory<_DummyTestObject1>() {
                                public _DummyTestObject1 generate() {
                                  return new _DummyTestObject1();
                                }
                              });
    lock();
  }

}