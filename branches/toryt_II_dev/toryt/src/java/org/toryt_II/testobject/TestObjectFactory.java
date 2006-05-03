package org.toryt_II.testobject;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>TestObjectGenerators generate test objects fresh for
 *   a particular test of a method.</p>
 * <p>Generating a test object might fail. If the {@link #generate()}
 *   method throws any {@link Throwable} (notably, an {@link AssertionError}),
 *   generation is considered to have failed. As a result, generating the case
 *   for which this test object was intended will fail. As a result, the
 *   test for which this case was intended will be skipped.</p>
 * <p>If the class for which this generator generates test objects is
 *   guaranteed to be immutable, the generator may return the same object
 *   every time. If the the class for which this generator generates
 *   test objects is not guaranteed to be immutable, the generator has
 *   to return a fresh object with the same values every time
 *   {@link #generate()} is called.</p>
 *
 * @author Jan Dockx
 *
 * @invar generate() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TestObjectFactory<_TestObject_> {

  /**
   * @basic
   *
   * @throws Throwable
   *         true;
   */
  _TestObject_ generate() throws Throwable;

}