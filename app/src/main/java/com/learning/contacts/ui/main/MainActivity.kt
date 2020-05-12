package com.learning.contacts.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.learning.contacts.R
import com.learning.contacts.databinding.ActivityMainBinding
import com.learning.contacts.model.Contact
import com.learning.contacts.network.ResponseState
import com.learning.contacts.ui.adapter.ContactAdapter
import com.learning.contacts.ui.base.BaseActivity
import com.learning.contacts.ui.contactdetails.ContactDetailActivity
import com.learning.contacts.utils.viewModelOf
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    ContactAdapter.OnItemClickListener {

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
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        initContacts()
        mViewModel.getContacts()
    }

    fun initContacts() {
        mViewModel.postContactLiveData.observe(this, Observer { state ->
            when (state) {
                is ResponseState.Loading -> {
                    Log.d("MAIN", "Loading")
                }
                is ResponseState.Success -> {
                    Log.d("contacrs", " data ${state.data}")
                    mAdapter.submitList(state.data.toMutableList())
                }
                is ResponseState.Error -> {
                    Log.d("Error", "error: ${state.message}")

                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClicked(contact: Contact, imageView: ImageView) {
        val intent = Intent(this, ContactDetailActivity::class.java)
        intent.putExtra("id", contact.id)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            imageView.transitionName
        )

        startActivity(intent, options.toBundle())
    }
}
