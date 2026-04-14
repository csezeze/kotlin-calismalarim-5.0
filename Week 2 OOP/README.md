\# Week 2 - OOP in Kotlin



This folder has simple Kotlin examples for basic OOP concepts.



\## Files



\### 1. Encapsulation

\*\*File:\*\* `Encapsulation.kt`



The `grade` variable is `private`, so it cannot be changed directly from outside the class.  

I use `setGrade()` and `getGrade()` to control it.



\*\*Output:\*\*

```text

Student grade: 85

Invalid grade

Student grade: 85



This shows that wrong values are not accepted.





\### 2. Inheritance

\*\*File:\*\* `Inheritance.kt`



`Dog` inherits from `Animal`.  

So `Dog` can use `eat()` from `Animal` and also its own `bark()` function.



\*\*Output:\*\*

```text

Animal is eating

Dog is barking

This shows that a child class can use parent class features.



\## 3. Polymorphism



```md

\### 3. Polymorphism

\*\*File:\*\* `Polymorphism.kt`



`Dog` and `Cat` both use `makeSound()`, but they give different outputs.



\*\*Output:\*\*

```text

Dog says: Woof woof

Cat says: Meow

This shows the same function name with different behavior.



\## 4. Abstraction



```md

\### 4. Abstraction

\*\*File:\*\* `Abstraction.kt`



`Animal` is an abstract class.  

`makeSound()` must be written in child classes.  

`sleep()` is a normal function.



\*\*Output:\*\*

```text

Dog says: Woof woof

Animal is sleeping

Cat says: Meow

Animal is sleeping

This shows that abstract classes can have both abstract and normal functions.



\## 5. Override vs Overloading



```md

\### 5. Override vs Overloading

\*\*File:\*\* `OverrideVsOverloading.kt`



\- \*\*Override:\*\* `Dog` changes `makeSound()`

\- \*\*Overloading:\*\* `Calculator` has two `add()` functions



\*\*Output:\*\*

```text

Dog says: Woof woof

8

10

This shows the difference between override and overloading.

