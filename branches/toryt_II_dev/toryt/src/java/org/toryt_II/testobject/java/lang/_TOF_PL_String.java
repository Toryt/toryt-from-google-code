package org.toryt_II.testobject.java.lang;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.testobject.ConstantTofPl;


/**
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for {@link java.lang.String}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class _TOF_PL_String extends ConstantTofPl<String> {

  {
    addImmutableTestObject(0, "Jan Dockx");
    addImmutableTestObject(1, "");
    /* there is no size limit in Java for Strings.
     * Below is a very long String, but Strings can be longer */
    addImmutableTestObject(2, "this is a test sentence with more then 1024 characters"
                                + "*0 12345678901234567890123456789012345678901234567890"
                                +    "12345678901234567890123456789012345678901234567890"
                                + "*1 12345678901234567890123456789012345678901234567890"
                                +    "12345678901234567890123456789012345678901234567890"
                                + "*2 12345678901234567890123456789012345678901234567890"
                                +    "12345678901234567890123456789012345678901234567890"
                                + "*3 12345678901234567890123456789012345678901234567890"
                                +    "12345678901234567890123456789012345678901234567890"
                                + "*4 12345678901234567890123456789012345678901234567890"
                                +    "12345678901234567890123456789012345678901234567890"
                                + "*5 12345678901234567890123456789012345678901234567890"
                                +    "12345678901234567890123456789012345678901234567890"
                                + "*6 12345678901234567890123456789012345678901234567890"
                                +    "12345678901234567890123456789012345678901234567890"
                                + "*7 12345678901234567890123456789012345678901234567890"
                                +    "12345678901234567890123456789012345678901234567890"
                                + "*8 12345678901234567890123456789012345678901234567890"
                                +    "12345678901234567890123456789012345678901234567890"
                                + "*9 12345678901234567890123456789012345678901234567890"
                                +    "12345678901234567890123456789012345678901234567890");
    addImmutableTestObject(2, "J");
    addImmutableTestObject(3, "Jan");
    addImmutableTestObject(3, "JanD");
    addImmutableTestObject(4, " JanD");
    addImmutableTestObject(4, "JanD ");
    addImmutableTestObject(5, " Jan Dockx ");
    lock();
  }

}