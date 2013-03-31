ATest = TestCase("a");

ATest.prototype.test_a = function() {
  var x = 5;
  var y = 7;
  var expected = 12;
  var result = a(5, 7);
  assertEquals(expected, result);
};

function reportPropertyNames(o) {
  var pNames = Object.getOwnPropertyNames(o);
  var filtered = pNames.filter(function(pn) {
    return pn.charAt(0) !== "_";
  });
  return filtered;
}

ATest.prototype.test_ObjectLiteral = function() {
  var newObject = {};
  assertNotNull(newObject);
  var thePrototype = Object.getPrototypeOf(newObject);
  assertNotNull(thePrototype);
  assertEquals(Object, newObject.constructor);
  assertEquals(Object, thePrototype.constructor);
  assertEquals(Object.prototype, thePrototype);
  console.log("properties of newObject: " + reportPropertyNames(newObject));
  console.log("properties of newObject prototype: " + reportPropertyNames(thePrototype));
  console.log("properties of Object (constructor): " + reportPropertyNames(Object));
};

ATest.prototype.test_ObjectConstructor = function() {
  var newObject = new Object();
  assertNotNull(newObject);
  var thePrototype = Object.getPrototypeOf(newObject);
  assertNotNull(thePrototype);
  assertEquals(Object, newObject.constructor);
  assertEquals(Object, thePrototype.constructor);
  assertEquals(Object.prototype, thePrototype);
  console.log("properties of newObject: " + reportPropertyNames(newObject));
  console.log("properties of newObject prototype: " + reportPropertyNames(thePrototype));
  console.log("properties of Object (constructor): " + reportPropertyNames(Object));
};

ATest.prototype.test_FunctionConstructor = function() {
  var newObject = new Function();
  assertNotNull(newObject);
  var thePrototype = Object.getPrototypeOf(newObject);
  assertNotNull(thePrototype);
  assertEquals(Function, newObject.constructor);
  assertEquals(Function, thePrototype.constructor);
  assertEquals(Function.prototype, thePrototype);
  console.log("properties of newObject: " + reportPropertyNames(newObject));
  console.log("properties of newObject prototype: " + reportPropertyNames(thePrototype));
  console.log("properties of Object (constructor): " + reportPropertyNames(Object));
  var thePrototypePrototype = Object.getPrototypeOf(thePrototype);
  assertNotNull(thePrototypePrototype);
  assertEquals(Object, thePrototypePrototype.constructor);
  console.log("properties of newObject prototype prototype: " + reportPropertyNames(thePrototypePrototype));
};
