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
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

@Suppress("DEPRECATION")
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

//!!!!!!! newer devices after sdk 28 do not need the permissions to write to external storage - do not need to check this
        binding.tvCamera.setOnClickListener {
//            Check multiple permissions with dexter
            Dexter.withContext(this@AddUpdateDishActivity).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
// Let only runs the code when report is not null - good to know
                    report?.let {
                        //                    Checks all permissions above are granted
                        if (report.areAllPermissionsGranted()) {
//                        Need to specify class as in listener
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, CAMERA)

                        }
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
            Dexter.withContext(this@AddUpdateDishActivity).withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, GALLERY)

                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@AddUpdateDishActivity,
                        "you have denied te storage permission now",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {
                data?.extras?.let {
                    val thumbnail: Bitmap = data.extras!!.get("data") as Bitmap
                    //Assign and crop image with Glide
                    Glide.with(this).load(thumbnail)
                        .centerCrop()
                        .into(mBinding.ivDishImage)

                    //Update camera to edit icon
                    mBinding.ivAddDishImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_vector_edit
                        )
                    )
                }
            } else if (requestCode == GALLERY) {
                data?.let {
                    val selectedPhotoUri = data.data
                    //Assign and crop image with Glide
                    Glide.with(this).load(selectedPhotoUri)
                        .centerCrop()
                        .into(mBinding.ivDishImage)

                    //Update camera to edit icon
                    mBinding.ivAddDishImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_vector_edit
                        )
                    )
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("cancelled", "user cancelled image selection")
        }
    }


    companion object {
        private const val CAMERA = 1
        private const val GALLERY = 2
    }
}