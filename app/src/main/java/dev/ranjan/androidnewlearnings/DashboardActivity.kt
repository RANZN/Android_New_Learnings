package dev.ranjan.androidnewlearnings

import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.ranjan.androidnewlearnings.databinding.ActivityDashboardBinding
import dev.ranjan.androidnewlearnings.fragments.FragmentOne
import dev.ranjan.androidnewlearnings.fragments.FragmentTwo

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding

    private val homeFragmentOne by lazy { FragmentOne() }
    private val profileFragmentTwo by lazy { FragmentTwo() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startTimer()

        setFragment(homeFragmentOne)

        binding.bottomNavigationView.setOnItemSelectedListener { menu ->

            when (menu.itemId) {
                R.id.home -> setFragment(homeFragmentOne)
                R.id.profile -> setFragment(profileFragmentTwo)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment, fragment.javaClass.simpleName).commit()
    }


}