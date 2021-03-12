package com.sibelyildiz.cryptocurrencyapp.util

import android.content.Context
import android.widget.Toast


fun String.toastMessage(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}
