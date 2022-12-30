package dev.ranjan.androidnewlearnings

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import dev.ranjan.androidnewlearnings.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.gender = Gender.MALE

        Log.d("TAG", "onCreate: ${this.getString(Gender.MALE.value)}")
        Log.d("TAG", "onCreate: ${this.getString(Gender.FEMALE.value)}")
        Log.d("TAG", "onCreate: ${this.getString(Gender.TRANSGENDER.value)}")
        Log.d("TAG", "onCreate: ${this.getString(Gender.OTHER.value)}")
    }
}

enum class Gender(@StringRes val value: Int) {
    MALE(R.string.male),
    FEMALE(R.string.female),
    TRANSGENDER(R.string.trans),
    OTHER(R.string.other),
}

@BindingAdapter(value = ["app:setGender", "app:flag"], requireAll = false)
fun getStringXML(view: TextView, gender: Gender, flag: Boolean) {
    if (flag) view.text = view.context.getString(gender.value)
    else view.text = "ranjan"
}

