package com.nui.alert

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableNativeArray
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NUIMultiChoice : DialogFragment, DialogInterface.OnClickListener {
  private val buttonListener: NUIButtonListener?
  private val multiChoiceListener: NUIMultiChoiceListener?
  private val options: MultiChoiceOptions?
  private var selections = mutableListOf<Boolean>()

  constructor() {
    buttonListener = null
    multiChoiceListener = null
    options = null
  }

  internal constructor(arguments: ReadableMap, callback: Callback, reactApplicationContext: ReactApplicationContext) {
    this.buttonListener = NUIButtonListener(callback, reactApplicationContext)
    this.multiChoiceListener = NUIMultiChoiceListener()
    this.options = MultiChoiceOptions(arguments)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
    createDialog(requireActivity(), this)

  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
    buttonListener?.onDismiss(dialog)
  }

  override fun onClick(dialog: DialogInterface, which: Int) {
    buttonListener?.onClick(dialog, which)
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
      builder.setPositiveButton(options.positiveText, buttonListener)
    }

    if (options.negativeText.isNotEmpty()) {
      builder.setNegativeButton(options.negativeText, buttonListener)
    }

    if (options.neutralText.isNotEmpty()) {
      builder.setNeutralButton(options.neutralText, buttonListener)
    }

    if (options.defaultSelections != null) {
      this.selections = options.defaultSelections
    }

    builder.setMultiChoiceItems(options.items, options.defaultSelections.toBooleanArray(), multiChoiceListener)

    return builder.create()
  }

  fun toWritableArray(): WritableNativeArray {
    val state = WritableNativeArray()

    selections.forEach {
      state.pushBoolean(it)
    }

    return state
  }

  internal inner class NUIButtonListener(private val callback: Callback, private val reactContext: ReactApplicationContext) :
    DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

    private var callbackConsumed = false

    override fun onClick(dialog: DialogInterface, which: Int) {
      if (!callbackConsumed) {
        if (reactContext.hasActiveReactInstance()) {
          WritableNativeArray()


          when (which) {
            DialogInterface.BUTTON_POSITIVE -> callback.invoke("positive", toWritableArray())
            DialogInterface.BUTTON_NEGATIVE -> callback.invoke("negative", toWritableArray())
            DialogInterface.BUTTON_NEUTRAL -> callback.invoke("neutral", toWritableArray())
          }
          callbackConsumed = true
        }
      }
    }

    override fun onDismiss(dialog: DialogInterface?) {
      if (!callbackConsumed) {
        if (reactContext.hasActiveReactInstance()) {
          callback.invoke("dismissed", toWritableArray())
          callbackConsumed = true
        }
      }
    }
  }

  internal inner class NUIMultiChoiceListener: OnMultiChoiceClickListener {
    override fun onClick(p0: DialogInterface?, index: Int, selection: Boolean) {
      this@NUIMultiChoice.selections[index] = selection
    }
  }
}
