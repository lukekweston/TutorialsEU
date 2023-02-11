package weston.luke.favdish.view.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import weston.luke.favdish.R
import weston.luke.favdish.application.FavDishApplication
import weston.luke.favdish.databinding.FragmentRandomDishBinding
import weston.luke.favdish.model.entities.FavDish
import weston.luke.favdish.model.entities.RandomDish
import weston.luke.favdish.util.Constants
import weston.luke.favdish.viewmodel.FavDishViewModel
import weston.luke.favdish.viewmodel.FavDishViewModelFactory
import weston.luke.favdish.viewmodel.NotificationsViewModel
import weston.luke.favdish.viewmodel.RandomDishViewModel

class RandomDishFragment : Fragment() {

    private var mBinding: FragmentRandomDishBinding? = null

    lateinit var mRandomDishViewModel: RandomDishViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentRandomDishBinding.inflate(inflater, container, false)

        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRandomDishViewModel = ViewModelProvider(this).get(RandomDishViewModel::class.java)

        mRandomDishViewModel.getRandomRecipeFromApi()

        randomDishViewModelObserver()


    }

    private fun randomDishViewModelObserver() {
        mRandomDishViewModel.randomDishResponse.observe(
            viewLifecycleOwner
        ) { randomDishResponse ->
            randomDishResponse?.let {
                setRandomDishResponseInUI(randomDishResponse.recipes[0])
            }
        }
        mRandomDishViewModel.randomDishLoadingError.observe(
            viewLifecycleOwner
        ) { dataError ->
            dataError?.let {
                Log.e("Random Dish API Error", "$dataError")
            }
        }

        mRandomDishViewModel.loadRandomDish.observe(
            viewLifecycleOwner
        ) { loadRandomDish ->
            loadRandomDish?.let {
                Log.i("Random Dish Loading", "$loadRandomDish")
            }
        }

    }

    private fun setRandomDishResponseInUI(recipe: RandomDish.Recipe) {
        Glide.with(requireActivity())
            .load(recipe.image)
            .centerCrop()
            .into(mBinding!!.ivDishImage)

        mBinding!!.tvTitle.text = recipe.title

        var dishType: String = "other"

        if (recipe.dishTypes.isNotEmpty()) {
            dishType = recipe.dishTypes[0]
        }
        mBinding!!.tvType.text = dishType

        mBinding!!.tvCategory.text = "other"

        var ingredients = ""
        for (value in recipe.extendedIngredients) {
            ingredients = if (ingredients.isEmpty()) {
                value.original
            } else {
                ingredients + ", \n" + value.original
            }
        }

        mBinding!!.tvIngredients.text = ingredients

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBinding!!.tvCookingDirection.text = Html.fromHtml(
                recipe.instructions,
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            @Suppress("DEPRECATION")
            mBinding!!.tvCookingDirection.text = Html.fromHtml(recipe.instructions)
        }

        mBinding!!.tvCookingTime.text =
            resources.getString(
                R.string.lbl_estimate_cooking_time,
                recipe.readyInMinutes.toString()
            )

        mBinding!!.ivFavoriteDish.setOnClickListener {
            val randomDishDetails = FavDish(
                recipe.image,
                Constants.DISH_IMAGE_SOURCE_ONLINE,
                recipe.title,
                dishType,
                "Other",
                ingredients,
                recipe.readyInMinutes.toString(),
                recipe.instructions,
                true
            )

            val mFavDishViewModel: FavDishViewModel by viewModels {
                FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
            }

            mFavDishViewModel.insert(randomDishDetails)

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
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}