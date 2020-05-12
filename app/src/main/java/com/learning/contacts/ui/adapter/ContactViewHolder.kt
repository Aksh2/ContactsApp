package com.learning.contacts.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.learning.contacts.BR
import com.learning.contacts.R
import com.learning.contacts.databinding.ItemContactBinding
import com.learning.contacts.model.Contact

class ContactViewHolder(private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: Contact, onItemClickListener: ContactAdapter.OnItemClickListener? = null) {
        binding.setVariable(BR.item, contact)
        binding.executePendingBindings()
        binding.thumbnail.load(binding.root.context.getDrawable(R.drawable.ic_contact)) {

        }

        onItemClickListener?.let { listener ->
            binding.root.setOnClickListener {
                listener.onItemClicked(contact, binding.thumbnail)
            }
        }
    }
}