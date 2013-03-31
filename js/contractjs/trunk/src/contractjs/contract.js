//define([], function() {

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

  var isArray = function(/*Object*/ a) {
    return a && (a instanceof Array || typeof a == "array"); // return Boolean
  };

  var isFunction = function(/*Object*/ f) {
    return Object.prototype.toString.call(f) === "[object Function]"; // return Boolean
  };

  var isArrayOfFunctions = function(/*Object*/ af) {
    return isArray(af) && // return Boolean
      af.every(function(c) {
        return isFunction(c);
      });
  };

  var isFunctionDefinition = function(/*FunctionDefinition*/ fd) {
    return fd && // return Boolean
      isArrayOfFunctions(fd.pre) &&
      isArrayOfFunctions(fd.post) &&
      isArrayOfFunctions(fd.exc) &&
      (fd.impl ? isFunction(fd.impl) : true);
  };

  var crackInstrument = function(/*String*/ instrument) {
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
  };

  var argsToString = function(args) {
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
  };

  var ContractViolation = function(/*Object*/ subject, /*Function*/ impl, /*Array*/ args, /*Function*/ violatingCondition) {
    this.subject = subject;
    this.function = impl;
    this.args = args;
    this.violation = violatingCondition;
    this.msg = this.toString();
  };
  ContractViolation.prototype = new Error();
  ContractViolation.prototype.constructor = ContractViolation;
  ContractViolation.prototype.kindString = "ABSTRACT";
  ContractViolation.prototype.extraToString = function() {
    return null;
  };
  ContractViolation.prototype.toString = function() {
    var extra = this.extraToString();
    return this.kindString + " VIOLATION: condition " + this.violation + " failed on function " +
      this.function + " when called on " + this.subject + " with arguments " + argsToString(this.args)
      + (extra ? " " + extra : "");
  };

  var PreconditionViolation = function(/*Object*/ subject, /*Function*/ impl, /*Array*/ args, /*Function*/ violatingCondition) {
    ContractViolation.apply(this, arguments);
  };
  PreconditionViolation.prototype = new ContractViolation();
  PreconditionViolation.prototype.constructor = PreconditionViolation;
  PreconditionViolation.prototype.kindString = "PRECONDITION";

  var PostconditionViolation = function(/*Object*/ subject, /*Function*/ impl, /*Array*/ args, /*Object*/ result, /*Function*/ violatingCondition) {
    ContractViolation.call(this, subject, impl, args, violatingCondition);
    this.result = result;
  };
  PostconditionViolation.prototype = new ContractViolation();
  PostconditionViolation.prototype.constructor = PostconditionViolation;
  PostconditionViolation.prototype.kindString = "POSTCONDITION";
  PostconditionViolation.prototype.extraToString = function() {
    return "with result " + this.result;
  };

  var ExceptionViolation = function(/*Object*/ subject, /*Function*/ impl, /*Array*/ args, /*Object*/ exc, /*Function*/ violatingCondition) {
    ContractViolation.call(this, subject, impl, args, violatingCondition);
    this.exc = exc;
  };
  ExceptionViolation.prototype = new ContractViolation();
  ExceptionViolation.prototype.constructor = ExceptionViolation;
  ExceptionViolation.prototype.kindString = "EXCEPTION CONDITION";
  ExceptionViolation.prototype.extraToString = function() {
    return "with exception " + this.exc;
  };

  var validatePreconditions = function(/*Object*/ self, /*Array*/ pres, /*Array*/ args, /*Function*/ impl) {
    pres.forEach(function(pre) {
      var preResult = pre.apply(self, args);
      if (!preResult) {
        var error = new PreconditionViolation(self, impl, args, pre);
        throw error;
      }
    });
  };

  var validateNominalPostconditions = function(/*Object*/ self, /*Array*/ nomPosts, /*Array*/ args, /*Object*/ result, /*Function*/ impl) {
    nomPosts.forEach(function(npost) {
      var postResult = true; // npost.apply(self, args, result); // MUDO result must be pushed to args (which is not an array)
      if (!postResult) {
        throw new PostconditionViolation(self, impl, args, result, npost);
      }
    });
  };

  var validateExceptionalPostconditions = function(/*Object*/ self, /*Array*/ excPosts, /*Array*/ args, /*Object*/ exc, /*Function*/ impl) {
    excPosts.forEach(function(epost) {
      var postResult = true; // epost.apply(self, args, exc); // MUDO exc must be pushed to args (which is not an array)
      if (!postResult) {
        throw new ExceptionViolation(self, impl, args, exc, epost);
      }
    });
  };

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
        validatePreconditions(this, fd.pre, arguments, fd.impl);
        var result = fd.impl.apply(this, arguments);
        return result;
      };
    }
    else if (inst.instrument.pre && inst.instrument.post) {
      instrumented = function() {
        validatePreconditions(this, fd.pre, arguments, fd.impl);
        try {
          var result = fd.impl.apply(this, arguments);
          validateNominalPostconditions(this, fd.post, arguments, result, fd.impl);
          return result;
        }
        catch (exc) {
          validateExceptionalPostconditions(this, fd.exc, arguments, exc, fd.impl);
          throw exc;
        }
      };
    }
    else {
      throw "ERROR: validation of postconditions, but not preconditions, makes no sense."
    }

    if (inst.include.pre) {
      instrumented.pre = fd.pre.slice(0);
    }
    if (inst.include.post) {
      instrumented.post = fd.post.slice(0);
      instrumented.exc = fd.exc.slice(0);
    }
    return instrumented;
  };
//
//  return instrumentFunction;
//});

