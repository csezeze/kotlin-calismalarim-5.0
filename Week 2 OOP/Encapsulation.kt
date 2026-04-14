class Student {
    private var grade = 0

    fun setGrade(newGrade: Int) {
        if (newGrade >= 0 && newGrade <= 100) {
            grade = newGrade
        } else {
            println("Invalid grade")
        }
    }

    fun getGrade(): Int {
        return grade
    }
}

fun main() {
    val student1 = Student()

    student1.setGrade(85)
    println("Student grade: ${student1.getGrade()}")

    student1.setGrade(150)
    println("Student grade: ${student1.getGrade()}")
}