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

class Cat : Animal() {
    override fun makeSound() {
        println("Cat says: Meow")
    }
}

fun main() {
    val animal1: Animal = Dog()
    val animal2: Animal = Cat()

    animal1.makeSound()
    animal2.makeSound()
}