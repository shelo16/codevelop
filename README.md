# codevelop
solution for test task

Things I would do differently/add if I had more time : 

* Logging using AOP
* Better parameter validation
* Create a single `Account` object and have `SavingsAccount` and `CurrentAccount` extend it
  * In this case we would have same code logic written in `Account` and different logics implemented in concrete classes 