package org.toryt.jexl;


import java.util.Map;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;
import org.toryt.Condition;
import org.toryt.TorytException;


/**
 * These conditions evaluate Strings as
 * <a href="http://jakarta.apache.org/commons/jexl/index.html" target="extern">jexl</a>
 * expressions.
 * 
 * @author Jan Dockx
 */
public class JexlCondition implements Condition {

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
   * @pre expression != null;
   * @post getExpression().equals(expression);
   * @throws TorytException
   *         ; could not parse <code>expression</code> 
   */
  public JexlCondition(String expression) throws TorytException {
    assert expression != null;
    try {
      $originalExpression = expression;
      $expression = ExpressionFactory.createExpression(expression);
    }
    catch (Exception e) {
      throw new TorytException(null, e);
    }
  }
  
  public final String getOriginalExpression() {
    return $originalExpression;
  }
  
  private String $originalExpression;
  
  /**
   * @basic
   */
  public final Expression getExpression() {
    return $expression;
  }
  
  /**
   * @invar $expression != null;
   */
  private Expression $expression;
  
  
  public boolean validate(Map context) {
    context.put("EMPTY", "");
    System.out.println("will evaluate " + toString());
    JexlContext jexlContext = JexlHelper.createContext();
    jexlContext.setVars(context);
    System.out.println("jexlContext: " + jexlContext.toString());
    Boolean result = null;
    try {
      result = (Boolean)getExpression().evaluate(jexlContext);
      System.out.println("result: " + result.toString());
    }
    catch (ClassCastException ccExc) {
      assert false : "result of expression is not Boolean";
      // MUDO should not be an assert failure
    }
    catch (Exception e) {
      assert false : "Problem evaluating expression: " + e;
      // MUDO should not be an assert failure
    }
    return result.booleanValue(); // unreachable statement
  }
  
  public String toString() {
    return getOriginalExpression();
  }

}