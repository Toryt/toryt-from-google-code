ContractTest = TestCase("contracts of methods");

ContractTest.prototype.setUp = function() {
  var epochYear = new Date(0).getFullYear();
  var Person = instrumentFunction(
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
  Person.prototype.ageAt = instrumentFunction(
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
  Person.prototype.age = instrumentFunction(
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
  Person.prototype.toString = instrumentFunction(
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
  Person.prototype.wrongImpl = instrumentFunction(
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
  this.Person = Person;
};

ContractTest.prototype.tearDown = function() {
  delete this.Person;
};

ContractTest.prototype.test_classobject_ok = function() {
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
  }
  catch (e) {
    fail(e);
  }
};

ContractTest.prototype.test_classobject_nok1 = function() {
  try {
    var future = new Date();
    future.setFullYear(future.getFullYear() + 5);
    var p = new this.Person("Jan", future);
    fail(p);
  }
  catch (e) {
    assertInstanceOf(PreconditionViolation, e);
    console.log(e);
  }
};

ContractTest.prototype.test_classobject_nok2 = function() {
  try {
    var p = new this.Person("Jan", new Date(1966, 9, 3));
    var result = p.wrongImpl();
    fail(p);
  }
  catch (e) {
    assertInstanceOf(PostconditionViolation, e);
    console.log(e);
  }
};

