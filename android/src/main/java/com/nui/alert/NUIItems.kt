package com.nui.alert

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NUIItems : DialogFragment, DialogInterface.OnClickListener {
  private val listener: NUIItemsListener?
  private val options: ItemsOptions?

  constructor() {
    listener = null
    options = null
  }

  internal constructor(arguments: ReadableMap, callback: Callback, reactApplicationContext: ReactApplicationContext) {
    this.listener = NUIItemsListener(callback, reactApplicationContext)
    this.options = ItemsOptions(arguments)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
    createDialog(requireActivity(), this)

  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
    listener?.onDismiss(dialog)
  }

  override fun onClick(dialog: DialogInterface, which: Int) {
    listener?.onClick(dialog, which)
  }

  private fun createDialog(
    activityContext: Context,
    fragment: DialogInterface.OnClickListener
  ): Dialog {
    this.isCancelable = options?.isCancelable ?: false

    val builder = MaterialAlertDialogBuilder(activityContext, options!!.headerAlignmentStyle)

    if (options.title.isNotEmpty()) {
      builder.setTitle(options.title)
    }

    if (options.isCancelable) {
      builder.setPositiveButton(options.cancelButtonText, this.listener)
    }

    builder.setItems(options.items, this.listener)
    return builder.create()
  }
}

internal class NUIItemsListener(private val callback: Callback, private val reactContext: ReactApplicationContext) :
  DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

  private var callbackConsumed = false

  override fun onClick(dialog: DialogInterface, which: Int) {
    if (!callbackConsumed) {
      if (reactContext.hasActiveReactInstance()) {

        if (which >= 0) {
          callback.invoke(which)
        } else {
          callback.invoke("dismissed")
        }
        callbackConsumed = true
      }
    }
  }

  override fun onDismiss(dialog: DialogInterface?) {
    if (!callbackConsumed) {
      if (reactContext.hasActiveReactInstance()) {
        callback.invoke("dismissed")
        callbackConsumed = true
      }
    }
  }
}
