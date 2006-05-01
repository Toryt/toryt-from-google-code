package org.toryt_II.main;


import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.toryt.support.straightlist.StraightList;
import org.toryt_II.OLDTorytException;
import org.toryt_II.contract.Contract;
import org.toryt_II.test.AbstractTest;
import org.toryt_II.test.Test;
import org.toryt_II.test.TestAlreadyStartedException;


/**
 * @author Jan Dockx
 */
public class SimpleCli extends AbstractTest {

  /*<section name="Meta Information">*/
  //  ------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /*</section>*/
  


  public SimpleCli(Contract contract, int maximumNrOfFailures) {
    assert contract != null;
    $contract = contract;
    $maximumNrOfFailures = maximumNrOfFailures;
  }
  
  /*<property name="failed tests">*/
  //------------------------------------------------------------------
  
  public final List getFailedTests() {
    return Collections.unmodifiableList($failedTests);
  }
  
  private List $failedTests;

  /*</property>*/

  
  public final Contract getContract() {
    return $contract;
  }
  
  private Contract $contract;
  
  public static final int DEFAULT_MAXIMUM_NUMBER_OF_FAILURES = 20;
  
  public int $maximumNrOfFailures = DEFAULT_MAXIMUM_NUMBER_OF_FAILURES;
  
  private final boolean hasEnough() {
    return ($maximumNrOfFailures >= 0)
             ? ((getFailedTests() != null) && (getFailedTests().size() > $maximumNrOfFailures))
             : false;
  }
  
  public final void run() throws TestAlreadyStartedException {
    if (hasRun()) {
      throw new OLDTorytException(null, null);
    }
    else {
      setRun();
    }
    $failedTests = new ArrayList();
    StraightList tests = getContract().getTests();
    BigInteger testsToRun = tests.getBigSize();
    Date startTime = new Date();
    BigInteger testsDone = BigInteger.ZERO;
    System.out.println(INTEGER_NUMBER_FORMATTER.format(testsToRun) + " tests to run");
    Iterator iter = tests.iterator();
    Date loopTimer = new Date();
    BigInteger loopCounter = testsDone;
    while (iter.hasNext() && ! hasEnough()) {
      Test t = (Test)iter.next();
      try {
      t.run();
      if (! t.isSuccessful()) {
        $failedTests.add(t);
        System.out.println();
        t.report(System.out);
      }
      }
      catch (Throwable exc) {
        $failedTests.add(t);
        exc.printStackTrace(); 
      }
      testsDone = testsDone.add(BigInteger.ONE);
// MUDO DEBUG
//if (! tests.getBigSize().equals(testsToRun)) {
//  System.out.println("SKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPEDSKIPPED");
//  System.out.println("WARN JAN == WARN JAN == WARN JAN == WARN JAN == WARN JAN == WARN JAN == WARN JAN == WARN JAN == WARN JAN == WARN JAN");
//  System.out.println(tests.getBigSize());
//  System.out.println(testsToRun);
//}
//MUDO DEBUG
      Date loopEnd = new Date();
      if (loopEnd.getTime() - loopTimer.getTime() > 10000) {
        long loopDuration = loopEnd.getTime() - startTime.getTime();
        double loopTestsPerSecond = testsDone.subtract(loopCounter).doubleValue() / (loopDuration/ 1000.0);
        System.out.println();
        System.out.println((loopDuration / 1000)
                           + "s, "
                           + INTEGER_NUMBER_FORMATTER.format(testsDone)
                           + " tests done ("
                           + FLOAT_NUMBER_FORMATTER.format(loopTestsPerSecond)
                           + "tps)");
        BigInteger totalDo = tests.getBigSize();
        System.out.println(PERCENT_FORMATTER.format(testsDone.doubleValue() / totalDo.doubleValue())
                           + " done ("
                           + INTEGER_NUMBER_FORMATTER.format(totalDo)
                           + " tests total)");
        System.out.println("current test: " + t.toString());
        loopTimer = loopEnd;
        loopCounter = testsDone;
        System.gc();
      }
    }
    Date endTime = new Date();
    System.out.println();
// MUDO DEBUG    
//System.out.println("BIG SIZE: " + tests.getBigSize());
//  MUDO DEBUG    
    System.out.println(INTEGER_NUMBER_FORMATTER.format(testsDone)
                       + " tests done ("
                       + INTEGER_NUMBER_FORMATTER.format(testsToRun.subtract(testsDone))
                       + " tests skipped)");
    long duration = endTime.getTime() - startTime.getTime();
    System.out.println("duration: " + FLOAT_NUMBER_FORMATTER.format(duration) + "ms");
    double testsPerSecond = testsDone.doubleValue() / (duration / 1000.0);
    System.out.println("speed: " + FLOAT_NUMBER_FORMATTER.format(testsPerSecond) + "tps");
  }
  
  private static final Locale FL = new Locale("nl", "BE");
  private static final NumberFormat INTEGER_NUMBER_FORMATTER = NumberFormat.getIntegerInstance(FL);
  private static final NumberFormat FLOAT_NUMBER_FORMATTER = NumberFormat.getNumberInstance(FL);
  private static final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance(FL);

  public final boolean isSuccessful() {
    return (getFailedTests() != null) && (getFailedTests().isEmpty());
  }

  public final void report(PrintStream out) {
    out.println(isSuccessful() ? "success" : "FAILURE");
  }

  public static void main(String[] args) throws OLDTorytException {
    Option helpOption = new Option("h", "help", false, "print this message" );
    OptionBuilder.withLongOpt("maxFailures");
    OptionBuilder.withArgName("natural");
    OptionBuilder.hasOptionalArg();
    OptionBuilder.withDescription("The maximum number of failures. Default is no maximum, "
                        + "without arguments, the default is "
                        + DEFAULT_MAXIMUM_NUMBER_OF_FAILURES);
    Option maxFailuresOption = OptionBuilder.create("f");
    OptionBuilder.withLongOpt("contracts");
    OptionBuilder.withArgName("Fqn1[:Fqn2[:...]]");
    OptionBuilder.hasArgs();
    OptionBuilder.withValueSeparator(':');
    OptionBuilder.isRequired();
    OptionBuilder.withDescription("Fully qualified names of the contracts to test (separated by a ':')");
    Option contractsOption = OptionBuilder.create("c");
    Options clOptions = new Options();
    clOptions.addOption(helpOption);
    clOptions.addOption(maxFailuresOption);
    clOptions.addOption(contractsOption);
    CommandLineParser clParser = new PosixParser();
    CommandLine cl = null;
    try {
      cl = clParser.parse(clOptions, args);
    }
    catch (ParseException e) {
      System.err.println("Parsing failed.  Reason: " + e.getMessage());
      printHelp(clOptions, -1, System.err);
    }
    if (cl.hasOption("h")) {
      printHelp(clOptions, 0, System.out);
    }
    int maxFailures = -1;
    if (cl.hasOption('f')) {
      maxFailures = DEFAULT_MAXIMUM_NUMBER_OF_FAILURES;
      String maxFailuresString = cl.getOptionValue("f");
      Number maxFailuresNumber = null;
      if (maxFailuresString != null) {
        try {
          maxFailuresNumber = INTEGER_NUMBER_FORMATTER.parse(maxFailuresString);
        }
        catch (java.text.ParseException pExc) {
          System.err.println("Parsing failed.  Reason: -f not a natural");
          printHelp(clOptions, -2, System.err);
        }
        maxFailures = maxFailuresNumber.intValue();
      }
    }
    System.out.println("Starting tests.");
    System.out.println(new Date());
    if (maxFailures < 0) {
      System.out.println("Will run complete test.");
    }
    else {
      System.out.println("Will stop after "
                         + INTEGER_NUMBER_FORMATTER.format(maxFailures)
                         + " test failures.");
    }
    System.out.println("\n");
    String[] contractNames = cl.getOptionValues("c");
    for (int i = 0; i < contractNames.length; i++) {
      System.out.println("Starting test for \""
                         + contractNames[i]
                         + "\".");
      Class contractClass = instantiateClass(contractNames[i]);
      if (contractClass != null) {
        Contract contractInstance = getContractInstance(contractClass);
        if (contractInstance != null) {
          SimpleCli test = new SimpleCli(contractInstance, maxFailures);
          test.run();
        }
      }
    }
    System.out.println();
    System.out.println(new Date());
    System.out.println("Tests done.");
  }
  
  private static void printHelp(Options clOptions, int exitCode, PrintStream ps) {
    printHelp(clOptions, exitCode, new PrintWriter(ps));
  }
  
  private static void printHelp(Options clOptions, int exitCode, PrintWriter pw) {
    HelpFormatter hf = new HelpFormatter();
    hf.printHelp(72, "java " + SimpleCli.class.getName(),
                 "Toryt " + CVS_REVISION + ", " + CVS_DATE,
                 clOptions,
                 "May the force be with you.",
                 true);
//    hf.printHelp(pw, 72, "java " + SimpleCli.class.getName(),
//                 "Toryt " + CVS_REVISION + ", " + CVS_DATE,
//                 clOptions,
//                 2, 10,
//                 "May the force be with you.",
//                 true);
    System.exit(exitCode);
  }

  private static Class instantiateClass(final String clazz) {
    Class result = null;
    try {
      result = Class.forName(clazz);
    }
    catch (ClassNotFoundException cnfExc) {
      System.out.println("ERROR: class \""
                         + clazz
                         + "\" not found.\n");
    }
    catch (ExceptionInInitializerError eiiErr) {
      System.out.println("ERROR: \"" + clazz
          + "\" threw an exception during "
          + "static initialisation.\n");
      eiiErr.printStackTrace();
      System.out.println();
    }
    catch (LinkageError lErr) {
      System.out.println("ERROR: a linkage error occurred "
                         + "when loading class\""
                         + clazz + "\". This is extreme.\n");
      lErr.printStackTrace();
      System.out.println();
    }
    return result;
  }

  private static Contract getContractInstance(final Class contractClass) {
    Contract contract = null;
    try {
      contract = (Contract)contractClass.newInstance();
        // throws ClassCastException if this was not a test class
    }
    catch (ClassCastException ccExc) {
      System.out.println("ERROR: \"" + contractClass.getName()
          + "\" does not extend"
          + "\""
          + Contract.class.getName()
          + "\".\n");
    }
    catch (IllegalAccessException iaExc) {
      System.out.println("ERROR: \"" + contractClass.getName()
          + "\" no-args constructor is not public.\n");
    }
    catch (InstantiationException iExc) {
      System.out.println("ERROR: \""
          + contractClass.getName() + "\" cannot be instantiated "
          + "(class is abstract, interface, array, primitive "
          + "type, void, or does not have a no-args "
          + "constructor).\n");
    }
    catch (ExceptionInInitializerError eiiExc) {
      System.out.println("ERROR: \"" + contractClass.getName()
          + "\" no-args constructor threw an exception:");
      eiiExc.getCause().printStackTrace();
      System.out.println();
    }
    catch (SecurityException sExc) {
      System.out.println("ERROR: \"" + contractClass.getName()
          + "\" security exception during construction.");
      sExc.getCause().printStackTrace();
      System.out.println();
    }
    return contract;
  }

}