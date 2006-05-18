package org.toryt_II.OLDcontract.hard;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.toryt_I.support.straightlist.ConcatStraightList;
import org.toryt_I.support.straightlist.LazyMappingStraightList;
import org.toryt_I.support.straightlist.NullFirstStraightList;
import org.toryt_I.support.straightlist.StraightList;
import org.toryt_II.OLDTorytException;
import org.toryt_II.cases.CaseProvider;
import org.toryt_II.main.Contracts;


/**
 * @author Jan Dockx
 */
public abstract class HardClassContract
    extends HardTypeContract implements org.toryt_II.OLDcontract.ClassContract, CaseProvider {

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

  /**
   * @pre ! type.isInterface();
   */
  public HardClassContract(Class type) {
    super(type);
    assert ! type.isInterface();
    initDirectSuperClassContract();
  }

  /**
   * @pre ! type.isInterface();
   */
  public HardClassContract(String fqn) throws OLDTorytException {
    super(fqn);
  }

  public final org.toryt_II.OLDcontract.ClassContract getDirectSuperClassContract() {
    return $directSuperClassContract;
  }
  
  private void initDirectSuperClassContract() {
    if (getType() == Object.class) {
      /* We don't need a superclass contract, since class Object doesn't
       * have a superclass.
       */
      return;
    }
    Class superClass = getType().getSuperclass();
    if (superClass == null) {
      assert false : "Can't be, every class but Object has a superclass.";
    }
    try {
      $directSuperClassContract = (HardClassContract)Contracts.typeContractInstance(superClass);
    }
    catch (IOException e) {
      // MUDO better output
      e.printStackTrace(System.out);
      assert false;
    }
    catch (ClassCastException e) {
      // MUDO better output
      e.printStackTrace(System.out);
      assert false : "expected class contract";
    }
    catch (ClassNotFoundException e) {
      // there should be a contract, but there is none
      // MUDO log this as a test failure, or a warning, or something
      System.out.println("No contract for superclass " + superClass.getName());
    }
  }
   
  private org.toryt_II.OLDcontract.ClassContract $directSuperClassContract;
  
  public final Set getConstructorContracts() {
    return Collections.unmodifiableSet($constructorContracts);
  }
  
  /**
   * @pre    cc != null;
   * @throws OLDTorytException
   *         isClosed();
   * @throws OLDTorytException
   *         ! cc.isClosed();
   */
  public final void addConstructorContract(HardConstructorContract cc) throws OLDTorytException {
    assert cc != null;
    if (isClosed()) {
      throw new OLDTorytException(this, null);
    }
    if (! cc.isClosed()) {
      throw new OLDTorytException(this, null);
    }
    $constructorContracts.add(cc);
  }
  
  private Set $constructorContracts = new HashSet();
  
  /**
   *
   */

  public StraightList getMethodTests() throws OLDTorytException {
    StraightList[] lists = new StraightList[getConstructorContracts().size() + 1];
    Iterator iter = getConstructorContracts().iterator();
    int i = 0;
    while (iter.hasNext()) {
      HardConstructorContract cc = (HardConstructorContract)iter.next();
      lists[i] = cc.getMethodTests();
      i++;
    }
    lists[i] = super.getMethodTests();
    return new ConcatStraightList(lists);
  }
  
  /**
   * Return a list of {@link Map} instances, that contain
   * combinations with wich we can generate a test instance
   * with {@link #getCaseMapping()}.
   * @throws OLDTorytException
   */
  public abstract StraightList getCasesMaps() throws OLDTorytException;
  
  public abstract LazyMappingStraightList.Mapping getCaseMapping();
  
  /**
   * All possible relevant cases for this type,
   * with <code>null</code>.
   * @throws OLDTorytException
   */
  public final NullFirstStraightList getCasesWithNull() throws OLDTorytException {
    return new NullFirstStraightList(getCases());
  }

  /**
   * All possible relevant cases for this type,
   * without <code>null</code>.
   * @throws OLDTorytException
   */
  public final StraightList getCases() throws OLDTorytException {
    return new LazyMappingStraightList(getCasesMaps(), getCaseMapping());
  }

  /**
   * A limited number of most important cases for this type,
   * without <code>null</code>.
   * @throws OLDTorytException
   */
  public final NullFirstStraightList getSomeCasesWithNull() throws OLDTorytException {
    return new NullFirstStraightList(getSomeCases());
  }

  /**
   * A limited number of most important cases for this type,
   * with <code>null</code>.
   * @throws OLDTorytException
   */
  public StraightList getSomeCases() throws OLDTorytException {
    return getCases();
  }

  public final Set getSubContracts() {
    Set result = super.typeSubContracts();
    result.addAll($constructorContracts);
    return Collections.unmodifiableSet(result);
  }
  
}