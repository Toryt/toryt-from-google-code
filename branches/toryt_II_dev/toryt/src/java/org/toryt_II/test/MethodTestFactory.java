package org.toryt_II.test;


import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.contractest.MethodContractTest;
import org.toryt_II.testobject.TestObjectFactory;


/**
 * Test class for non-constructor methods, i.e., instance methods
 * and non-constructor class methods.
 *
 * @invar getContract() instanceof NonConstructorMethodContract;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class MethodTestFactory<_Subject_ extends Member, _Test_ extends MethodContractTest<_Subject_>> {

  static, constructor: no subject????

  public final _Subject_ getSubject() {
    return $subject;
  }

  public final void setSubject(_Subject subject) {
    $subject = subject;
  }

  private _Subject_ $subject;

  public final Map<String, TestObjectFactory<?>> getTestCaseObjectFactoryMap() {
    return $testCaseObjectFactoryMap;
  }

  public final void setTestCaseObjectFactoryMap(Map<String, TestObjectFactory<?>> testCaseObejctFactoryMap) {
    $testCaseObjectFactoryMap = testCaseObejctFactoryMap;
  }

  private Map<String, TestObjectFactory<?>> $testCaseObjectFactoryMap;

  public final Map<String, Object> getTestCase() throws TestObjectCreationFailedException {
    if ($testCase == null) {
      prepareTestCase();
    }
    return $testCase;
  }

  private void prepareTestCase() throws CannotGenerateTestCaseException {
    $testCase = new HashMap<String, Object>();
    try {
      for (Map.Entry<String, TestObjectFactory<?>> entry : $testCaseObjectFactoryMap.entrySet()) {
        // NullPointerException if $testCaseObjectFactoryMap == null
        $testCase.put(entry.getKey(), entry.getValue().generate()); // Throwable
      }
    }
    catch (NullPointerException npExc) {
// StateException
    }
    catch (Throwable t) {
      _LOG.debug("generation of test object for argument " + entry.getKey() +
                 " failed with an exception", t);
      $testCase = null;
      throw new TestObjectCreationFailedException(this, entry.getKey(), entry.getValue());
//    TestObjectCreationFailedException < CannotGenerateTestException
    }
  }

  private Map<String, Object> $testCase;

  public _Test_ generate() throws CannotGenerateTestException {
    if ($subject == null) {
      ...
    }
    if ($testCaseObjectFactoryMap == null) {
      ...
    }
    if ($contract == null) {
      ...
    }
    _Test_ test = createNewTest();
    test.setSubject($subject);
    test.setTestCase(getTestCase());
    validate pre's
    test.setInvars, Post, ...
  }

  public abstract _Test_ createNewTest();
  // MUDO set contract

}