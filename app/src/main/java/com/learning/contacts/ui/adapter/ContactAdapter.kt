package com.learning.contacts.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.learning.contacts.databinding.ItemContactBinding
import com.learning.contacts.model.Contact

class ContactAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<Contact, ContactViewHolder>(DIFF_CALLBACK)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder = ContactViewHolder(
        ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClickListener)



    interface OnItemClickListener{
       fun onItemClicked(contact: Contact, imageView: ImageView)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>(){
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
               return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
               return oldItem == newItem
            }
        }
    }
}