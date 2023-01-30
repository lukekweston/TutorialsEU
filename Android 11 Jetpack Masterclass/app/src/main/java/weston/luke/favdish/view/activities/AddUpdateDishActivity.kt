package weston.luke.favdish.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import weston.luke.favdish.R
import weston.luke.favdish.databinding.ActivityAddUpdateDishBinding
import weston.luke.favdish.databinding.DialogCustomImageSelectionBinding

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
                    customImageSelectionDialog()
                    return
                }
            }
        }
    }


    private fun customImageSelectionDialog(){
        var dialog = Dialog(this)
        //    Create a new view binding to the dialog
        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)


        binding.tvCamera.setOnClickListener{
            Toast.makeText(this, "Camera clicked", Toast.LENGTH_LONG).show()
            //Close the dialog after clicking it
            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener{
            Toast.makeText(this, "Gallery clicked", Toast.LENGTH_LONG).show()
            //Close the dialog after clicking it
            dialog.dismiss()
        }


//        make the dialog appear
        dialog.show()


    }

}