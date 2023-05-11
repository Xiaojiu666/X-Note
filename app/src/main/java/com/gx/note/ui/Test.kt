package com.gx.note.ui


fun main() {
    val tom = Person("Tom", 19, null)
    val nulTom: Student? = null
    nulTom?.also {

    }?.person
    var name = with(tom) {

        111
    }
    println("tom person info ${name}")
    val alsoTom = tom.also { person ->
        person.age = 20
    }
    val applyTom = tom.apply {
        age = 20
    }
    val letTom = tom.let { person ->
        person.age = 20
        Student(person)
    }
    val runTom = tom.run {
        age = 20
        Student(this)
    }
//
//    println("tom person info ${tom.hashCode()}")
//    println("tom person info ${alsoTom === tom}")
//    println("tom person info ${applyTom === tom}")
//
//    println("tom person info ${letTom.person === tom}")
//    println("tom person info ${runTom.person === tom}")

}


data class Person(val name: String, var age: Int, var money: Int?) {
    fun work() {
        println("Person is working ")
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

data class Student(var person: Person)