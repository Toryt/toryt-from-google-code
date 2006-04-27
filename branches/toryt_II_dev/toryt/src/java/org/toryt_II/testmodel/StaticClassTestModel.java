package org.toryt_II.testmodel;


import org.toryt.util_I.reflect.Reflection.TypeKind;


/**
 * Test model for top level and static nested classes.
 * Apart from the child test models offered by
 * {@link ClassTestModel}, static classes also can have
 * {@link StaticClassTestModel static nested classes}.
 *
 * @author Jan Dockx
 *
 * @invar getTypeKind() == TypeKind.STATIC;
 * @invar staticNestedClassTestModels != null;
 */
public class StaticClassTestModel extends ClassTestModel {

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



  public StaticClassTestModel() {
    super(TypeKind.STATIC);
  }

  public final TestModelCollectionDelegate<StaticClassTestModel> staticNestedClassTestModels =
    new TestModelCollectionDelegate<StaticClassTestModel>(this);

  {
    addTestModelCollectionDelegate("static nested classes", staticNestedClassTestModels);
  }

}