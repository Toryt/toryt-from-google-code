package org.toryt.hard;


import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.toryt.CaseProvider;
import org.toryt.TorytException;
import org.toryt.support.straightlist.ConcatStraightList;
import org.toryt.support.straightlist.LazyMappingStraightList;
import org.toryt.support.straightlist.NullFirstStraightList;
import org.toryt.support.straightlist.StraightList;


/**
 * @author Jan Dockx
 */
public abstract class ClassContract
    extends TypeContract implements org.toryt.ClassContract, CaseProvider {

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
  public ClassContract(Class type) {
    super(type);
    assert ! type.isInterface();
  }

  /**
   * @pre ! type.isInterface();
   */
  public ClassContract(String fqn) throws TorytException {
    super(fqn);
  }

  public final ClassContract getSuperClassContract() {
    return $superClassContract;
  }
  
  /**
   * @pre cc != null;
   * @post new.getSuperClassContract() != null;
   */
  protected final void setSuperClassContract(ClassContract cc) {
    assert cc != null;
    $superClassContract = cc;
  }
  
  /**
   * @pre fqn != null;
   */
  protected final void setSuperClassContract(String fqn) throws TorytException {
    assert fqn != null;
//  Class si = Class.forName(fqn with prefix contract; look in specific package);
  }
  
  
  
  private ClassContract $superClassContract;
  
  public final Set getConstructorContracts() {
    return Collections.unmodifiableSet($constructorContracts);
  }
  
  /**
   * @pre    cc != null;
   * @throws TorytException
   *         isClosed();
   * @throws TorytException
   *         ! cc.isClosed();
   */
  public final void addConstructorContract(ConstructorContract cc) throws TorytException {
    assert cc != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    if (! cc.isClosed()) {
      throw new TorytException(this, null);
    }
    $constructorContracts.add(cc);
  }
  
  private Set $constructorContracts = new HashSet();
  
  /**
   *
   */

  public StraightList getMethodTests() throws TorytException {
    StraightList[] lists = new StraightList[getConstructorContracts().size() + 1];
    Iterator iter = getConstructorContracts().iterator();
    int i = 0;
    while (iter.hasNext()) {
      ConstructorContract cc = (ConstructorContract)iter.next();
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
   * @throws TorytException
   */
  public abstract StraightList getCasesMaps() throws TorytException;
  
  public abstract LazyMappingStraightList.Mapping getCaseMapping();
  
  /**
   * All possible relevant cases for this type,
   * with <code>null</code>.
   * @throws TorytException
   */
  public final NullFirstStraightList getCasesWithNull() throws TorytException {
    return new NullFirstStraightList(getCases());
  }

  /**
   * All possible relevant cases for this type,
   * without <code>null</code>.
   * @throws TorytException
   */
  public final StraightList getCases() throws TorytException {
    return new LazyMappingStraightList(getCasesMaps(), getCaseMapping());
  }

  /**
   * A limited number of most important cases for this type,
   * without <code>null</code>.
   * @throws TorytException
   */
  public final NullFirstStraightList getSomeCasesWithNull() throws TorytException {
    return new NullFirstStraightList(getSomeCases());
  }

  /**
   * A limited number of most important cases for this type,
   * with <code>null</code>.
   * @throws TorytException
   */
  public StraightList getSomeCases() throws TorytException {
    return getCases();
  }

}