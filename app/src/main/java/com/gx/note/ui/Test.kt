package com.gx.note.ui


fun main() {
    val tom = Person("Tom", 19, null)
    val nulTom: Person? = null
    println("tom person info ${tom.hashCode()}")
    nulTom?.let { }

    val runTom = tom.run {
        age = 20
        Student(this)
    }
    val applyTom = tom.apply {
        age = 20
        Student(this)
    }

    val alsoTom = tom.also {
        it.age = 20
        Student(it)
    }
    val letTom = tom.let {
        it.age = 20
        Student(it)
    }
    println("tom person info ${alsoTom.toString()}")
    println("tom person info ${letTom.toString()}")

}


data class Person(val name: String, var age: Int, var money: Int?) {
    fun work() {
        println("Person is working ")
    }
}

data class Student(var person: Person)