package ru.dw.gbkotlinweather.view.contacts.recycler


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.dw.gbkotlinweather.databinding.ItemUserPhoneUserBinding
import ru.dw.gbkotlinweather.model.UserContact

class HolderAdapterContactUser(
    view: View,
    private val onItemListenerContactUser: OnItemListenerContactUser) :
    RecyclerView.ViewHolder(view) {
    fun bind(userContact: UserContact) {
        ItemUserPhoneUserBinding.bind(itemView).apply {
            nameContact.text = userContact.nameContact
            phoneContact.text = userContact.phoneContact
            root.setOnClickListener {
                onItemListenerContactUser.onClickItemContactUser(userContact)
            }
        }
    }

}