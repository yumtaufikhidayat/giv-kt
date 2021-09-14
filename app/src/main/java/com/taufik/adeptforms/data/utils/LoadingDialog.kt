package com.taufik.adeptforms.data.utils

import android.app.Activity
import android.app.AlertDialog
import com.taufik.adeptforms.R

class LoadingDialog(val activity: Activity) {

    private var dialog: AlertDialog? = null

    fun startLoadingDialog() {
        val inflater = activity.layoutInflater
        val builder = AlertDialog.Builder(activity).apply {
            setView(inflater.inflate(R.layout.custom_progress_dialog, null))
            setCancelable(false)
        }

        dialog = builder.create()
        dialog?.show()
    }

    fun dismissLoadingDialog() {
        dialog?.dismiss()
    }
}