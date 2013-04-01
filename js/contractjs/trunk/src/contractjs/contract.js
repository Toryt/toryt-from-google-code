var _tc_ = {
  // summary:
  //   Toryt contracts.
  // description:
  //   _tc_ is intended as a encapsulation namespace, not really as an object.
  //   (On the way to AMD).

  /*
  FunctionDefinition: {
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

  isFunctionDefinition: function(/*Object*/ fd) {

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
  },

  methodPropertyName: function(self, method) {
    if (!self) {
      return undefined;
    }
    for (var pName in self) {
      //noinspection JSUnfilteredForInLoop
      if (self[pName] === method) {
        //noinspection JSUnfilteredForInLoop
        return pName;
      }
    }
    return undefined;
  },

  ContractViolation: function(/*Object*/ subject, /*Function*/ f, /*Array*/ args, /*Function*/ violatingCondition) {

    function functionRepresentation(/*Object*/ self, /*Function*/ fct) {
      if (!fct) {
        return undefined;
      }
      if (!self) {
        if (fct.impl) {
          return fct.impl.toString();
        }
        return fct.toString();
      }
      var mPropName = _tc_.methodPropertyName(self, fct);
      if (mPropName) {
        return mPropName;
      }
      if (fct.name) {
        return fct.name;
      }
      if (fct.nom) {
        return fct.nom;
      }
      if (fct.impl) {
        return fct.impl.toString();
      }
      else {
        return fct.toString();
      }
    }

    this.subject = subject;
    this.function = f ? f.impl : undefined;
    this.args = args;
    this.violation = violatingCondition;
    this.caller = f ? f.caller : undefined;
    this.functionRepresentation = functionRepresentation(subject, f);
    this.callerRepresentation = functionRepresentation(subject, this.caller);
    this.msg = this.toString();
  },

  PreconditionViolation: function(/*Object*/ subject, /*Function*/ f, /*Array*/ args, /*Function*/ violatingCondition) {
    _tc_.ContractViolation.apply(this, arguments);
  },

  PostconditionViolation: function(/*Object*/ subject, /*Function*/ f, /*Array*/ args, /*Object*/ result, /*Function*/ violatingCondition) {
    _tc_.ContractViolation.call(this, subject, f, args, violatingCondition);
    this.result = result;
  },

  ExceptionViolation: function(/*Object*/ subject, /*Function*/ f, /*Array*/ args, /*Object*/ exc, /*Function*/ violatingCondition) {
    _tc_.ContractViolation.call(this, subject, f, args, violatingCondition);
    this.exc = exc;
  },

  noExceptionExpected: function () {
    // Exception handling needs to be changed to OR. Then this will be removed.
    return false;
  },

  buildf: function(/*Object*/ fd, /*String*/ instrument) {
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
    //   conditions are met. Optionally (see `instrument`), the preconditions,
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

    function methodInheritanceChain(o, methodName) {
      // summary:
      //   All the methods in the prototype chain of `o`,
      //   starting with `o`, with name `methodName`,
      //   except when `methodName === "constructor"`.
      //   In that case, only the first encountered method
      //   of that name.
      if (methodName === "constructor") {
        return o["constructor"] ? [o["constructor"]] : [];
      }
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
      var methodName = _tc_.methodPropertyName(self, instrumented);
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

    function validatePreconditions(/*Object*/ self, /*Array*/ pres, /*Array*/ args, /*Function*/ f) {
      pres.forEach(function(pre) {
        var preResult = pre.apply(self, args);
        if (!preResult) {
          var error = new _tc_.PreconditionViolation(self, f, args, pre);
          throw error;
        }
      });
    }

    function validateNominalPostconditions(/*Object*/ self, /*Array*/ nomPosts, /*Array*/ args, /*Object*/ result, /*Function*/ f) {
      var argsArray = Array.prototype.slice.call(args); // to make it an array for sure
      argsArray.push(result);
      nomPosts.forEach(function(nPost) {
        var postResult = nPost.apply(self, argsArray);
        if (!postResult) {
          throw new _tc_.PostconditionViolation(self, f, args, result, nPost);
        }
      });
    }

    function validateExceptionalPostconditions(/*Object*/ self, /*Array*/ excPosts, /*Array*/ args, /*Object*/ exc, /*Function*/ f) {
      // MUDO must be changed to an "at least one"! (or)
      var argsArray = Array.prototype.slice.call(args); // to make it an array for sure
      argsArray.push(exc);
      excPosts.forEach(function(ePost) {
        var postResult = ePost.apply(self, argsArray);
        if (!postResult) {
          throw new _tc_.ExceptionViolation(self, f, args, exc, ePost);
        }
      });
    }





    if (!_tc_.isFunctionDefinition(fd)) {
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
        validatePreconditions(this, preconditions, arguments, instrumented, instrumented.caller);
        var result = instrumented.impl.apply(this, arguments);
        return result;
      };
    }
    else if (inst.instrument.pre && inst.instrument.post) {
      instrumented = function() {
        // IDEA cache this
        var methods = overrideChain(this, instrumented);
        var preconditions = gatherConditions(methods, "pre");
        validatePreconditions(this, preconditions, arguments, instrumented, instrumented.caller);
        try {
          var result = instrumented.impl.apply(this, arguments);
          var postconditions = gatherConditions(methods, "post");
          validateNominalPostconditions(this, postconditions, arguments, result, instrumented, instrumented.caller);
          return result;
        }
        catch (exc) {
          if (! exc instanceof _tc_.ContractViolation) {
            var excConditions = gatherConditions(methods, "exc");
            validateExceptionalPostconditions(this, excConditions, arguments, exc, instrumented, instrumented.caller);
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
        instrumented.exc.push(_tc_.noExceptionExpected);
      }
    }
    return instrumented;
  }
};

_tc_.ContractViolation.prototype = new Error();
_tc_.ContractViolation.prototype.constructor = _tc_.ContractViolation;
_tc_.ContractViolation.prototype.kindString = "ABSTRACT";
_tc_.ContractViolation.prototype.extraToString = function() {
  return null;
};
_tc_.ContractViolation.prototype.toString = function() {

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
  return this.kindString + " VIOLATION:\ncondition\n" + this.violation + "\nfailed on function\n" +
    this.functionRepresentation + "\nwhen called on " + this.subject + "\nwith arguments " + argsToString(this.args) +
    (extra ? "\n" + extra : "") +
    (this.caller ? "\nby function\n" + this.callerRepresentation : "");
};

_tc_.PreconditionViolation.prototype = new _tc_.ContractViolation();
_tc_.PreconditionViolation.prototype.constructor = _tc_.PreconditionViolation;
_tc_.PreconditionViolation.prototype.kindString = "PRECONDITION";

_tc_.PostconditionViolation.prototype = new _tc_.ContractViolation();
_tc_.PostconditionViolation.prototype.constructor = _tc_.PostconditionViolation;
_tc_.PostconditionViolation.prototype.kindString = "POSTCONDITION";
_tc_.PostconditionViolation.prototype.extraToString = function() {
  return "with result " + this.result;
};

_tc_.ExceptionViolation.prototype = new _tc_.ContractViolation();
_tc_.ExceptionViolation.prototype.constructor = _tc_.ExceptionViolation;
_tc_.ExceptionViolation.prototype.kindString = "EXCEPTION CONDITION";
_tc_.ExceptionViolation.prototype.extraToString = function() {
  return "with exception " + this.exc;
};


