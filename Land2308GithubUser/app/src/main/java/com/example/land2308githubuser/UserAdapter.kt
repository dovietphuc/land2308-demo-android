package com.example.land2308githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.land2308githubuser.databinding.UserItemBinding

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var data: List<GithubUser> = emptyList()

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

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(data[position])
    }
}