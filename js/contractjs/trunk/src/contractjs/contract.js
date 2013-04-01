/*
  var FunctionDefinition = {
    // pre: Array<Function>
    //   Mandatory array of preconditions.
    //   Preconditions are functions whose return value must be true
    //   before the function is called.
    //   The precondition-functions have the same arguments (including this)
    //   as the actual function. Their value is their state at the moment of
    //   the call. The functions should never change the state
    //   of anything.
    //   If a function is called in violation of its preconditions, the
    //   implementation of the function has no responsibility for the effects
    //   whatsoever.
    //   When there are no preconditions, this must be explicitly the empty Array.
    pre: [],

    // impl: Function
    //   The actual implementation of this function. Optional.
    //   If called with respect for the preconditions, this function must
    //   either end nominally, fulfilling the nominal postconditions, or
    //   exceptionally, fulfilling the matching exceptional postconditions.
    //   All other outcomes are a programming error.
    //   This can be null or undefined (intended to be used for abstract functions
    //   in an OO context).
    impl: null,

    // post: Array<Function>
    //   Mandatory array of nominal postconditions.
    //   Nominal postconditions are functions whose return value must be true
    //   when the function ends nominally, if it was called in accordance
    //   to the specified preconditions.
    //   The postcondition-functions have the same arguments (including this)
    //   as the actual function, with their value after impl has executed,
    //   and have access to the state that is reachable
    //   from the arguments of the function as it was just before the call.
    //   They have an extra argument added at the end of the argument list,
    //   that is the result of the implementation.
    //   The functions should never change the state
    //   of anything.
    //   TODO SYNTAX OLD
    //   When there are no nominal postconditions, this must be explicitly the
    //   empty Array.
    post: [],

    // exc: Array<Function>
    //   Mandatory array of exceptional postconditions.
    //   Exceptional postconditions are functions whose return value must be true
    //   when the function ends with an exception, if it was called in accordance
    //   to the specified preconditions.
    //   The postcondition-functions have the same arguments (including this)
    //   as the actual function, with their value after impl has executed. This has
    //   to be the same value as these arguments had just before the call (when
    //   a function throws an exception, nothing should have changed).
    //   They have an extra argument added at the end of the argument list,
    //   that is the exception that was thrown.
    //   TODO SYNTAX OLD
    //   The functions should never change the state
    //   of anything.
    //   When there are no exceptional postconditions, this must be explicitly the
    //   empty Array.
    exc: []
  }
 */
function isFunctionDefinition(/*FunctionDefinition*/ fd) {

  function isArray(/*Object*/ a) {
    return a && (a instanceof Array || typeof a == "array"); // return Boolean
  }

  function isFunction(/*Object*/ f) {
    return Object.prototype.toString.call(f) === "[object Function]"; // return Boolean
  }

  function isArrayOfFunctions(/*Object*/ af) {
    return isArray(af) && // return Boolean
      af.every(function(c) {
        return isFunction(c);
      });
  }

  return fd && // return Boolean
    isArrayOfFunctions(fd.pre) &&
    isArrayOfFunctions(fd.post) &&
    isArrayOfFunctions(fd.exc) &&
    (fd.impl ? isFunction(fd.impl) : true);
}

var ContractViolation = function(/*Object*/ subject, /*Function*/ impl, /*Array*/ args, /*Function*/ violatingCondition, /*Function*/ caller) {
  this.subject = subject;
  this.function = impl;
  this.args = args;
  this.violation = violatingCondition;
  this.caller =  caller;
  this.msg = this.toString();
};
ContractViolation.prototype = new Error();
ContractViolation.prototype.constructor = ContractViolation;
ContractViolation.prototype.kindString = "ABSTRACT";
ContractViolation.prototype.extraToString = function() {
  return null;
};
ContractViolation.prototype.toString = function() {
  
  function argsToString(args) {
    if (args === undefined) {
      return "undefined";
    }
    if (args === null) {
      return "null";
    }
    var result = "(";
    var i;
    for (i = 0; i < args.length; i++) {
      result += args[i];
      result += (i < args.length - 1) ? ", " : "";
    }
    result += ")";
    return result;
  }

  var extra = this.extraToString();
  return this.kindString + " VIOLATION: condition " + this.violation + " failed on function " +
    this.function + " when called on " + this.subject + " with arguments " + argsToString(this.args) +
    (extra ? " " + extra : "") +
    (this.caller ? " by function " + this.caller : "");
};

var PreconditionViolation = function(/*Object*/ subject, /*Function*/ impl, /*Array*/ args, /*Function*/ violatingCondition, /*Function*/ caller) {
  ContractViolation.apply(this, arguments);
};
PreconditionViolation.prototype = new ContractViolation();
PreconditionViolation.prototype.constructor = PreconditionViolation;
PreconditionViolation.prototype.kindString = "PRECONDITION";

var PostconditionViolation = function(/*Object*/ subject, /*Function*/ impl, /*Array*/ args, /*Object*/ result, /*Function*/ violatingCondition, /*Function*/ caller) {
  ContractViolation.call(this, subject, impl, args, violatingCondition, caller);
  this.result = result;
};
PostconditionViolation.prototype = new ContractViolation();
PostconditionViolation.prototype.constructor = PostconditionViolation;
PostconditionViolation.prototype.kindString = "POSTCONDITION";
PostconditionViolation.prototype.extraToString = function() {
  return "with result " + this.result;
};

var ExceptionViolation = function(/*Object*/ subject, /*Function*/ impl, /*Array*/ args, /*Object*/ exc, /*Function*/ violatingCondition, /*Function*/ caller) {
  ContractViolation.call(this, subject, impl, args, violatingCondition, caller);
  this.exc = exc;
};
ExceptionViolation.prototype = new ContractViolation();
ExceptionViolation.prototype.constructor = ExceptionViolation;
ExceptionViolation.prototype.kindString = "EXCEPTION CONDITION";
ExceptionViolation.prototype.extraToString = function() {
  return "with exception " + this.exc;
};

function noExceptionExpected() {
  return false;
}

var instrumentFunction = function(/*FunctionDefinition*/ fd, /*String*/ instrument) {
  // summary:
  //    Transforms a FunctionDefinition into a function, with optionally
  //    attached preconditions, nominal postconditions and exceptional
  //    postconditions; optionally instrumented to verify the conditions
  //    during an execution.
  // fd: FunctionDefinition
  //    The function definition. Mandatory.
  // instrument: string
  //    With this variable, you can vary whether or not contracts are verified,
  //    e.g., based on whether you are running the code in test, debug or production
  //    mode.
  //    - If null or undefined, the resulting function is fd.impl.
  //      Preconditions, nominal postconditions and exceptional postconditions
  //      are not included in the resulting function.
  //    - If "+pre", the resulting function is fd.impl.
  //      The preconditions are available in result.pre.
  //      Nominal postconditions and exceptional postconditions are not included
  //      in the resulting function.
  //    - If "+pre +post", the resulting function is fd.impl.
  //      The preconditions are available in result.pre.
  //      Nominal postconditions and exceptional postconditions are  available in
  //      result.post and result.exc respectively.
  //    - If "#pre", the resulting function is setup to verify preconditions.
  //      The preconditions are available in result.pre.
  //      Nominal postconditions and exceptional postconditions are not included
  //      in the resulting function.
  //    - If "#pre +post", the resulting function is setup to verify preconditions.
  //      The preconditions are available in result.pre.
  //      Nominal postconditions and exceptional postconditions are  available in
  //      result.post and result.exc respectively.
  //    - If "#pre #post", the resulting function is setup to verify preconditions,
  //      nominal postconditions and exceptional conditions.
  //      The preconditions are available in result.pre.
  //      Nominal postconditions and exceptional postconditions are  available in
  //      result.post and result.exc respectively.
  //    Anything else results in an error.
  // description:
  //   The result is a function with the exact same effect as impl, when all
  //   conditions are met. Optionally (see `instrument`), the precondtions,
  //   nominal and exceptional postconditions, are added as instance variables
  //   to the result, and impl is instrumented to verify the conditions.

  function crackInstrument(/*String*/ instrument) {
    if (!instrument) {
      return {
        include:    { pre:  false, post: false },
        instrument: { pre:  false, post: false }
      }
    }
    switch (instrument) {
      case "+pre":
        return {
          include:    { pre:  true, post: false },
          instrument: { pre:  false, post: false }
        };
      case "+pre +post":
        return {
          include:    { pre:  true, post: true },
          instrument: { pre:  false, post: false }
        };
      case "#pre":
        return {
          include:    { pre:  true, post: false },
          instrument: { pre:  true, post: false }
        };
      case "#pre +post":
        return {
          include:    { pre:  true, post: true},
          instrument: { pre:  true, post: false }
        };
      case "#pre #post":
        return {
          include:    { pre:  true, post: true },
          instrument: { pre:  true, post: true }
        };
      default:
        throw "ERROR: illegal instrument argument (" + instrument + ")";
    }
  }

  function methodPropertyName(self, method) {
    if (!self) {
      return undefined;
    }
    for (var pName in self) {
      if (self[pName] === method) {
        return pName;
      }
    }
    return undefined;
  }

  function methodInheritanceChain(o, methodName) {
    var result = [];
    function smRecursive(oRec, acc) {
      if (!oRec) {
        return acc;
      }
      if (oRec.hasOwnProperty(methodName)) {
        acc.push(oRec[methodName]);
      }
      var oRecPrototype = Object.getPrototypeOf(oRec);
      return smRecursive(oRecPrototype, acc);
    }
    return smRecursive(o, result);
  }

  function overrideChain(self, instrumented) {
    var methodName = methodPropertyName(self, instrumented);
    var methods = null;
    if (methodName) {
      methods = methodInheritanceChain(self, methodName);
    }
    else {
      methods = [instrumented];
    }
    return methods;
  }

  function gatherConditions(methods, conditionName) {
    var result = methods.reduce(
      function(acc, method) {
        return method[conditionName] ? acc.concat(method[conditionName]) : acc;
      },
      []
    );
    return result;
  }

  function validatePreconditions(/*Object*/ self, /*Array*/ pres, /*Array*/ args, /*Function*/ impl, /*Function*/ caller) {
    pres.forEach(function(pre) {
      var preResult = pre.apply(self, args);
      if (!preResult) {
        var error = new PreconditionViolation(self, impl, args, pre, caller);
        throw error;
      }
    });
  }

  function validateNominalPostconditions(/*Object*/ self, /*Array*/ nomPosts, /*Array*/ args, /*Object*/ result, /*Function*/ impl, /*Function*/ caller) {
    var argsArray = Array.prototype.slice.call(args); // to make it an array for sure
    argsArray.push(result);
    nomPosts.forEach(function(npost) {
      var postResult = npost.apply(self, argsArray);
      if (!postResult) {
        throw new PostconditionViolation(self, impl, args, result, npost, caller);
      }
    });
  }

  function validateExceptionalPostconditions(/*Object*/ self, /*Array*/ excPosts, /*Array*/ args, /*Object*/ exc, /*Function*/ impl, /*Function*/ caller) {
    // MUDO must be changed to an "at least one"! (or)
    var argsArray = Array.prototype.slice.call(args); // to make it an array for sure
    argsArray.push(exc);
    excPosts.forEach(function(epost) {
      var postResult = epost.apply(self, argsArray);
      if (!postResult) {
        throw new ExceptionViolation(self, impl, args, exc, epost, caller);
      }
    });
  }





  if (!isFunctionDefinition(fd)) {
    throw "ERROR: not a FunctionDefinition (" + fd + ")";
  }
  var inst = crackInstrument(instrument);

  var instrumented = null;

  if (!fd.impl) {
    instrumented = function() { throw "ERROR: abstract method called"};
    // instrumentation makes no sense
  }
  else if (!inst.instrument.pre && !inst.instrument.post) {
    instrumented = fd.impl;
  }
  else if (inst.instrument.pre && !inst.instrument.post) {
    instrumented = function() {
      // IDEA cache this
      var methods = overrideChain(this, instrumented);
      var preconditions = gatherConditions(methods, "pre");
      validatePreconditions(this, instrumented.pre, arguments, preconditions, instrumented.caller);
      var result = instrumented.impl.apply(this, arguments);
      return result;
    };
  }
  else if (inst.instrument.pre && inst.instrument.post) {
    instrumented = function() {
      // IDEA cache this
      var methods = overrideChain(this, instrumented);
      var preconditions = gatherConditions(methods, "pre");
      validatePreconditions(this, preconditions, arguments, instrumented.impl, instrumented.caller);
      try {
        var result = instrumented.impl.apply(this, arguments);
        var postconditions = gatherConditions(methods, "post");
        validateNominalPostconditions(this, postconditions, arguments, result, instrumented.impl, instrumented.caller);
        return result;
      }
      catch (exc) {
        if (! exc instanceof ContractViolation) {
          var excConditions = gatherConditions(methods, "exc");
          validateExceptionalPostconditions(this, excConditions, arguments, exc, instrumented.impl, instrumented.caller);
        }
        throw exc;
      }
    };
  }
  else {
    throw "ERROR: validation of postconditions, but not preconditions, makes no sense."
  }

  if (inst.include.pre || inst.include.post) {
    instrumented.impl = fd.impl;
  }
  if (inst.include.pre) {
    instrumented.pre = fd.pre.slice(0);
  }
  if (inst.include.post) {
    instrumented.post = fd.post.slice(0);
    instrumented.exc = fd.exc.slice(0);
    if (instrumented.exc.length === 0) {
      instrumented.exc.push(noExceptionExpected);
    }
  }
  return instrumented;
};

