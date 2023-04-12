package dev.ranjan.androidnewlearnings.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import dev.ranjan.androidnewlearnings.data.entities.School
import dev.ranjan.androidnewlearnings.data.entities.Student

data class SchoolAndStudents(
    @Embedded val school: School,
    @Relation(
        parentColumn = "schoolName",
        entityColumn = "schoolName"
    )
    val students: List<Student>
)