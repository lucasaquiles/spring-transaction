# spring-transaction
example avoiding transactional problem with spring


@Transactionals in spring are used to prevent inconsistencies at data to data base follows the ACID (Atomicity, Consistency, Isolation, Durability) principles.

A Transaction in Spring may propagated using attribute ```propagation``` 

* Propagation.REQUIRED_NEW

Always creates a new transaction. If one transaction exists, It got suspensed and a new will be created

* Propagation.REQUIRED

That code block should be encapsulated by a transaction. This is default when no propagation is specified. It`s will use a existent transaction, if none exists, a new transaction will be created

* Propagation.PROPAGATION_NESTED

The code block will execute in a nested transaction if another transaction already exists, otherwise a new transaction will be created.
