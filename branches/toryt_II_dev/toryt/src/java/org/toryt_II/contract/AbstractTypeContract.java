package org.toryt_II.contract;


import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.Reflection;
import org.toryt_I.support.straightlist.ConcatStraightList;
import org.toryt_I.support.straightlist.StraightList;
import org.toryt_II.OLDTorytException;
import org.toryt_II.OLDcontract.TypeContract.AllMembersCoveredTest;
import org.toryt_II.contract.condition.Condition;


/**
 * The contract of a method. This features preconditions, postconditions
 * and exception conditions.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractTypeContract
    extends AbstractContract
    implements TypeContract {

  /**
   * @pre type != null;
   * @post new.getType() == type;
   */
  protected AbstractTypeContract(Class type) {
    try {
      addExtraTests(new AllMembersCoveredTest(this));
    }
    catch (OLDTorytException e) {
      assert false : "we should not be closed";
    }
    assert type != null;
    $type = type;
  }

  /**
   * @throws OLDTorytException
   * @pre fqn != null;
   * @post new.getType().getName().equals(fqn);
   */
  protected AbstractTypeContract(String fqn) throws OLDTorytException {
    this(Reflection.loadForName(fqn));
  }

  public final Class getType() {
    return $type;
  }

  private Class $type;



  /*<property name="type invariant conditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final Set<Condition> getTypeInvariantConditions() {
    return Collections.unmodifiableSet($typeInvariantConditions);
  }

  /**
   * @pre    condition != null;
   * @post   new.getTypeInvariantConditions().contains(condition);
   * @throws ContractIsClosedException
   *         isClosed();
   */
  public void addTypeInvariantCondition(Condition condition)
      throws ContractIsClosedException {
    assert condition != null;
    if (isClosed()) {
      throw new ContractIsClosedException(this, condition, "type invariant conditions");
    }
    $typeInvariantConditions.add(condition);
  }

  /**
   * @invar $typeInvariantConditions != null;
   * @invar ! $typeInvariantConditions.contains(null);
   */
  private Set<Condition> $typeInvariantConditions = new HashSet<Condition>();

  /*</property>*/

}