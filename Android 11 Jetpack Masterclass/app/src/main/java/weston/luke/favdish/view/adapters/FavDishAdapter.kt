package weston.luke.favdish.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import weston.luke.favdish.R
import weston.luke.favdish.databinding.ItemDishLayoutBinding
import weston.luke.favdish.model.entities.FavDish
import weston.luke.favdish.view.fragments.AllDishesFragment
import weston.luke.favdish.view.fragments.FavoriteDishesFragment

class FavDishAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<FavDishAdapter.ViewHolder>() {

    private var dishes: List<FavDish> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        Create binding
        val binding: ItemDishLayoutBinding = ItemDishLayoutBinding.inflate(
            LayoutInflater.from(fragment.context), parent, false
        )
//        Create the viewHolder specified in this class with the binding
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes[position]
//        Use Glide to scale the image
        Glide.with(fragment)
            .load(dish.image)
            .into(holder.ivDishImage)
        holder.ivTitle.text = dish.title

        holder.itemView.setOnClickListener {
            if (fragment is AllDishesFragment) {
                fragment.dishDetails(dish)
            }
            if (fragment is FavoriteDishesFragment) {
                fragment.dishDetails(dish)
            }
        }

        holder.ibMore.setOnClickListener {
            val popupMenu = PopupMenu(fragment.context, holder.ibMore)
            popupMenu.menuInflater.inflate(R.menu.menu_adapter, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_edit_dish) {
                    Log.i("you clicked", "edit dish")
                } else if (it.itemId == R.id.action_delete_dish) {
                    Log.i("you clicked", "delete dish")
                }
                true
            }

            popupMenu.show()
        }
// only show menu on all dishes fragment
        if (fragment is AllDishesFragment) {
            holder.ibMore.visibility = View.VISIBLE
        }
        else if(fragment is FavoriteDishesFragment){
            holder.ibMore.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    //    Update the data with the observer in the fragment
    fun dishesList(list: List<FavDish>) {
        dishes = list
        notifyDataSetChanged()
    }

    class ViewHolder(view: ItemDishLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val ivDishImage = view.ivDishImage
        val ivTitle = view.tvDishTitle
        val ibMore = view.ibMore
    }
}