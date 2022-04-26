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
import ru.dw.gbkotlinweather.utils.TAG
import ru.dw.gbkotlinweather.utils.arrayPermissions


class ContactFragment : Fragment() {
    private var _banding: FragmentUserContactBinding? = null
    private val binding get() = _banding!!
    private var permissionCall = false


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
                message("${it.key}")

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
                Log.d(TAG, "loadPhoneContact: ${cur.getString(columnIndexName)}")
                val phone =
                    cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                Log.d(TAG, "loadPhoneContact number: $phone ")

                val view = LayoutInflater.from(requireContext())
                    .inflate(R.layout.item_user_phone_user, binding.root, false)
                val textViewName = view.findViewById<TextView>(R.id.name_contact)
                val textViewPhone = view.findViewById<TextView>(R.id.phone_contact)
                textViewName.text = name
                textViewPhone.text = phone
                binding.containerForContact.addView(view)
                view.setOnClickListener {
                    if (permissionCall){
                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse("tel:$phone")
                        startActivity(intent)
                    }

                }
            }
        }
        cursor?.close()
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


}