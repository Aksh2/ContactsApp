package com.learning.contacts.ui.addcontact

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.learning.contacts.R
import com.learning.contacts.databinding.ActivityAddContactBinding
import com.learning.contacts.model.Contact
import com.learning.contacts.ui.base.BaseActivity
import com.learning.contacts.utils.NetworkUtils
import com.learning.contacts.utils.showToast
import com.learning.contacts.utils.viewModelOf
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddContactActivity : BaseActivity<AddContactViewModel, ActivityAddContactBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        title = "Add Contact"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        NetworkUtils.getNetworkState(this).observe(this, Observer { isConnected ->
            if (isConnected) {
                mViewBinding.btnSave.isEnabled = true
            } else {
                mViewBinding.btnSave.isEnabled = false
                showToast(this, getString(R.string.no_connection), Toast.LENGTH_LONG)
            }
        })

        mViewBinding.btnSave.setOnClickListener {
            val newContact = Contact(
                firstname = tilFirstName.editText?.text.toString(),
                lastname = tilLastName.editText?.text.toString(),
                email = tilEmail.editText?.text.toString(),
                phone = tilPhone.editText?.text.toString()
            )
            mViewModel.saveNewContact(newContact).observe(this, Observer {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setMessage(it)
                    .setNeutralButton("OK") { dialogInterface, which ->
                        if (it.equals("Created", true)) {
                            this.finish()
                        }
                    }
                alertDialogBuilder.create().show()
            })

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun getViewModel(): AddContactViewModel = viewModelOf(mViewModelProvider)

    override fun getViewBinding(): ActivityAddContactBinding =
        ActivityAddContactBinding.inflate(layoutInflater)
}
