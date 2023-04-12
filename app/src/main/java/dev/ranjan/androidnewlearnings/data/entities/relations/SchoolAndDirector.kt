package dev.ranjan.androidnewlearnings.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import dev.ranjan.androidnewlearnings.data.entities.Director
import dev.ranjan.androidnewlearnings.data.entities.School

data class SchoolAndDirector(
    @Embedded val school: School,
    @Relation(
        parentColumn = "schoolName",
        entityColumn = "schoolName"
    ) val director: Director
)
