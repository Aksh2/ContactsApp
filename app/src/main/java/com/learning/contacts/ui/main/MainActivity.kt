package com.learning.contacts.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.contacts.databinding.ActivityMainBinding
import com.learning.contacts.model.Contact
import com.learning.contacts.network.ResponseState
import com.learning.contacts.ui.adapter.ContactAdapter
import com.learning.contacts.ui.addcontact.AddContactActivity
import com.learning.contacts.ui.base.BaseActivity
import com.learning.contacts.ui.contactdetails.ContactDetailActivity
import com.learning.contacts.utils.viewModelOf
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    ContactAdapter.OnItemClickListener {
    val TAG = MainActivity::class.java.simpleName
    private val mAdapter: ContactAdapter by lazy { ContactAdapter(onItemClickListener = this) }

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun getViewModel() = viewModelOf<MainViewModel>(mViewModelProvider)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        setSupportActionBar(toolbar)

        mViewBinding.rvContacts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }

        fab.setOnClickListener { view ->
            startActivity(Intent(this, AddContactActivity::class.java))
        }

        initContacts()
        mViewModel.getContacts()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getContacts()
    }

    fun initContacts() {
        mViewModel.postContactLiveData.observe(this, Observer { state ->
            when (state) {
                is ResponseState.Loading -> {
                    Log.d(TAG, "Loading")
                }
                is ResponseState.Success -> {
                    Log.d(TAG, " data ${state.data}")
                    mAdapter.submitList(state.data.toMutableList())
                }
                is ResponseState.Error -> {
                    Log.d(TAG, "error: ${state.message}")
                }
            }
        })

    }

    override fun onItemClicked(contact: Contact, imageView: ImageView) {
        val intent = Intent(this, ContactDetailActivity::class.java)
        intent.putExtra(ContactDetailActivity.CONTACT_ID, contact.id)
        startActivity(intent)
    }
}
