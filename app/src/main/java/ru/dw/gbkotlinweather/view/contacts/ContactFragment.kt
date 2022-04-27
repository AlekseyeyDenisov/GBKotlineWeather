package ru.dw.gbkotlinweather.view.contacts

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
import ru.dw.gbkotlinweather.databinding.FragmentUserContactBinding
import ru.dw.gbkotlinweather.model.UserContact
import ru.dw.gbkotlinweather.utils.arrayPermissions
import ru.dw.gbkotlinweather.view.contacts.recycler.AdapterContactUser
import ru.dw.gbkotlinweather.view.contacts.recycler.OnItemListenerContactUser


class ContactFragment : Fragment(), OnItemListenerContactUser {
    private var _banding: FragmentUserContactBinding? = null
    private val binding get() = _banding!!
    private val viewModel: ContactUserViewModel by lazy {
        ViewModelProvider(this).get(ContactUserViewModel::class.java)
    }
    private var permissionCall = false
    private val adapterContract = AdapterContactUser(this)
    var oldData: List<UserContact> = ArrayList()


    private val requestMultiplePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    )
    { resultsMap ->

        resultsMap.forEach {
            if (it.value) {
                when (it.key) {
                    "android.permission.READ_CONTACTS" -> {
                        //loadPhoneContact()
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
        val observer = Observer<List<UserContact>> { data ->
            oldData = data
            setData(data)
        }
        viewModel.getLiveContact().observe(viewLifecycleOwner, observer)
        initRecycler()
        val mSearch = binding.ContactSearch
        mSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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


    private fun message(text: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Доступ к контактам")
            .setMessage("Необходимо разрешение $text")
            .setPositiveButton("предоставить доступ") { _, _ ->
                requestMultiplePermissionLauncher.launch(arrayPermissions)
            }
            .setNegativeButton("Вернуться обратно") { dialog, _ ->
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
        if (permissionCall) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${userContact.phoneContact}")
            startActivity(intent)
        }
    }


}