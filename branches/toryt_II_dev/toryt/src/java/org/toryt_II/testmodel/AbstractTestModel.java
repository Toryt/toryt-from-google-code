package org.toryt_II.testmodel;


import java.io.PrintStream;
import java.util.Iterator;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;




/**
 * Implementation of a number of methods of {@link TestModel}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractTestModel<_SubjectType_> implements TestModel<_SubjectType_> {

  public final _SubjectType_ getSubject() {
    return $subject;
  }

  /**
   * @post new.getSubject() == subject;
   */
  public void setSubject(_SubjectType_ subject) {
    $subject = subject;
  }

  /**
   * @invar $subject != null;
   */
  private _SubjectType_ $subject;

  public final String toString() {
    return getClass().getName() + "[" + getSubject() + "]";
  }

  /**
   * Human-readable name for the {@link #getSubject()}.
   *
   * @basic
   */
  protected final String getSubjectDisplayName() {
    return (getSubject() == null) ? "null" : getSubjectDisplayNameSave();
  }

  /**
   * Human-readable name for the {@link #getSubject()}.
   *
   * @basic
   * @pre getSubject() != null;
   */
  protected abstract String getSubjectDisplayNameSave();

  /**
   * Implementation that uses package accessible code
   * to write compound test model contents indented, recursively.
   */
  public void printStructure(PrintStream out) {
    assert out != null;
    printStructure(new IndentPrinter(out, 0));
  }

  /**
   * @pre out != null;
   * @pre depth >= 0;
   */
  abstract void printStructure(IndentPrinter indentPrinter);

  /**
   * Class of instances to ease printing structured textual
   * representation of a tree. The implementation of
   * {@link #printStructure(IndentPrinter)} should write
   * information using the current test model node using
   * <code>println</code>. After that, it should create a new
   * <code>IndentPrinter</code>, with the current
   * <code>IndentPrinter</code> as {@link #getPrevious()},
   * and call {@link #printChildren(String, Set)} on that
   * <code>IndentPrinter</code> for all its kinds of children,
   * that will then be rendered in different sections.
   *
   * @todo This was created as needed, but looks interesting,
   *       and should be generalized. This is a bad design
   *       as it is, since IndentPrinter depends on AbstractTestModel,
   *       and it should only depend on the TestModel interface.
   */
  static class IndentPrinter {

    public IndentPrinter(PrintStream out, int nrOfEntries) {
      $out = out;
      $nrOfEntriesLeft = nrOfEntries;
    }

    public IndentPrinter(IndentPrinter previous, int nrOfEntries) {
      this(previous.getPrintStream(), nrOfEntries);
      $previous = previous;
    }

    public PrintStream getPrintStream() {
      return $out;
    }

    private PrintStream $out;

    public final IndentPrinter getPrevious() {
      return $previous;
    }

    private IndentPrinter $previous;

    private int $nrOfEntriesLeft;

    private void printIndentSameEntry() {
      if ($previous != null) {
        $previous.printIndentSameEntry();
      }
      if ($nrOfEntriesLeft > 0) {
        $out.print(CONTINUING_ENTRY_INDENT);
      }
      else if ($nrOfEntriesLeft == 0) {
        $out.print(BLANK_INDENT);
      }
      else { // < 0
        assert false;
      }
    }

    private void printIndent() {
      if ($previous != null) {
        $previous.printIndentSameEntry();
      }
      if ($nrOfEntriesLeft > 1) {
        $out.print(ENTRY_INDENT);
      }
      else if ($nrOfEntriesLeft == 1) {
        $out.print(LAST_ENTRY_INDENT);
      }
      // < 1; no indent
      if ($nrOfEntriesLeft > 0) {
        $nrOfEntriesLeft--;
      }
    }

    public void println(String string) {
      printIndent();
      $out.println(string);
    }

    public void println(Object obj) {
      printIndent();
      $out.println(obj);
    }

    public void printChildren(String sectionHeading, Set testModels) {
      println(sectionHeading);
      IndentPrinter indent = new IndentPrinter(this, testModels.size());
      Iterator iter = testModels.iterator();
      while (iter.hasNext()) {
        AbstractTestModel tm = (AbstractTestModel)iter.next();
        tm.printStructure(indent);
      }

    }

  }

  private final static String BLANK_INDENT = "    ";

  private final static String ENTRY_INDENT = "  |-";

  private final static String CONTINUING_ENTRY_INDENT = "  | ";

  private final static String LAST_ENTRY_INDENT = "  `-";

}