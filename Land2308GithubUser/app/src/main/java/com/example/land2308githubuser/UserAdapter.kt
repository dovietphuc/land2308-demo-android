package com.example.land2308githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.land2308githubuser.databinding.UserItemBinding

class GithubDiffCallback : DiffUtil.ItemCallback<GithubUser>() {
    override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.avatarUrl == newItem.avatarUrl && oldItem.login == newItem.login
    }

}

class UserAdapter() : PagingDataAdapter<GithubUser, UserAdapter.UserViewHolder>(
    GithubDiffCallback()
) {
    class UserViewHolder(val binding: UserItemBinding)
                                : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUser) {
            binding.txtLogin.text = user.login
            Glide.with(binding.imgAvatar)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.imgAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }
}