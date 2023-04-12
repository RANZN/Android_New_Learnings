package dev.ranjan.androidnewlearnings.data.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import dev.ranjan.androidnewlearnings.data.entities.Student
import dev.ranjan.androidnewlearnings.data.entities.Subject

data class SubjectWithStudents(
    @Embedded val subject: Subject,
    @Relation(
        parentColumn = "subjectName",
        entityColumn = "studentID",
        associateBy = Junction(StudentAndSubjectCrossRel::class)
    )
    val students: List<Student>
)