ContractTest = TestCase("contracts of functions");

ContractTest.prototype.test_module_load = function(queue) {
  assertFunction(window.instrumentFunction);
};

ContractTest.prototype.setUp = function() {
  this.definition = {
    pre: [
      function(x, y) {return typeof x === "number";},
      function(x, y) {return x >= 0;},
      function(x, y) {return typeof y === "number";},
      function(x, y) {return y > 0;}
    ],
    impl: function(x, y) {
      return x / y;
    },
    post: [
      function(x, y, result) {return typeof result === "number";},
      function(x, y, result) {return result >= 0;},
      function(x, y, result) {return result * y === x;}
    ],
    exc: [
    ]
  };
};

ContractTest.prototype.tearDown = function() {
  delete this.definition;
};

ContractTest.prototype.test_contract_def_nopre_nopost = function(queue) {
  var result = instrumentFunction(this.definition);
  assertFunction(result);
  assertEquals(this.definition.impl, result);
  assertUndefined(result.pre);
  assertUndefined(result.post);
  assertUndefined(result.exc);
  var execResult = result(1, 2);
  assertEquals(0.5, execResult);
};

ContractTest.prototype.test_contract_def_pre_nopost = function(queue) {
  var result = instrumentFunction(this.definition, "+pre");
  assertFunction(result);
  assertEquals(this.definition.impl, result);
  assertNotUndefined(result.pre);
  assertEquals(this.definition.pre, result.pre);
  assertTrue(this.definition.pre !== result.pre);
  assertUndefined(result.post);
  assertUndefined(result.exc);
  var execResult = result(1, 2);
  assertEquals(0.5, execResult);
};

ContractTest.prototype.test_contract_def_pre_post = function(queue) {
  var result = instrumentFunction(this.definition, "+pre +post");
  assertFunction(result);
  assertEquals(this.definition.impl, result);
  assertNotUndefined(result.pre);
  assertEquals(this.definition.pre, result.pre);
  assertTrue(this.definition.pre !== result.pre);
  assertNotUndefined(result.post);
  assertEquals(this.definition.post, result.post);
  assertTrue(this.definition.post !== result.post);
  assertNotUndefined(result.exc);
  assertEquals([noExceptionExpected], result.exc);
  assertTrue(this.definition.exc !== result.exc);
  var execResult = result(1, 2);
  assertEquals(0.5, execResult);
};

ContractTest.prototype.test_contract_exec_pre_nopost_ok = function(queue) {
  var result = instrumentFunction(this.definition, "#pre");
  assertFunction(result);
  assertNotEquals(this.definition.impl, result);
  assertNotUndefined(result.pre);
  assertEquals(this.definition.pre, result.pre);
  assertTrue(this.definition.pre !== result.pre);
  assertUndefined(result.post);
  assertUndefined(result.exc);
  var execResult = result(1, 2);
  assertEquals(0.5, execResult);
};

ContractTest.prototype.test_contract_exec_pre_nopost_nok = function(queue) {
  var result = instrumentFunction(this.definition, "#pre");
  assertFunction(result);
  assertNotEquals(this.definition.impl, result);
  assertNotUndefined(result.pre);
  assertEquals(this.definition.pre, result.pre);
  assertTrue(this.definition.pre !== result.pre);
  assertUndefined(result.post);
  assertUndefined(result.exc);
  try {
    var execResult = result(1, "two");
    fail();
  }
  catch (e) {
    assertInstanceOf(PreconditionViolation, e);
    console.log(e);
  }
};

ContractTest.prototype.test_contract_exec_pre_post_ok = function(queue) {
  var result = instrumentFunction(this.definition, "#pre #post");
  assertFunction(result);
  assertNotEquals(this.definition.impl, result);
  assertNotUndefined(result.pre);
  assertEquals(this.definition.pre, result.pre);
  assertTrue(this.definition.pre !== result.pre);
  assertNotUndefined(result.post);
  assertEquals(this.definition.post, result.post);
  assertTrue(this.definition.post !== result.post);
  assertNotUndefined(result.exc);
  assertEquals([noExceptionExpected], result.exc);
  assertTrue(this.definition.exc !== result.exc);
  var execResult = result(1, 2);
  assertEquals(0.5, execResult);
};

ContractTest.prototype.test_contract_exec_pre_post_nok = function(queue) {
  var definition = {
    pre: [
      function(x, y) {return typeof x === "number";},
      function(x, y) {return x >= 0;},
      function(x, y) {return typeof y === "number";},
      function(x, y) {return y > 0;}
    ],
    impl: function(x, y) {
      return 5 / y;
    },
    post: [
      function(x, y, result) {return typeof result === "number";},
      function(x, y, result) {return result >= 0;},
      function(x, y, result) {return result * y === x;}
    ],
    exc: [
    ]
  };
  var result = instrumentFunction(definition, "#pre #post");
  assertFunction(result);
  assertNotEquals(definition.impl, result);
  assertNotUndefined(result.pre);
  assertEquals(definition.pre, result.pre);
  assertTrue(definition.pre !== result.pre);
  assertNotUndefined(result.post);
  assertEquals(definition.post, result.post);
  assertTrue(definition.post !== result.post);
  assertNotUndefined(result.exc);
  assertEquals([noExceptionExpected], result.exc);
  assertTrue(definition.exc !== result.exc);
  try {
    var execResult = result(1, 2);
    fail();
  }
  catch (e) {
    assertInstanceOf(PostconditionViolation, e);
    console.log(e);
  }
};

ContractTest.prototype.test_contract_exec_pre_post_exc_nok = function(queue) {
  var definition = {
    pre: [
      function(x, y) {return typeof x === "number";},
      function(x, y) {return x >= 0;},
      function(x, y) {return typeof y === "number";},
      function(x, y) {return y > 0;}
    ],
    impl: function(x, y) {
      throw "UNEXPECTED ERROR";
    },
    post: [
      function(x, y, result) {return typeof result === "number";},
      function(x, y, result) {return result >= 0;},
      function(x, y, result) {return result * y === x;}
    ],
    exc: [
    ]
  };
  var result = instrumentFunction(definition, "#pre #post");
  assertFunction(result);
  assertNotEquals(definition.impl, result);
  assertNotUndefined(result.pre);
  assertEquals(definition.pre, result.pre);
  assertTrue(definition.pre !== result.pre);
  assertNotUndefined(result.post);
  assertEquals(definition.post, result.post);
  assertTrue(definition.post !== result.post);
  assertNotUndefined(result.exc);
  assertEquals([noExceptionExpected], result.exc);
  assertTrue(definition.exc !== result.exc);
  try {
    var execResult = result(1, 2);
    fail();
  }
  catch (e) {
    assertInstanceOf(ExceptionViolation, e);
    console.log(e);
  }
};

ContractTest.prototype.test_contract_exec_pre_post_exc_nok = function(queue) {
  var definition = {
    pre: [
      function(x, y) {return typeof x === "number";},
      function(x, y) {return typeof y === "number";}
    ],
    impl: function(x, y) {
      if (y === 0) {
        throw "DIV/0"
      }
      return x / y;
    },
    post: [
      function(x, y, result) {return typeof result === "number";},
      function(x, y, result) {return result * y === x;}
    ],
    exc: [
      function(x, y, exc) {return exc === "DIV/0" && y === 0;}
    ]
  };
  var result = instrumentFunction(definition, "#pre #post");
  assertFunction(result);
  assertNotEquals(definition.impl, result);
  assertNotUndefined(result.pre);
  assertEquals(definition.pre, result.pre);
  assertTrue(definition.pre !== result.pre);
  assertNotUndefined(result.post);
  assertEquals(definition.post, result.post);
  assertTrue(definition.post !== result.post);
  assertNotUndefined(result.exc);
  assertEquals(definition.exc, result.exc);
  assertTrue(definition.exc !== result.exc);
  try {
    var execResult = result(1, 0);
    fail();
  }
  catch (e) {
    assertEquals("DIV/0", e);
    console.log(e);
  }
};