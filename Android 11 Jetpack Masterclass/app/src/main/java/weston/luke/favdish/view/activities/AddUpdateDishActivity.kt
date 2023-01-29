package weston.luke.favdish.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import weston.luke.favdish.R
import weston.luke.favdish.databinding.ActivityAddUpdateDishBinding
import weston.luke.favdish.databinding.FragmentAllDishesBinding

class AddUpdateDishActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityAddUpdateDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setUpActionBar()
        //Sets the onclick listener to the onClickListener set up in this class
        mBinding.ivAddDishImage.setOnClickListener(this)
    }


    private fun setUpActionBar() {
        setSupportActionBar(mBinding.toolbarAddDishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddDishActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_add_dish_image -> {
                    Toast.makeText(
                        this, "You have clicked on the imageView",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
            }
        }
    }

}