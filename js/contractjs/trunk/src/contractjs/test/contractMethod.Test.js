ContractTest = TestCase("contracts of overrideChain");

var epoch = new Date(0);
var epochYear = epoch.getFullYear();

ContractTest.prototype.setUp = function() {
  var Person = _tc_.buildf(
    {
      pre: [
        function(name, dob) {return name && typeof name === "string";},
        function(name, dob) {return dob && dob instanceof Date;},
        function(name, dob) {return dob < Date.now();}
      ],
      impl: function(/*String*/ name, /*Date*/ dob) {
        this.name = name;
        this.dob = dob;
      },
      post: [
        function(name, dob) {return this.name === name;},
        function(name, dob) {return this.dob === dob;}
      ],
      exc: []
    },
    "#pre #post"
  );
  Person.prototype.constructor = Person;
  Person.prototype.ageAt = _tc_.buildf(
    {
      pre: [
        function(d) {return d && d instanceof Date;}
      ],
      impl: function(/*Date*/ d) {
        if (d < this.dob) {
          throw "d must be later than dob";
        }
        var age = d - this.dob;
        return new Date(age).getFullYear() - epochYear; // return Number
      },
      post: [
        function(d, result) {return typeof result === "number";},
        function(d, result) {return this.dob.getFullYear() + result + 1 >= d.getFullYear();},
        function(d, result) {return d.getFullYear() - result >= this.dob.getFullYear();}
      ],
      exc: [
        function(d, exc) {return exc instanceof String && d < this.dob;}
      ]
    },
    "#pre #post"
  );
  Person.prototype.age = _tc_.buildf(
    {
      pre: [],
      impl: function() {
        return this.ageAt(new Date());  // return Number
      },
      post: [
        function(result) {return result && typeof result === "number";},
        function(result) {return result === this.ageAt(new Date());}
      ],
      exc: []
    },
    "#pre #post"
  );
  Person.prototype.toString = _tc_.buildf(
    {
      pre: [],
      impl: function() {
        return "name: " + this.name + ", dob " + this.dob; // return String
      },
      post: [
        function(result) {return typeof result === "string" && result != "";}
      ],
      exc: []
    },
    "#pre #post"
  );
  Person.prototype.wrongImpl = _tc_.buildf(
    {
      pre: [],
      impl: function() {
        return new Date(this.dob.getFullYear() + 5, this.dob.getMonth(), this.dob.getDate());
      },
      post: [
        function(result) {return result ===  this.dob;}
      ],
      exc: []
    },
    "#pre #post"
  );
  Person.prototype.fullName = _tc_.buildf(
    {
      pre: [],
      impl: function() {
        return this.name;
      },
      post: [
        function(result) {return result.indexOf(this.name) >= 0;}
      ],
      exc: []
    },
    "#pre #post"
  );
  Person.prototype.getFirstName = _tc_.buildf(
    {
      pre: [],
      post: [
        function(result) {return typeof result === "string";}
      ],
      exc: []
    },
    "#pre #post"
  );
  this.Person = Person;
};

ContractTest.prototype.tearDown = function() {
  delete this.Person;
};

ContractTest.prototype.test_classObject_ok = function() {
  try {
    var p = new this.Person("Jan", new Date(1966, 9, 3));
    var ageAt = p.ageAt(new Date(2013, 2, 31));
    assertEquals(46, ageAt);
    ageAt = p.ageAt(new Date(2013, 9, 3));
    assertEquals(47, ageAt);
    var age = p.age();
    var asString = p.toString();
    console.log(asString);
    console.log(p);
    var fullName = p.fullName();
  }
  catch (e) {
    fail(e);
  }
};

ContractTest.prototype.test_classObject_nok1 = function() {
  try {
    var future = new Date();
    future.setFullYear(future.getFullYear() + 5);
    var p = new this.Person("Jan", future);
    fail(p);
  }
  catch (e) {
    assertInstanceOf(_tc_.PreconditionViolation, e);
    console.log(e);
  }
};

ContractTest.prototype.test_classObject_nok2 = function() {
  try {
    var p = new this.Person("Jan", new Date(1966, 9, 3));
    var result = p.wrongImpl();
    fail(p);
  }
  catch (e) {
    assertInstanceOf(_tc_.PostconditionViolation, e);
    console.log(e);
  }
};

ContractTest.prototype.test_classObject_abstract = function() {
  try {
    var p = new this.Person("Jan", new Date(1966, 9, 3));
    var result = p.getFirstName();
    fail(p);
  }
  catch (e) {
    assertEquals("ERROR: abstract method called", e);
    console.log(e);
  }
};

ContractTest.prototype.test_classObject__inherit_ok = function() {
  var Person = this.Person;
  var Person2 = _tc_.buildf(
    {
      pre: [
        function(firstName, lastName, dob) {return firstName && typeof firstName === "string";},
        function(firstName, lastName, dob) {return lastName && typeof lastName === "string";},
        function(firstName, lastName, dob) {return dob && dob instanceof Date;},
        function(firstName, lastName, dob) {return dob < Date.now();}
      ],
      impl: function(/*String*/ firstName, /*String*/ lastName, /*Date*/ dob) {
        Person.call(this, lastName, dob);
        this.firstName = firstName;
      },
      post: [
        function(firstName, lastName, dob) {return this.firstName === firstName;},
        function(firstName, lastName, dob) {return this.name === lastName;},
        function(firstName, lastName, dob) {return this.dob === dob;}
      ],
      exc: []
    },
    "#pre #post"
  );
  Person2.prototype = new Person("DEFAULT NAME", epoch);
  Person2.prototype.constructor = Person2;
  Person2.prototype.fullName = _tc_.buildf(
    {
      pre: [],
      impl: function() {
        return this.firstName + " " + this.name;
      },
      post: [
        function(result) {return result.indexOf(this.firstName) >= 0;}
      ],
      exc: []
    },
    "#pre #post"
  );
  var p = new Person2("Jan", "Dockx", new Date(1966, 9, 3));
  var result = p.fullName();
  console.log(result);
};

ContractTest.prototype.test_classObject__inherit_nok1 = function() {
  var Person = this.Person;
  var Person2 = _tc_.buildf(
    {
      pre: [
        function(firstName, lastName, dob) {return firstName && typeof firstName === "string";},
        function(firstName, lastName, dob) {return lastName && typeof lastName === "string";},
        function(firstName, lastName, dob) {return dob && dob instanceof Date;},
        function(firstName, lastName, dob) {return dob < Date.now();}
      ],
      impl: function(/*String*/ firstName, /*String*/ lastName, /*Date*/ dob) {
        Person.call(this, lastName, dob);
        this.firstName = firstName;
      },
      post: [
        function(firstName, lastName, dob) {return this.firstName === firstName;},
        function(firstName, lastName, dob) {return this.name === lastName;},
        function(firstName, lastName, dob) {return this.dob === dob;}
      ],
      exc: []
    },
    "#pre #post"
  );
  Person2.prototype = new Person("DEFAULT NAME", epoch);
  Person2.prototype.constructor = Person2;
  Person2.prototype.fullName = _tc_.buildf(
    {
      pre: [],
      impl: function() {
        return this.firstName;
      },
      post: [
        function(result) {return result.indexOf(this.firstName) >= 0;}
      ],
      exc: []
    },
    "#pre #post"
  );
  var p = new Person2("Jan", "Dockx", new Date(1966, 9, 3));
  try {
    var result = p.fullName();
    fail(result);
  }
  catch (e) {
    assertInstanceOf(_tc_.PostconditionViolation, e);
    console.log(e);
  }
};

ContractTest.prototype.test_classObject__inherit_nok2 = function() {
  var Person = this.Person;
  var Person2 = _tc_.buildf(
    {
      pre: [
        function(firstName, lastName, dob) {return firstName && typeof firstName === "string";},
        function(firstName, lastName, dob) {return lastName && typeof lastName === "string";},
        function(firstName, lastName, dob) {return dob && dob instanceof Date;},
        function(firstName, lastName, dob) {return dob < Date.now();}
      ],
      impl: function(/*String*/ firstName, /*String*/ lastName, /*Date*/ dob) {
        Person.call(this, lastName, dob);
        this.firstName = firstName;
      },
      post: [
        function(firstName, lastName, dob) {return this.firstName === firstName;},
        function(firstName, lastName, dob) {return this.name === lastName;},
        function(firstName, lastName, dob) {return this.dob === dob;}
      ],
      exc: []
    },
    "#pre #post"
  );
  Person2.prototype = new Person("DEFAULT NAME", epoch);
  Person2.prototype.constructor = Person2;
  Person2.prototype.ageAt = _tc_.buildf(
    {
      pre: [], // means no extra preconditions
      impl: function(/*Date*/ d) { // actually weakens preconditions: not allowed
        var age = d - this.dob;
        return new Date(age).getFullYear() - epochYear; // return Number
      },
      post: [],
      exc: []
    },
    "#pre #post"
  );
  var p = new Person2("Jan", "Dockx", new Date(1966, 9, 3));
  try {
    var result = p.ageAt(null);
    fail(result);
  }
  catch (e) {
    assertInstanceOf(_tc_.PreconditionViolation, e);
    console.log(e);
  }
};

ContractTest.prototype.test_classObject__inherit_abstract_ok = function() {
  var Person = this.Person;
  var Person2 = _tc_.buildf(
    {
      pre: [
        function(firstName, lastName, dob) {return firstName && typeof firstName === "string";},
        function(firstName, lastName, dob) {return lastName && typeof lastName === "string";},
        function(firstName, lastName, dob) {return dob && dob instanceof Date;},
        function(firstName, lastName, dob) {return dob < Date.now();}
      ],
      impl: function(/*String*/ firstName, /*String*/ lastName, /*Date*/ dob) {
        Person.call(this, lastName, dob);
        this.firstName = firstName;
      },
      post: [
        function(firstName, lastName, dob) {return this.firstName === firstName;},
        function(firstName, lastName, dob) {return this.name === lastName;},
        function(firstName, lastName, dob) {return this.dob === dob;}
      ],
      exc: []
    },
    "#pre #post"
  );
  Person2.prototype = new Person("DEFAULT NAME", epoch);
  Person2.prototype.constructor = Person2;
  Person2.prototype.getFirstName = _tc_.buildf(
    {
      pre: [],
      impl: function() {return this.firstName;},
      post: [],
      exc: []
    },
    "#pre #post"
  );
  var p = new Person2("Jan", "Dockx", new Date(1966, 9, 3));
  var result = p.getFirstName();
  assertEquals("Jan", result);
};

ContractTest.prototype.test_classObject__inherit_abstract_nok = function() {
  var Person = this.Person;
  var Person2 = _tc_.buildf(
    {
      pre: [
        function(firstName, lastName, dob) {return firstName && typeof firstName === "string";},
        function(firstName, lastName, dob) {return lastName && typeof lastName === "string";},
        function(firstName, lastName, dob) {return dob && dob instanceof Date;},
        function(firstName, lastName, dob) {return dob < Date.now();}
      ],
      impl: function(/*String*/ firstName, /*String*/ lastName, /*Date*/ dob) {
        Person.call(this, lastName, dob);
        this.firstName = firstName;
      },
      post: [
        function(firstName, lastName, dob) {return this.firstName === firstName;},
        function(firstName, lastName, dob) {return this.name === lastName;},
        function(firstName, lastName, dob) {return this.dob === dob;}
      ],
      exc: []
    },
    "#pre #post"
  );
  Person2.prototype = new Person("DEFAULT NAME", epoch);
  Person2.prototype.constructor = Person2;
  Person2.prototype.getFirstName = _tc_.buildf(
    {
      pre: [],
      impl: function() {return 5;},
      post: [],
      exc: []
    },
    "#pre #post"
  );
  try {
    var p = new Person2("Jan", "Dockx", new Date(1966, 9, 3));
    var result = p.getFirstName();
  }
  catch (e) {
    assertInstanceOf(_tc_.PostconditionViolation, e);
    console.log(e);
  }
};


