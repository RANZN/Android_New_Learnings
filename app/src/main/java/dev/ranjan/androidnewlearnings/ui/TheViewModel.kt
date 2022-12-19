package dev.ranjan.androidnewlearnings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ranjan.androidnewlearnings.model.local.RoomDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TheViewModel(private val dao: RoomDao) : ViewModel() {

    fun callSomeData() {
        viewModelScope.launch(Dispatchers.IO) {
//            dao.delete(1)
        }
    }
}