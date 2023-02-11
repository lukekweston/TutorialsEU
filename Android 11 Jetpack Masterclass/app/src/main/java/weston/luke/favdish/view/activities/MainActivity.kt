package weston.luke.favdish.view.activities

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import weston.luke.favdish.R
import weston.luke.favdish.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mNavController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_all_dishes, R.id.navigation_favourite_dishes, R.id.navigation_random_dish
            )
        )
        setupActionBarWithNavController(mNavController, appBarConfiguration)
        mBinding.navView.setupWithNavController(mNavController)
    }

//    Makes the user be able to navigate back by pressing back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController, null)
    }

    fun hideBottomNavigationView(){
        mBinding.navView.clearAnimation()
//        Animate the navView down by its own height for 300 ms
        mBinding.navView.animate().translationY(mBinding.navView.height.toFloat()).duration = 300

        mBinding.navView.visibility = View.GONE
    }

    fun showBottomNavigationView(){
        mBinding.navView.clearAnimation()
        mBinding.navView.visibility = View.VISIBLE
//        Put the navView back to its origin over 300ms
        mBinding.navView.animate().translationY(0f).duration = 300


    }
}