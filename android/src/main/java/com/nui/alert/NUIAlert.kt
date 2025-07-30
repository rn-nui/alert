package com.nui.alert

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NUIAlert : DialogFragment, DialogInterface.OnClickListener {
  private val listener: NUIAlertListener?
  private val options: AlertOptions?

  constructor() {
    listener = null
    options = null
  }

  internal constructor(options: AlertOptions, listener: NUIAlertListener?) {
    this.listener = listener
    this.options = options
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

    if (options.message.isNotEmpty()) {
      builder.setMessage(options.message)
    }

    if (options.positiveButtonText.isNotEmpty()) {
      builder.setPositiveButton(options.positiveButtonText, listener)
    }

    if (options.negativeButtonText.isNotEmpty()) {
      builder.setNegativeButton(options.negativeButtonText, listener)
    }

    if (options.neutralButtonText.isNotEmpty()) {
      builder.setNeutralButton(options.neutralButtonText, listener)
    }

    if (!options.icon.isNullOrBlank()) {
      builder.setIcon(IconFinder(requireContext()).getId(options.icon!!))
    }

    return builder.create()
  }
}

internal class NUIAlertListener(private val callback: Callback, private val reactContext: ReactApplicationContext) :
  DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

  private var callbackConsumed = false

  override fun onClick(dialog: DialogInterface, which: Int) {
    if (!callbackConsumed) {
      if (reactContext.hasActiveReactInstance()) {
        when (which) {
          DialogInterface.BUTTON_POSITIVE -> callback.invoke("positive")
          DialogInterface.BUTTON_NEGATIVE -> callback.invoke("negative")
          DialogInterface.BUTTON_NEUTRAL -> callback.invoke("neutral")
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
