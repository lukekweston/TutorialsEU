package weston.luke.favdish.view.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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
import weston.luke.favdish.util.Constants
import weston.luke.favdish.viewmodel.FavDishViewModel
import weston.luke.favdish.viewmodel.FavDishViewModelFactory
import java.io.IOException
import java.util.*


@Suppress("DEPRECATION")
class DishDetailsFragment : Fragment() {

    private var mFavDishDetails: FavDish? = null

    private var mBinding: FragmentDishDetailsBinding? = null

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_share, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share_dish -> {
                val type = "text/plain"
                val subject = "Checkout this dish recipe"
                var extraText = ""
                val shareWith = "Share with"

                mFavDishDetails?.let {
                    var image = ""
                    if (it.imageSource == Constants.DISH_IMAGE_SOURCE_ONLINE) {
                        image = it.image
                    }
                    var cookingInstructions = ""

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        cookingInstructions = Html.fromHtml(
                            it.directionsToCook,
                            Html.FROM_HTML_MODE_COMPACT
                        ).toString()
                    } else {
                        @Suppress("DEPRECATION")
                        cookingInstructions = Html.fromHtml(it.directionsToCook).toString()
                    }

                    extraText =
                        "$image \n" +
                                "\nTitle: ${it.title} \n\nType: ${it.type} \n\nCategory: ${it.category}" +
                                "\n\nIngredients: \n ${it.ingredients} \n\nInstructions To Cook: \n\n ${it.directionsToCook}" +
                                "\n\nTime required to cook the dish approx ${it.cookingTime} minutes"
                }
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = type
                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                intent.putExtra(Intent.EXTRA_TEXT, extraText)
                startActivity(Intent.createChooser(intent, shareWith))

                return true
            }
        }
        return super.onOptionsItemSelected(item)
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

        mFavDishDetails = args.dishDetails
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mBinding!!.tvCookingDirection.text  = Html.fromHtml(
                    it.dishDetails.directionsToCook,
                    Html.FROM_HTML_MODE_COMPACT
                ).toString()
            } else {
                @Suppress("DEPRECATION")
                mBinding!!.tvCookingDirection.text  = Html.fromHtml(it.dishDetails.directionsToCook).toString()
            }


            mBinding!!.tvCookingTime.text =
                resources.getString(R.string.lbl_estimate_cooking_time, it.dishDetails.cookingTime)

            if (args.dishDetails.favouriteDish) {
                mBinding!!.ivFavoriteDish.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_selected
                    )
                )
            } else {
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