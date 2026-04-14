abstract class Animal {
    abstract fun makeSound()

    fun sleep() {
        println("Animal is sleeping")
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
    val dog = Dog()
    val cat = Cat()

    dog.makeSound()
    dog.sleep()

    cat.makeSound()
    cat.sleep()
}