package com.learning.contacts.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> AppCompatActivity.viewModelOf(factory: ViewModelProvider.Factory) =
    ViewModelProvider(this, factory).get(VM::class.java)

fun Activity.getColorRes(id: Int) = ContextCompat.getColor(applicationContext, id)

fun Activity.showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}