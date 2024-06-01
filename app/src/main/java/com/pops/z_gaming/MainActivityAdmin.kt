package com.pops.z_gaming

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pops.z_gaming.databinding.ActivityMainAdminBinding
import com.pops.z_gaming.databinding.ActivityMainBinding

class MainActivityAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityMainAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeAdmin())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.adminHome -> replaceFragment(HomeAdmin())
                R.id.adminManagementUser -> replaceFragment(UserManagerAdmin())
                R.id.adminProfile -> replaceFragment(Profile())

                else ->{

                }

            }

            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }
}