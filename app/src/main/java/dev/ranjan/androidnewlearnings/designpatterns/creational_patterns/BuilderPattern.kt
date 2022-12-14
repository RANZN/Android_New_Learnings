package dev.ranjan.androidnewlearnings.designpatterns.creational_patterns

import android.app.AlertDialog
import android.content.Context

class BuilderPattern {
    fun alterDialog(context: Context) {
        AlertDialog.Builder(context).setTitle("Ranjan").setMessage("Dummy Message")
            .setNegativeButton("No thanks") { dialogInterface, i ->
                // "No thanks" action
            }.setPositiveButton("OK") { dialogInterface, i ->
                // "OK" action
            }.show()
    }

    fun retrofit() {
        val list = mutableListOf<Int>()
        val list2 = arrayListOf<Int>()
        list.add(1)
        list.add(2)


    }
}