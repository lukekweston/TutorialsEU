package weston.luke.favdish.view.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import weston.luke.favdish.R
import weston.luke.favdish.application.FavDishApplication
import weston.luke.favdish.databinding.FragmentDishDetailsBinding
import weston.luke.favdish.databinding.FragmentFavouriteDishesBinding
import weston.luke.favdish.model.database.FavDishRepository
import weston.luke.favdish.model.entities.FavDish
import weston.luke.favdish.viewmodel.FavDishViewModel
import weston.luke.favdish.viewmodel.FavDishViewModelFactory
import java.io.IOException
import java.util.*


class DishDetailsFragment : Fragment() {

    private var mBinding: FragmentDishDetailsBinding? = null

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDishDetailsBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DishDetailsFragmentArgs by navArgs()

//        Populate the fragment
        args.let {
            try {
                Glide.with(requireActivity())
                    .load(it.dishDetails.image)
                    .centerCrop()
//                        This listerner uses palette to get the background color based from the image passed in and apply it to the background of the fragment
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.e("TAG", "Error loading image", e)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource.let {
//                                Get the value from palette and apply it to rlDishDetailMain
                                Palette.from(resource!!.toBitmap()).generate() { palette ->
                                    val intColor =
                                        palette?.vibrantSwatch?.rgb ?: palette?.dominantSwatch?.rgb
                                        ?: 0
                                    mBinding!!.rlDishDetailMain.setBackgroundColor(intColor)
                                }
                            }
                            return false
                        }


                    })
                    .into(mBinding!!.ivDishImage)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            mBinding!!.tvTitle.text = it.dishDetails.title
//            Make first letter uppercase
            mBinding!!.tvType.text = it.dishDetails.type.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
            mBinding!!.tvCategory.text = it.dishDetails.category
            mBinding!!.tvIngredients.text = it.dishDetails.ingredients
            mBinding!!.tvCookingDirection.text = it.dishDetails.directionsToCook
            mBinding!!.tvCookingTime.text =
                resources.getString(R.string.lbl_estimate_cooking_time, it.dishDetails.cookingTime)

            if(args.dishDetails.favouriteDish){
                mBinding!!.ivFavoriteDish.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_selected
                    )
                )
            }
            else{
                mBinding!!.ivFavoriteDish.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_unselected
                    )
                )
            }


        }

        mBinding!!.ivFavoriteDish.setOnClickListener {
            args.dishDetails.favouriteDish = !args.dishDetails.favouriteDish

            mFavDishViewModel.update(args.dishDetails)

//            toggle the favorite image being selected or unselected
            if (args.dishDetails.favouriteDish) {
                mBinding!!.ivFavoriteDish.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_selected
                    )
                )
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.msg_added_to_favorites),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mBinding!!.ivFavoriteDish.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_unselected
                    )
                )
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.msg_removed_from_favorites),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}