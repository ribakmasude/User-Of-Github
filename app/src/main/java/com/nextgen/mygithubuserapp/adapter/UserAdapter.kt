package com.nextgen.mygithubuserapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nextgen.mygithubuserapp.R
import com.nextgen.mygithubuserapp.data.local.entity.UserEntity
import com.nextgen.mygithubuserapp.databinding.ItemUserBinding
import com.nextgen.mygithubuserapp.ui.detail.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView

//fun CircleImageView.loadAvatar(url: String?){
//    Glide.with(this.context) .load(url) .apply(RequestOptions().override(500, 500)) .centerCrop() .into(this)}

class UserAdapter(private val onFavoriteClick: (UserEntity) -> Unit): ListAdapter<UserEntity, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        val ivfavorite = holder.binding.ivFavorite
        if (user.isFavorite!!){
            ivfavorite.setImageDrawable(ContextCompat.getDrawable(ivfavorite.context, R.drawable.ic_baseline_stared_24))
        }else{
            ivfavorite.setImageDrawable(ContextCompat.getDrawable(ivfavorite.context, R.drawable.ic_baseline_star_24))
        }
        ivfavorite.setOnClickListener {
            onFavoriteClick(user)
        }
    }

    class UserViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            binding.tvUsername.text = user.login
//            binding.ivPhoto.loadAvatar(user.avatar.toString())
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .circleCrop()
                .into(binding.ivPhoto)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.LOGIN, user.login)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEntity> =
            object : DiffUtil.ItemCallback<UserEntity>(){
                override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem.login == newItem.login
                }
                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}