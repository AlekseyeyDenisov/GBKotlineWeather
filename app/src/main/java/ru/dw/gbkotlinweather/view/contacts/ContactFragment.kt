package ru.dw.gbkotlinweather.view.contacts

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.dw.gbkotlinweather.MyApp
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.databinding.FragmentUserContactBinding
import ru.dw.gbkotlinweather.model.UserContact

import ru.dw.gbkotlinweather.view.contacts.recycler.AdapterContactUser
import ru.dw.gbkotlinweather.view.contacts.recycler.OnItemListenerContactUser


class ContactFragment : Fragment(), OnItemListenerContactUser {
    private var _banding: FragmentUserContactBinding? = null
    private val binding get() = _banding!!
    private val viewModel: ContactUserViewModel by lazy {
        ViewModelProvider(this).get(ContactUserViewModel::class.java)
    }

    private val adapterContract = AdapterContactUser(this)
    private var oldData: List<UserContact> = ArrayList()
    private val pref = MyApp.pref
    private var currentCallContact: UserContact? = null

    private val requestReadContactPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    )
    { permission ->
        if (permission) {
            viewModel.loadPhoneContact()
        } else {
            pref.setPermitsNumberNotReceivedReadContacts()
            if (pref.getPermitsNumberNotReceivedReadContacts() <= 2) {
                repeatMessageRequest(
                    getString(R.string.permission_contact),
                    getString((R.string.permission_contact)),
                    Manifest.permission.READ_CONTACTS
                )
            }else {
                messageNoPermission()
            }
        }
    }



    private val requestCallPhonePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    )
    {permission ->
        if (permission) {
            currentCallContact?.let {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:${currentCallContact!!.phoneContact}")
                startActivity(intent)
            }
        } else {
            pref.setPermitsNumberNotReceivedReadContacts()
            if (pref.getPermitsNumberNotReceivedReadContacts() <= 2) {
                repeatMessageRequest(
                    getString(R.string.permission_call_phone),
                    getString((R.string.permission_contact)),
                    Manifest.permission.CALL_PHONE
                )
            }else {
                messageNoPermission()
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
        requestReadContactPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)

        val observer = Observer<List<UserContact>> { data ->
            oldData = data
            setData(data)
        }

        viewModel.getLiveContact().observe(viewLifecycleOwner, observer)
        initRecycler()
        callPhone()

    }

    private fun callPhone() {
        binding.ContactSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filterData = oldData.filter {
                    it.nameContact.contains(newText)
                }
                adapterContract.submitList(filterData)
                return false
            }
        })
    }


    private fun setData(data: List<UserContact>) {
        val sort = data.sortedBy { it.nameContact }
        adapterContract.submitList(sort)
    }

    private fun initRecycler() {
        binding.recyclerViewUserContact.adapter = adapterContract
    }


    private fun repeatMessageRequest(title: String, message: String,permission:String) {

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(getString(R.string.permission_required) + message)
            .setPositiveButton(getString(R.string.grant_access)) { _, _ ->
                requestReadContactPermissionLauncher.launch(permission)
            }
            .setNegativeButton(getString(R.string.go_back)) { dialog, _ ->
                requireActivity().onBackPressed()
                dialog.dismiss()
            }
            .create()
            .show()
    }
    private fun messageNoPermission() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.permission_required)
            .setMessage(getString(R.string.message_reinstal_application))
            .setNegativeButton(getString(R.string.go_back)) { dialog, _ ->
                dialog.dismiss()
                requireActivity().onBackPressed()
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
        currentCallContact = userContact
        requestCallPhonePermissionLauncher.launch(Manifest.permission.CALL_PHONE)
    }


}