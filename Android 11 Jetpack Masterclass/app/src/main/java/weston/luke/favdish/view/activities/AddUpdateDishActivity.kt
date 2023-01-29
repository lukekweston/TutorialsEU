package weston.luke.favdish.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import weston.luke.favdish.R
import weston.luke.favdish.databinding.ActivityAddUpdateDishBinding
import weston.luke.favdish.databinding.FragmentAllDishesBinding

class AddUpdateDishActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddUpdateDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setUpActionBar()
    }


    private fun setUpActionBar(){
        setSupportActionBar(mBinding.toolbarAddDishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddDishActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}