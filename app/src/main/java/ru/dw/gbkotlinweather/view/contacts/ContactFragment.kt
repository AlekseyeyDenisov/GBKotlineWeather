package ru.dw.gbkotlinweather.view.contacts

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.databinding.FragmentUserContactBinding
import ru.dw.gbkotlinweather.model.UserContact
import ru.dw.gbkotlinweather.utils.TAG
import ru.dw.gbkotlinweather.utils.arrayPermissions
import ru.dw.gbkotlinweather.view.contacts.recycler.AdapterContactUser
import ru.dw.gbkotlinweather.view.contacts.recycler.OnItemListenerContactUser


class ContactFragment : Fragment(), OnItemListenerContactUser {
    private var _banding: FragmentUserContactBinding? = null
    private val binding get() = _banding!!
    private var permissionCall = false
    private val adapterContract = AdapterContactUser(this)
    private val listUserContact = ArrayList<UserContact>()



    private val requestMultiplePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    )
    { resultsMap ->

        resultsMap.forEach {
            if (it.value) {
                when (it.key) {
                    "android.permission.READ_CONTACTS" -> {
                        loadPhoneContact()
                    }
                    "android.permission.CALL_PHONE" -> {
                        permissionCall = true

                    }
                }
            } else {
                message(it.key)

            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _banding = FragmentUserContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestMultiplePermissionLauncher.launch(arrayPermissions)
        initRecycler()

    }

    private fun initRecycler() {
        binding.recyclerViewUserContact.adapter = adapterContract
    }

    @SuppressLint("Range")
    private fun loadPhoneContact() {

        val contentResolver: ContentResolver = requireActivity().contentResolver
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
        }
        cursor?.close()
        adapterContract.submitList(listUserContact)
    }

    private fun message(text:String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Доступ к контактам")
            .setMessage("Необходимо разрешение $text")
            .setPositiveButton("предоставить доступ"){_,_->
                requestMultiplePermissionLauncher.launch(arrayPermissions)
            }
            .setNegativeButton("Вернуться обратно"){dialog,_->
                requireActivity().onBackPressed()
                dialog.dismiss()
            }
            .create()
            .show()
    }


    companion object {
        fun newInstance(): ContactFragment {
            return ContactFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _banding = null
    }

    override fun onClickItemContactUser(userContact: UserContact) {
        if (permissionCall){
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${userContact.phoneContact}")
            startActivity(intent)
        }
    }


}