package org.toryt.patterns_I;


import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.CannotGetPropertyException;
import org.toryt.util_I.reflect.CannotGetValueException;
import org.toryt.util_I.reflect.Reflection;


/**
 * Often used contracts for unidirectional and bidirectional associations.
 *
 * @author    Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class Associations {

  private Associations() {
    // NOP
  }

  /**
   * This method gathers the type invariants for a reference that implements
   * the to-one part of a bidirectional one-to-many association. The reference
   * is optional or mandatory (weak type). For a bidirectional associaton
   * {@code A <-*as---b1-> B}, this method should be used as a type invariant
   * in class {@code A}. For a mandatory association, e.g., the invariant should
   * be {@code @invar bidrNTo1(getB(), "as", this, true);}. The invariant then
   * expresses that, for all instances {@code a} of {@code A}, the instance
   * {@code a.getB()} refers to has a {@link Set getAs()} that contains the instance
   * {@code a}.
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
   *
   * @todo use graphic representation here
   */
  public static final <_Many_, _One_> boolean bidirNTo1(final _One_ reference,
                                                        final String toManySetPropertyName,
                                                        final _Many_ manyObject,
                                                        final boolean mandatory) {
    assert manyObject != null;
    boolean result = false;
    if (reference == null) {
      result = !mandatory;
    }
    else { // reference != null
      try {
        assert Reflection.hasPropertyReadMethod(reference.getClass(),
                                                toManySetPropertyName);
      }
      catch (CannotGetPropertyException e) {
        assert false : "CannotGetPropertyException should not happen: " + e;
      }
      try {
        @SuppressWarnings("unchecked") Set<_Many_> manySet =
            (Set<_Many_>)Reflection.getPropertyValue(reference, toManySetPropertyName);
        result = manySet.contains(manyObject);
      }
      catch (CannotGetValueException e) {
        assert false : "CannotGetValueException should not happen: " + e;
      }
    }
    return result;
  }

  /**
   * This method gathers the type invariants for a {@link Set} that implements
   * the to-many part of a bidirectional one-to-many association. For a
   * bidirectional associaton {@code A <-*as---b1-> B}, this method should be used
   * as a type invariant in class {@code B}. The invariant should
   * be {@code @invar bidr1ToN(getAs(), "b", this);}. The invariant then
   * expresses that, for all instances {@code b} of {@code B}, for each reference {@code a}
   * contained in the {@link Set} {@code b.getAs()}, {@code a.getB()} refers to {@code b}.
   *
   * @param     manySet
   *            The {@link Set} that is to contain many references to instances of
   *            type <code>manyType</code>. This set is owned by <code>oneObject</code>.
   * @param     toOneReferencePropertyName
   *            The name of the property that holds the to-one reference
   *            on instances of <code>manyType</code> in <code>manySet</code>.
   *            This property should refer to <code>oneObject</code>.
   *            <code>_Many_</code> must have a getter for this property
   *            that returns a reference to an instance of the type of
   *            <code>oneObject</code>.
   * @param     oneObject
   *            The instance on the one-side of the one-to-many association,
   *            that owns <code>manySet</code>.
   * @pre       Beans.hasPropertyReadMethod(manyType, toOneReferenceProperty);
   * @return    (manySet != null)
   *            && cC:noNull(manySet)
   *            && (forall _Many_ o; manySet.contains(o);
   *                    o[toOneReferencePropertyName] == oneObject);
   */
  public static final <_One_, _Many_> boolean bidir1ToN(final Set<_Many_> manySet,
                                                       final String toOneReferencePropertyName,
                                                       final _One_ oneObject) {
    boolean result = (manySet != null) && (!manySet.contains(null));
    if (result) {
      for (_Many_ manyObject : manySet) {
        try {
          if (Reflection.getPropertyValue(manyObject, toOneReferencePropertyName) != oneObject) {
            return false; // break
          }
          // else continue
        }
        catch (CannotGetValueException e) {
          assert false : "CannotGetValueException should not happen: " + e;
        }
      }
      // if we get here, result is still true
    }
    return result;
  }

  /**
   * This method gathers the postconditions for the setter on the many-side
   * of a bidirectional one-to-many association. If the old and the new
   * <code>oneObject</code> are the same, nothing changes. For a bidirectional associaton
   * {@code A <-*as---b1-> B}, this postcondition should be applied to a method
   * {@code A.setB(B )} as {@code @invar bidir1ToN(new.getB(), getB(), b, "as", this},
   * This means that, for the instance @{a} of type {@code A}, the old associated
   * {@code B} instance {@code a.getB()} no longer is associated with {@code a},
   * i.e., {@code a.getB().getAs()} no longer contains {@code a}, and the new
   * associated {@code B} instance {@code b} is returned by {@code new a.getB()}
   * and {@code a} is contained in the set {@code b.getAs()} in the new state.
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
   * @param   manySetPropertyName
   *          The name of the property that holds the to-many reference
   *          on instances of the <dfn>oneObject</dfn>.
   *          This property should return a {@link Set} instance,
   *          that contains references to the <code>manyObject</code>s.
   *          This property of the <code>oldOneObject</code> should no
   *          longer contain a reference to <code>manyObject</code>,
   *          while this property of the <code>newOneObject</code> should
   *          contain a reference to <code>manyObject</code>.
   * @param   manyObject
   *          The instance on the many-side of the one-to-many association,
   *          that holds a <code>reference</code> to the <dfn>oneObject</code>.
   * @pre     manyObject != null;
   * @pre     Beans.hasPropertyReadMethod(oldOneReference, manySetPropertyName);
   * @pre     Beans.hasPropertyReadMethod(newOneReference, manySetPropertyName);
   * @result  (newOneObject != oldOneObject)
   *              ? ((reference == newOneObject)
   *                    && ((oldOneObject != null)
   *                        ? (! oldOneObject[manySetPropertyName]
   *                                    .contains(manyObject)))
   *                    && ((newOneObject != null)
   *                        ? (newOneObject[manySetPropertyName]
   *                                    .contains(manyObject))));
   */
  public static final <_One_, _Many_> boolean bidir1ToN(final _One_ reference,
                                                        final _One_ oldOneObject,
                                                        final _One_ newOneObject,
                                                        final String manySetPropertyName,
                                                        final _Many_ manyObject) {
    assert manyObject != null;
    if (newOneObject == oldOneObject) {
      return true;
    }
    boolean result = (reference == newOneObject);
    if (result && (oldOneObject != null)) {
      try {
        @SuppressWarnings("unchecked") Set<_Many_> oldManySet
            = (Set<_Many_>)Reflection.getPropertyValue(oldOneObject, manySetPropertyName);
        result &= (! oldManySet.contains(manyObject));
      }
      catch (NullPointerException e) {
        assert false : "NullPointerExceptionshould not happen: " + e;
      }
      catch (CannotGetValueException e) {
        assert false : "CannotGetValueException should not happen: " + e;
      }
    }
    if (result && (newOneObject != null)) {
      try {
        @SuppressWarnings("unchecked") Set<_Many_> newManySet
            = (Set<_Many_>)Reflection.getPropertyValue(newOneObject, manySetPropertyName);
        result &= (newManySet.contains(manyObject));
      }
      catch (NullPointerException e) {
        assert false : "NullPointerException should not happen: " + e;
      }
      catch (CannotGetValueException e) {
        assert false : "CannotGetValueException should not happen: " + e;
      }
    }
    return result;
  }

}
