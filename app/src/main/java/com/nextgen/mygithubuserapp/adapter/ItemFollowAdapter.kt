package com.nextgen.mygithubuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nextgen.mygithubuserapp.R
import com.nextgen.mygithubuserapp.data.remote.response.UserResponse
import com.nextgen.mygithubuserapp.databinding.ItemFollowBinding
import com.nextgen.mygithubuserapp.ui.detail.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView

//fun CircleImageView.loadImage(url: String?){
//    Glide.with(this.context) .load(url) .apply(RequestOptions().override(500, 500)) .centerCrop() .into(this)}

class ItemFollowAdapter(private var listUser: List<UserResponse>): RecyclerView.Adapter<ItemFollowAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: ItemFollowBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponse) {
            binding.tvUsername.text = user.login
//            binding.ivPhoto.loadImage(user.avatarUrl.toString())
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .circleCrop()
                .into(binding.ivPhoto)
            itemView.setOnClickListener {
                val username = user.login
                val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.LOGIN, username)
                itemView.context.startActivity(intentDetail)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size ?: 0
}

