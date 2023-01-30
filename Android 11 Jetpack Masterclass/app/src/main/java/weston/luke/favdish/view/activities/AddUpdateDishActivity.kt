package weston.luke.favdish.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.karumi.dexter.Dexter
import weston.luke.favdish.R
import weston.luke.favdish.databinding.ActivityAddUpdateDishBinding
import weston.luke.favdish.databinding.DialogCustomImageSelectionBinding
import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

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


    private fun customImageSelectionDialog() {
        var dialog = Dialog(this)
        //    Create a new view binding to the dialog
        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)


        binding.tvCamera.setOnClickListener {
//            Check multiple permissions with dexter
            Dexter.withContext(this@AddUpdateDishActivity).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//                    Checks all permissions above are granted
                    if (report!!.areAllPermissionsGranted()) {
//                        Need to specify class as in listener
                        Toast.makeText(
                            this@AddUpdateDishActivity,
                            "you have camera permission now",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }
            ).onSameThread().check()

            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {
            Dexter.withContext(this@AddUpdateDishActivity).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//                    Checks all permissions above are granted
                    Log.e("hmm", "hmmm")
                    if (report!!.areAllPermissionsGranted()) {
                        Log.e("sajdjsadkkjsadkjsadk", "sajhdkjhsadkjsad")
//                        Need to specify class as in listener
                        Toast.makeText(
                            this@AddUpdateDishActivity,
                            "you have GALLERY permission now",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }
            ).onSameThread().check()
            //Close the dialog after clicking it
            dialog.dismiss()
        }
//        make the dialog appear
        dialog.show()
    }


    //    Display an alert saying that the user doesn't have the required permissions set
//    On positive click go to phone settings to be able to enable permissions - dont need to dismiss this as going to a new display, settings
//    Negative click - dismiss
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It looks like you have turned off permissions required fot this feature. It can ben enabled under Application Settings")
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
//                    Create an intent to go towards phone settings - specifically to application details settings
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                    use the package name of this app to be able to get to the specific application settings for this app
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
//                    Start the settings
                    startActivity(intent)
                }
//                Catch not found, print stack trace
                catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

}