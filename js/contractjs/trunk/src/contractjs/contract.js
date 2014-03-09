// This file can be loaded as a standalone JS file, or as an AMD module.
// If, on load, we cannot create a module with `define` due to a ReferenceError,
// (`define` which would be provided by requirejs or dojo, e.g.,), we create
// a global variable `_tc_` (Toryt contracts) that contains the relevant
// structures as properties.
// If we can create a module with `define`, we define a module,
// without any dependencies, that resolves to an object that
// contains the relevant structures as properties.

(function() {
  // Anonymous function to encapsulate the definition of the structures. Will be
  // executed immediately.

  function generateTorytContracts() {

    function isFunctionDefinition(/*Object*/ fd) {

      function isArray(/*Object*/ a) {
        return a && (a instanceof Array || typeof a == "array"); // return Boolean
      }

      function isFunction(/*Object*/ f) {
        return Object.prototype.toString.call(f) === "[object Function]"; // return Boolean
      }

      function isArrayOfFunctions(/*Array*/ af) {
        return isArray(af) && // return Boolean
               af.every(function(c) {
                 return isFunction(c);
               });
      }

      function isExceptionalPostcondition(epc) {
        return epc instanceof Object && // return Boolean
               isFunction(epc.when) &&
               isArrayOfFunctions(epc.then);
      }

      function isArrayOfExceptionalPostconditions(/*Array*/ aepc) {
        return isArray(aepc) && // return Boolean
               aepc.every(function(epc) {
                 return isExceptionalPostcondition(epc);
               });
      }

      return fd && // return Boolean
             isArrayOfFunctions(fd.pre) &&
             isArrayOfFunctions(fd.post) &&
             isArrayOfExceptionalPostconditions(fd.exc) &&
             (fd.impl ? isFunction(fd.impl) : true);
    }



    function methodPropertyName(self, method) {
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
    }



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





    function ContractViolation(/*Object*/ subject, /*Function*/ f, /*Array*/ args) {

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
        var mPropName = methodPropertyName(self, fct);
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
      this.subjectFunction = f ? f.impl : undefined;
      this.args = args;
      this.caller = f ? f.caller : undefined;
      this.functionRepresentation = functionRepresentation(subject, f);
      this.callerRepresentation = functionRepresentation(subject, this.caller);
    }

    ContractViolation.prototype = new Error();
    ContractViolation.prototype.constructor = ContractViolation;
    ContractViolation.prototype.kindString = "ABSTRACT";





    function ConditionViolation(/*Object*/ subject, /*Function*/ f, /*Array*/ args, /*Function*/ violatingCondition) {
      ContractViolation.call(this, subject, f, args);
      this.violation = violatingCondition;
      this.msg = this.toString();
    }

    ConditionViolation.prototype = new ContractViolation();
    ConditionViolation.prototype.constructor = ConditionViolation;
    ConditionViolation.prototype.extraToString = function() {
      return null;
    };
    ConditionViolation.prototype.toString = function() {
      var extra = this.extraToString();
      return this.kindString + " VIOLATION:\ncondition\n" + this.violation + "\nfailed on function\n" +
             this.functionRepresentation + "\nwhen called on " + this.subject + "\nwith arguments " +
             argsToString(this.args) + (extra ? "\n" + extra : "") +
             (this.caller ? "\nby function\n" + this.callerRepresentation : "");
    };





    function PreconditionViolation(/*Object*/ subject, /*Function*/ f, /*Array*/ args, /*Function*/ violatingCondition) {
      ConditionViolation.apply(this, arguments);
    }

    PreconditionViolation.prototype = new ConditionViolation();
    PreconditionViolation.prototype.constructor = PreconditionViolation;
    PreconditionViolation.prototype.kindString = "PRECONDITION";





    function PostconditionViolation(/*Object*/ subject, /*Function*/ f, /*Array*/ args, /*Object*/ result, /*Function*/ violatingCondition) {
      ConditionViolation.call(this, subject, f, args, violatingCondition);
      this.result = result;
    }

    PostconditionViolation.prototype = new ConditionViolation();
    PostconditionViolation.prototype.constructor = PostconditionViolation;
    PostconditionViolation.prototype.kindString = "POSTCONDITION";
    PostconditionViolation.prototype.extraToString = function() {
      return "with result " + this.result;
    };





    function ExceptionViolation(/*Object*/ subject, /*Function*/ f, /*Array*/ args, /*Object*/ exc, /*Function*/ violatingCondition) {
      ConditionViolation.call(this, subject, f, args, violatingCondition);
      this.exc = exc;
    }

    ExceptionViolation.prototype = new ConditionViolation();
    ExceptionViolation.prototype.constructor = ExceptionViolation;
    ExceptionViolation.prototype.kindString = "EXCEPTION CONDITION";
    ExceptionViolation.prototype.extraToString = function() {
      return "with exception " + this.exc;
    };





    function UnexpectedException(/*Object*/ subject, /*Function*/ f, /*Array*/ args, /*Object*/ exc) {
      ContractViolation.call(this, subject, f, args, exc);
      this.exc = exc;
      this.msg = this.toString();
    }

    UnexpectedException.prototype = new ContractViolation();
    UnexpectedException.prototype.constructor = UnexpectedException;
    UnexpectedException.prototype.kindString = "UNEXPECTED EXCEPTION";
    UnexpectedException.prototype.toString = function() {
      return this.kindString + " (CONTRACT VIOLATION):\n" + this.exc +
             "\noccured on function\n" + this.functionRepresentation + "\nwhen called on " +
             this.subject + "\nwith arguments " + argsToString(this.args) +
             (this.caller ? "\nby function\n" + this.callerRepresentation : "");
    };




    function buildf(/*Object*/ fd, /*String*/ instrument) {
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

      function validatePreconditions(/*Object*/ self, /*Array*/ pres, /*Array*/ args, /*Function*/ f) {
        pres.forEach(function(pre) {
          var preResult = pre.apply(self, args);
          if (!preResult) {
            var error = new PreconditionViolation(self, f, args, pre);
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
            throw new PostconditionViolation(self, f, args, result, nPost);
          }
        });
      }

      function validateExceptionalPostconditions(/*Object*/ self, /*Array*/ excPosts, /*Array*/ args, /*Object*/ exc, /*Function*/ f) {
        var applicable = excPosts.filter(function(excPost) {
          return excPost.when.call(self, exc);
        });
        if (applicable.length <= 0) {
          // we did not expect this exception
          throw new UnexpectedException(self, f, args, exc);
        }
        // we only use the first matching excPost
        var argsArray = Array.prototype.slice.call(args); // to make it an array for sure
        argsArray.push(exc);
        applicable[0].then.forEach(function(ePost) {
          var postResult = ePost.apply(self, argsArray);
          if (!postResult) {
            throw new ExceptionViolation(self, f, args, exc, ePost);
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
          validatePreconditions(this, preconditions, arguments, instrumented);
          var result = instrumented.impl.apply(this, arguments);
          return result;
        };
      }
      else if (inst.instrument.pre && inst.instrument.post) {
        instrumented = function() {
          // IDEA cache this
          var methods = overrideChain(this, instrumented);
          var preconditions = gatherConditions(methods, "pre");
          validatePreconditions(this, preconditions, arguments, instrumented);
          try {
            var result = instrumented.impl.apply(this, arguments);
            var postconditions = gatherConditions(methods, "post");
            validateNominalPostconditions(this, postconditions, arguments, result, instrumented);
            return result;
          }
          catch (exc) {
            if (!(exc instanceof ContractViolation)) {
              var excConditions = gatherConditions(methods, "exc");
              validateExceptionalPostconditions(this, excConditions, arguments, exc, instrumented);
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
      }
      return instrumented;
    }





    var _tc_ = {
      // summary:
      //   Toryt contracts.
      // description:
      //   _tc_ is intended as a encapsulation namespace, not really as an object.

      /*
      FunctionDefinition: {
        // pre: Function[]
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

        // post: Function[]
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

        // exc: Function[]
        //   Mandatory array of exceptional postconditions.
        //   When the function was called in accordance to the specified preconditions,
        //   and the call does not end nominally, but throws an exception,
        //   at least one of the exceptional postconditions must evaluate truthy.
        //
        //   Exceptional postconditions are objects, with 2 properties:
        //   - when: a function that takes the exception as an argument; it returns
        //           true if this set of conditions applies to this exception
        //   - then: an array of functions, which all must return true for
        //           exceptions for which `when` returns true
        //   The then-functions have the same arguments (including this)
        //   as the actual function, with their value after impl has executed. This has
        //   to be the same value as these arguments had just before the call (when
        //   a function throws an exception, nothing should have changed).
        //   They have an extra argument added at the end of the argument list,
        //   that is the exception that was thrown.
        //   TODO SYNTAX OLD
        //   The functions should never change the state of anything.
        //   When there are no exceptional postconditions, this must be explicitly the
        //   empty Array.
        exc: []
      }
      */

      isFunctionDefinition: isFunctionDefinition,
      methodPropertyName: methodPropertyName,
      argsToString: argsToString,

      ContractViolation: ContractViolation,
      ConditionViolation: ConditionViolation,
      PreconditionViolation: PreconditionViolation,
      PostconditionViolation: PostconditionViolation,
      ExceptionViolation: ExceptionViolation,
      UnexpectedException: UnexpectedException,

      buildf: buildf
    };

    return _tc_;
  }

  try {
    console.log("contractjs: trying to define as AMD");
    define([], function() {
      console.log("contractjs: loading as AMD");
      return generateTorytContracts();
    });
    // if this succeeds, we had an AMD loader, and we defined a module
  }
  catch (re) {
    // if this fails, we generate the structures, and put them in a global variable
    console.log("contractjs: failed to define as AMD");
    if (re instanceof ReferenceError) {
      console.log("contractjs: defining in _tc_ global variable");
      //noinspection JSUndeclaredVariable
      _tc_ = generateTorytContracts(); /*global*/
    }
  }

})();
