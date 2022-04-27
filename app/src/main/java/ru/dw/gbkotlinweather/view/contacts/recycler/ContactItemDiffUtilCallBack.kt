package ru.dw.gbkotlinweather.view.contacts.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.dw.gbkotlinweather.model.UserContact
import ru.dw.gbkotlinweather.model.Weather

class ContactItemDiffUtilCallBack:DiffUtil.ItemCallback<UserContact>() {
    override fun areItemsTheSame(oldItem: UserContact, newItem: UserContact): Boolean {
        return oldItem.nameContact == newItem.nameContact
    }

    override fun areContentsTheSame(oldItem: UserContact, newItem: UserContact): Boolean {
        return oldItem == newItem
    }
}