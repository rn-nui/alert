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

class NUISingleChoice : DialogFragment, DialogInterface.OnClickListener {
  private val listener: NUISingleChoiceListener?
  private val options: SingleChoiceOptions?
  private var selectedIndex: Int? = null

  constructor() {
    listener = null
    options = null
  }

  internal constructor(arguments: ReadableMap, callback: Callback, reactApplicationContext: ReactApplicationContext) {
    this.listener = NUISingleChoiceListener(callback, reactApplicationContext)
    this.options = SingleChoiceOptions(arguments)
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

    if (!options.icon.isNullOrEmpty()) {
      builder.setIcon(IconFinder(requireContext()).getId(options.icon!!))
    }

    if (options.positiveText.isNotEmpty()) {
      builder.setPositiveButton(options.positiveText, listener)
    }

    if (options.negativeText.isNotEmpty()) {
      builder.setNegativeButton(options.negativeText, listener)
    }

    if (options.neutralText.isNotEmpty()) {
      builder.setNeutralButton(options.neutralText, listener)
    }

    if (options.defaultSelectedIndex != null) {
      selectedIndex = options.defaultSelectedIndex
    }

    builder.setSingleChoiceItems(options.items, options.defaultSelectedIndex ?: -1, this.listener)

    return builder.create()
  }

  internal inner class NUISingleChoiceListener(private val callback: Callback, private val reactContext: ReactApplicationContext) :
    DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

    private var callbackConsumed = false

    override fun onClick(dialog: DialogInterface, which: Int) {
      if (which >= 0 ) {
        this@NUISingleChoice.selectedIndex = which
        return
      }

      if (!callbackConsumed) {
        if (reactContext.hasActiveReactInstance()) {
          when (which) {
            DialogInterface.BUTTON_POSITIVE -> callback.invoke("positive", this@NUISingleChoice.selectedIndex)
            DialogInterface.BUTTON_NEGATIVE -> callback.invoke("negative", this@NUISingleChoice.selectedIndex)
            DialogInterface.BUTTON_NEUTRAL -> callback.invoke("neutral", this@NUISingleChoice.selectedIndex)
          }
          callbackConsumed = true
        }
      }
    }

    override fun onDismiss(dialog: DialogInterface?) {
      if (!callbackConsumed) {
        if (reactContext.hasActiveReactInstance()) {
          callback.invoke("dismissed", this@NUISingleChoice.selectedIndex)
          callbackConsumed = true
        }
      }
    }
  }

}

