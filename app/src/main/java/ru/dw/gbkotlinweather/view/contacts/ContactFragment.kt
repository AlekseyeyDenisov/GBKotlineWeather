package ru.dw.gbkotlinweather.view.contacts

import android.annotation.SuppressLint
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.databinding.FragmentUserContactBinding
import ru.dw.gbkotlinweather.utils.TAG
import ru.dw.gbkotlinweather.utils.arrayPermissions
import ru.dw.gbkotlinweather.view.state.DetailsState


class ContactFragment : Fragment() {
    private var _banding: FragmentUserContactBinding? = null
    private val binding get() = _banding!!

    private val viewModelDetails: ContactViewModel by lazy {
        ViewModelProvider(this).get(ContactViewModel::class.java)
    }

    private val requestMultiplePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    )
    { resultsMap ->
        var i = 0
        resultsMap.forEach {
            if (it.value) {
                i++
            } else {
                Log.d(TAG, "Необходима разрешение ${it.key}")
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

        val observer = Observer<DetailsState> {

        }
        viewModelDetails.getLiveDataCityWeather().observe(viewLifecycleOwner, observer)

        loadPhoneContact()


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
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:$phone")
                    startActivity(intent)
                }
            }
        }
        cursor?.close()
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