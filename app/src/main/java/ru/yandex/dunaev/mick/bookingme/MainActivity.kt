package ru.yandex.dunaev.mick.bookingme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ru.yandex.dunaev.mick.bookingme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            viewModel = model
            bottomMenu.apply {
                setOnNavigationItemSelectedListener { menuItemSelected(it) }
                selectedItemId = model.selectedPage?: R.id.menu_compas_id
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun menuItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_compas_id -> {
                setFragment(CompassFragment())
                binding.bottomMenu.itemIconTintList = resources.getColorStateList(R.color.bnv_dark, theme)
                model.selectedPage = R.id.menu_compas_id

            }
            R.id.menu_search_id -> {
                setFragment(SearchFragment())
                binding.bottomMenu.itemIconTintList = resources.getColorStateList(R.color.bnv_dark, theme)
                model.selectedPage =R.id.menu_search_id
            }
            R.id.menu_account_id -> {
                setFragment(AccountFragment())
                binding.bottomMenu.itemIconTintList = resources.getColorStateList(R.color.bnv_light, theme)
                model.selectedPage = R.id.menu_account_id
            }
        }
        return true
    }
}
