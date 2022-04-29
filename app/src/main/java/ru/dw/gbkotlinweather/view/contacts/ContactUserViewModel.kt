package ru.dw.gbkotlinweather.view.contacts

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.dw.gbkotlinweather.model.UserContact


class ContactUserViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application
    private val liveDate: MutableLiveData<List<UserContact>> = MutableLiveData()
    private val listUserContact = ArrayList<UserContact>()

    fun getLiveContact() = liveDate

    @SuppressLint("Range")
    fun loadPhoneContact() {

        val contentResolver: ContentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.let { cur ->
            while (cur.moveToNext()) {
                val columnIndexName = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val name = cur.getString(columnIndexName)
                val phone =
                    cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                listUserContact.add(UserContact(name,phone))

            }
            liveDate.postValue(listUserContact)
        }
        cursor?.close()


    }


}