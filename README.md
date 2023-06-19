# codevelop
solution for test task

# Technologies: 
* Java Version : 17
* Spring Boot Version : 3.1.0
* Maven

# Comment
Things I would do differently/add if I had more time : 

* Logging using AOP
* Better parameter validation
* Create a single `Account` object and have `SavingsAccount` and `CurrentAccount` extend it
  * In this case we would have same code logic written in `Account` and different logics implemented in concrete classes 
* `UpdateDate` and `CreationDate` would be `@PrePersist` and `@PreUpdate` , in our case I would write this logic inside `Account` object's constructor
