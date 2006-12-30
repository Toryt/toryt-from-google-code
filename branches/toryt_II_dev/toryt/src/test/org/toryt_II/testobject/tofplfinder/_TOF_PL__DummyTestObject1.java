package org.toryt_II.testobject.tofplfinder;


import org.toryt_II.testobject.ArrayHashTofPl;
import org.toryt_II.testobject.TestObjectFactory;


/**
 * Dummy TOF PL for unit testing. This is intended
 * to be found in the same package as the base type
 * {@link _DummyTestObject1}.
 *
 * @author Jan Dockx
 */
public class _TOF_PL__DummyTestObject1 extends ArrayHashTofPl<_DummyTestObject1> {

  {
    addPriorityElement(0, new TestObjectFactory<_DummyTestObject1>() {
                                public _DummyTestObject1 generate() {
                                  return new _DummyTestObject1();
                                }
                              });
    lock();
  }

}