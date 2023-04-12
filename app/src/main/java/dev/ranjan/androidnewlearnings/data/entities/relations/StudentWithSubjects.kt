package dev.ranjan.androidnewlearnings.data.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import dev.ranjan.androidnewlearnings.data.entities.Student
import dev.ranjan.androidnewlearnings.data.entities.Subject

data class StudentWithSubjects(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "studentID",
        entityColumn = "subjectName",
        associateBy = Junction(StudentAndSubjectCrossRel::class)
    )
    val subjects: List<Subject>
)
