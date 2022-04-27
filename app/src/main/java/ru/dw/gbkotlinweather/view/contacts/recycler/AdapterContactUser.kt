package ru.dw.gbkotlinweather.view.contacts.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.dw.gbkotlinweather.databinding.ItemUserPhoneUserBinding
import ru.dw.gbkotlinweather.model.UserContact

interface OnItemListenerContactUser {
    fun onClickItemContactUser(userContact: UserContact)
}

class AdapterContactUser(private val onItemListenerContactUser: OnItemListenerContactUser) :
    ListAdapter<UserContact, HolderAdapterContactUser>(ContactItemDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderAdapterContactUser {
        val binding = ItemUserPhoneUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HolderAdapterContactUser(binding.root,onItemListenerContactUser)
    }

    override fun onBindViewHolder(holder: HolderAdapterContactUser, position: Int) {
        holder.bind(getItem(position))
    }


}