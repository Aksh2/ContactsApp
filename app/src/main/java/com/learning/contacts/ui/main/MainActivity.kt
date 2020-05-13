package com.learning.contacts.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.contacts.R
import com.learning.contacts.databinding.ActivityMainBinding
import com.learning.contacts.model.Contact
import com.learning.contacts.network.ResponseState
import com.learning.contacts.ui.adapter.ContactAdapter
import com.learning.contacts.ui.addcontact.AddContactActivity
import com.learning.contacts.ui.base.BaseActivity
import com.learning.contacts.ui.contactdetails.ContactDetailActivity
import com.learning.contacts.utils.NetworkUtils
import com.learning.contacts.utils.getColorRes
import com.learning.contacts.utils.showToast
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
        checkConnectivity()
    }


    fun initContacts() {
        mViewModel.postContactLiveData.observe(this, Observer { state ->
            when (state) {
                is ResponseState.Loading -> {
                    showToast(this, "Loading")
                    showProgress(true)
                }
                is ResponseState.Success -> {
                    Log.d(TAG, " data ${state.data}")
                    if (state.data.isNotEmpty()) {
                        mAdapter.submitList(state.data.toMutableList())
                        showProgress(false)
                    }
                }
                is ResponseState.Error -> {
                    Log.d(TAG, "error: ${state.message}")
                    showToast(this, "Error: ${state.message}")
                    showProgress(false)

                }
            }
        })

        mViewBinding.srlRoot.setOnRefreshListener {
            fetchContacts()
        }

    }

    override fun onItemClicked(contact: Contact, imageView: ImageView) {
        val intent = Intent(this, ContactDetailActivity::class.java)
        intent.putExtra(ContactDetailActivity.CONTACT_ID, contact.id)
        startActivity(intent)
    }

    private fun checkConnectivity() {
        NetworkUtils.getNetworkState(applicationContext).observe(this, Observer { isConnected ->
            run {
                if (!isConnected) {
                    mViewBinding.tvNetworkStatus.text = getString(R.string.no_connection)
                    mViewBinding.llNetworkStatusView.apply {
                        visibility = View.VISIBLE
                        setBackgroundColor(getColorRes(R.color.colorNetworkStatusNotConnected))
                    }
                } else {
                    if (mViewModel.postContactLiveData.value is ResponseState.Error || mAdapter.itemCount == 0) {
                        fetchContacts()
                    }
                    mViewBinding.tvNetworkStatus.text = getString(R.string.connected)
                    mViewBinding.llNetworkStatusView.apply {
                        setBackgroundColor(getColorRes(R.color.colorNetworkStatusConnected))

                        animate()
                            .alpha(1f)
                            .setStartDelay(ANIMATION_DURATION)
                            .setDuration(ANIMATION_DURATION)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator?) {
                                    visibility = View.GONE
                                }
                            })
                    }

                }
            }
        })
    }

    fun fetchContacts() {
        mViewModel.getContacts()
    }

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

    private fun showProgress(isLoading: Boolean) {
        mViewBinding.srlRoot.isRefreshing = isLoading
    }
}
