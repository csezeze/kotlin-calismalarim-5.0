open class Animal {
    open fun makeSound() {
        println("Animal makes a sound")
    }
}

class Dog : Animal() {
    override fun makeSound() {
        println("Dog says: Woof woof")
    }
}

class Calculator {
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun add(a: Int, b: Int, c: Int): Int {
        return a + b + c
    }
}

fun main() {
    val dog = Dog()
    dog.makeSound()

    val calculator = Calculator()
    println(calculator.add(5, 3))
    println(calculator.add(5, 3, 2))
}