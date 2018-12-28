package com.takusemba.jethub.ui.item

import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import com.takusemba.jethub.R
import com.takusemba.jethub.databinding.ItemUserBinding
import com.takusemba.jethub.model.SimpleUser
import com.xwray.groupie.databinding.BindableItem

data class UserItem(
  val user: SimpleUser
) : BindableItem<ItemUserBinding>(
  user.hashCode().toLong()
) {

  override fun bind(binding: ItemUserBinding, position: Int) {
    Picasso.get().load(user.avatarUrl).into(binding.icon)
    binding.name.text = user.login
    binding.root.setOnClickListener { view ->
      view.findNavController().navigate(R.id.userDetailFragment)
    }
  }

  override fun getLayout() = R.layout.item_user
}