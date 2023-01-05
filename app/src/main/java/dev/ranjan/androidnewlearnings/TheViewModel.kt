package dev.ranjan.androidnewlearnings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class TheViewModel : ViewModel() {
    private var i = 0
    private val _liveData = MutableLiveData(i)
    val liveData: LiveData<Int>
        get() = _liveData

    private val _genderLiveData = MutableLiveData(Gender.MALE)
    val genderLiveData
        get() = _genderLiveData

    fun startCall() {
        CoroutineScope(Dispatchers.IO).launch {
            async {
                while (i < 30) {
                    delay(1000)
                    _liveData.postValue(++i)

                }
            }

            async {
                while (i < 30) {
                    delay(500)
                    _genderLiveData.postValue(
                        when {
                            i % 5 == 0 -> Gender.MALE
                            i % 4 == 0 -> Gender.FEMALE
                            i % 3 == 0 -> Gender.TRANSGENDER
                            i % 2 == 0 -> Gender.OTHER
                            else -> Gender.MALE
                        }
                    )

                }
            }
        }
    }
}