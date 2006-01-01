package org.toryt.patterns_I;


import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.toryt.support.Reflection;


/**
 * Often used contracts for unidirectional and bidirectional associations.
 *
 * @author    Jan Dockx
 * 
 * @toryt:cC Collections;
 */
public class Associations {

  /*<section name="Meta Information">*/
  //------------------------------------------------------------------

  /** {@value} */
  public static final String CVS_REVISION = "$Revision$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_DATE = "$Date$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_STATE = "$State$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_TAG = "$Name$"; //$NON-NLS-1$

  /*</section>*/



  private Associations() {
    // NOP
  }
  
  /**
   * This method gathers the type invariants for a reference that implements
   * the to-one part of a bidirectional one-to-many association. The reference
   * is optional or mandatory (weak type).
   *
   * @param     reference
   *            The object that <code>manyObject</code> refers to, that is on
   *            the one-side of the many-to-one association.
   * @param     toManySetPropertyName
   *            The name of the property that holds the to-many reference
   *            on <code>reference</code>.
   *            This property should contain <code>manyObject</code>.
   *            <code>reference</code> must have a getter for this property
   *            that returns a <code>Set</code>.
   * @param     manyObject
   *            The instance on the many-side of the many-to-one association,
   *            that owns <code>reference</code>.
   * @param     mandatory
   *            Is participation of <code>manyObject</code> into the many-to-one
   *            association mandatory? I.e., is <code>manyObject</code> of a
   *            weak type? In other words, must <code>reference</code> be
   *            non-<code>null</code>?
   * @pre       Beans.hasPropertyReadMethod(reference.getClass(),
   *                                        toManySetPropertyName);
   * @pre       manyObject != null;
   * @return    (mandatory ==> (reference != null))
   *            && ((reference != null) ==>
   *                    reference[toManySetPropertyName].contains(manyObject));
   */
  public static final boolean bidirNTo1(final Object reference,
                                        final String toManySetPropertyName,
                                        final Object manyObject,
                                        final boolean mandatory) {
    assert manyObject != null;
    boolean result;
    if (reference == null) {
      result = !mandatory;
    }
    else { // reference != null
      try {
        assert Reflection.hasPropertyReadMethod(reference.getClass(),
                                           toManySetPropertyName);
      }
      catch (IntrospectionException iExc) {
        assert false : "IntrospectionExceptionshould not happen: " + iExc; //$NON-NLS-1$
      }
      Set manySet = null;
      try {
        manySet = (Set)Reflection.getPropertyValue(reference, toManySetPropertyName);
      }
      catch (NullPointerException npExc) {
        assert false : "NullPointerExceptionshould not happen: " + npExc; //$NON-NLS-1$
      }
      catch (IntrospectionException iExc) {
        assert false : "IntrospectionExceptionshould not happen: " + iExc; //$NON-NLS-1$
      }
      catch (NoSuchMethodException nsmExc) {
        assert false : "NoSuchMethodExceptionshould not happen: " + nsmExc; //$NON-NLS-1$
      }
      catch (IllegalAccessException iaExc) {
        assert false : "IllegalAccessExceptionshould not happen: " + iaExc; //$NON-NLS-1$
      }
      catch (InvocationTargetException itExc) {
        assert false : "InvocationTargetExceptionshould not happen: " + itExc; //$NON-NLS-1$
      }
      result = manySet.contains(manyObject);
    }
    return result;
  }

  /**
   * This method gathers the type invariants for a set that implements
   * the to-many part of a bidirectional one-to-many association.
   *
   * @param     manyCollection
   *            The {@link Collection} that is to contain many references to instances of
   *            type <code>manyType</code>. This set is owned by <code>oneObject</code>.
   * @param     manyType
   *            The type on the many side of the one-to-many association.
   * @param     toOneReferencePropertyName
   *            The name of the property that holds the to-one reference
   *            on instances of <code>manyType</code> in <code>manySet</code>.
   *            This property should refer to <code>oneObject</code>.
   *            <code>manyType</code> must have a getter for this property
   *            that returns a reference to an instance of the type of
   *            <code>oneObject</code>.
   * @param     oneObject
   *            The instance on the one-side of the one-to-many association,
   *            that owns <code>manySet</code>.
   * @pre       manyType != null;
   * @pre       Beans.hasPropertyReadMethod(manyType, toOneReferenceProperty);
   * @return    (manySet != null)
   *            && cC:noNull(manySet)
   *            && cC:instanceOf(manyType)
   *            && (forall Object o; manySet.contains(o);
   *                    o[toOneReferencePropertyName] == oneObject);
   */
  public static final boolean bidir1ToN(final Collection manyCollection,
                                        final Class manyType,
                                        final String toOneReferencePropertyName,
                                        final Object oneObject) {
    assert manyType != null;
    boolean result = (manyCollection != null) && (!manyCollection.contains(null));
    if (result) {
      Iterator iter = manyCollection.iterator();
      while (iter.hasNext()) {
        Object manyObject = iter.next();
        try {
          if ((!manyType.isInstance(manyObject))
              || (Reflection.getPropertyValue(manyObject, toOneReferencePropertyName)
                      != oneObject)) {
            return false; // break
          }
          // else continue
        }
        catch (NullPointerException npExc) {
          assert false : "NullPointerExceptionshould not happen: " + npExc; //$NON-NLS-1$
        }
        catch (IntrospectionException iExc) {
          assert false : "IntrospectionExceptionshould not happen: " + iExc; //$NON-NLS-1$
        }
        catch (NoSuchMethodException nsmExc) {
          assert false : "NoSuchMethodExceptionshould not happen: " + nsmExc; //$NON-NLS-1$
        }
        catch (IllegalArgumentException iaExc) {
          assert false : "IllegalArgumentExceptionshould not happen: " + iaExc; //$NON-NLS-1$
        }
        catch (IllegalAccessException iaExc) {
          assert false : "IllegalAccessExceptionshould not happen: " + iaExc; //$NON-NLS-1$
        }
        catch (InvocationTargetException itExc) {
          assert false : "InvocationTargetExceptionshould not happen: " + itExc; //$NON-NLS-1$
        }
      }
      // else result is still true
    }
    return result;
  }

  /**
   * This method gathers the postconditions for the setter on the many-side
   * of a bidirectional one-to-many association. If the old and the new
   * <code>oneObject</code> are the same, nothing changes.
   *
   * @param   reference
   *          The object the <code>manyObject</code>refers to; this is the
   *          getter of the <code>manyObject</code> that should return the
   *          <code>newOneObject</code>; the result of applying the basic
   *          inspector that expresses the relationship on <code>manyObject</code>.
   *          This may be <code>null</code>.
   * @param   oldOneObject
   *          The object <code>manyObject</code> refered to in the old state,
   *          before the mutator was called. This may be <code>null</code>.
   * @param   newOneObject
   *          The object <code>manyObject</code> should refer to in the new state,
   *          after the mutator is called. This may be <code>null</code>.
   *          This is the actual parameter of the setter.
   * @param   manyCollectionPropertyName
   *          The name of the property that holds the to-many reference
   *          on instances of the <dfn>oneObject</dfn>.
   *          This property should return a {@link Collection} instance,
   *          that contains references to the <code>manyObject</code>s.
   *          This property of the <code>oldOneObject</code> should no
   *          longer contain a reference to <code>manyObject</code>,
   *          while this property of the <code>newOneObject</code> should
   *          contain a reference to <code>manyObject</code>.
   * @param   manyObject
   *          The instance on the many-side of the one-to-many association,
   *          that holds a <code>reference</code> to the <dfn>oneObject</code>.
   * @pre     manyObject != null;
   * @pre     Beans.hasPropertyReadMethod(oldOneReference, manyCollectionPropertyName);
   * @pre     Beans.hasPropertyReadMethod(newOneReference, manyCollectionPropertyName);
   * @result  (newOneObject != oldOneObject)
   *              ? ((reference == newOneObject)
   *                    && ((oldOneObject != null)
   *                        ? (! oldOneObject[manyCollectionPropertyName]
   *                                    .contains(manyObject)))
   *                    && ((newOneObject != null)
   *                        ? (newOneObject[manyCollectionPropertyName]
   *                                    .contains(manyObject))));
   */
  public static final boolean bidir1ToN(final Object reference,
                                        final Object oldOneObject,
                                        final Object newOneObject,
                                        final String manyCollectionPropertyName,
                                        final Object manyObject) {
    assert manyObject != null;
    if (newOneObject == oldOneObject) {
      return true;
    }
    boolean result = (reference == newOneObject);
    if (result && (oldOneObject != null)) {
      try {
        Collection oldManyCollection
            = (Collection)Reflection.getPropertyValue(oldOneObject, manyCollectionPropertyName);
        result &= (! oldManyCollection.contains(manyObject));
      }
      catch (NullPointerException e) {
        assert false : "NullPointerExceptionshould not happen: " + e;
      }
      catch (IntrospectionException e) {
        assert false : "IntrospectionExceptionshould not happen: " + e;
      }
      catch (NoSuchMethodException e) {
        assert false : "NoSuchMethodExceptionshould not happen: " + e;
      }
      catch (IllegalAccessException e) {
        assert false : "IllegalAccessExceptionshould not happen: " + e;
      }
      catch (InvocationTargetException e) {
        assert false : "InvocationTargetExceptionshould not happen: " + e;
      }
    }
    if (result && (newOneObject != null)) {
      try {
        Collection newManyCollection
            = (Collection)Reflection.getPropertyValue(newOneObject, manyCollectionPropertyName);
        result &= (newManyCollection.contains(manyObject));
      }
      catch (NullPointerException e) {
        assert false : "NullPointerExceptionshould not happen: " + e;
      }
      catch (IntrospectionException e) {
        assert false : "IntrospectionExceptionshould not happen: " + e;
      }
      catch (NoSuchMethodException e) {
        assert false : "NoSuchMethodExceptionshould not happen: " + e;
      }
      catch (IllegalAccessException e) {
        assert false : "IllegalAccessExceptionshould not happen: " + e;
      }
      catch (InvocationTargetException e) {
        assert false : "InvocationTargetExceptionshould not happen: " + e;
      }
    }
    return result;
  }

}
