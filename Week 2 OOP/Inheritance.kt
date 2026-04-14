open class Animal {
    fun eat() {
        println("Animal is eating")
    }
}

class Dog : Animal() {
    fun bark() {
        println("Dog is barking")
    }
}

fun main() {
    val dog1 = Dog()

    dog1.eat()
    dog1.bark()
}