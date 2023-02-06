package weston.luke.favdish.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import weston.luke.favdish.databinding.ItemDishLayoutBinding
import weston.luke.favdish.model.entities.FavDish

class FavDishAdapter(private val fragment: Fragment): RecyclerView.Adapter<FavDishAdapter.ViewHolder>() {

    private var dishes: List<FavDish> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        Create binding
        val binding: ItemDishLayoutBinding = ItemDishLayoutBinding.inflate(
            LayoutInflater.from(fragment.context), parent, false)
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
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

//    Update the data with the observer in the fragment
    fun dishesList(list: List<FavDish>){
        dishes = list
        notifyDataSetChanged()
    }

    class ViewHolder(view: ItemDishLayoutBinding) : RecyclerView.ViewHolder(view.root){
        val ivDishImage = view.ivDishImage
        val ivTitle = view.tvDishTitle
    }
}