package com.nui.alert

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableNativeArray
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NUIPrompt : DialogFragment, DialogInterface.OnClickListener {
  private val listener: NUIPromptListener?
  private val options: PromptOptions?
  private var dialog: View? = null

  constructor() {
    listener = null
    options = null
  }

  internal constructor(arguments: ReadableMap, callback: Callback, reactApplicationContext: ReactApplicationContext) {
    this.listener = NUIPromptListener(callback, reactApplicationContext)
    this.options = PromptOptions(arguments)
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

    builder.setView(getInputView())

    if (options.placeholder.isNotEmpty() ) {
      val firstInput = dialog!!.findViewById<TextInputLayout>(R.id.outlinedTextField)
      firstInput.hint = options.placeholder
    }

    return builder.create()
  }

  private fun getInputView(): View {
    when(options!!.inputType) {
      "login-password" -> {
        dialog = layoutInflater.inflate(R.layout.login, null)
        return dialog!!
      }
      "secure-text" -> {
        dialog = layoutInflater.inflate(R.layout.secure, null)
        return dialog!!
      }
      else -> {
        dialog = layoutInflater.inflate(R.layout.plain_text, null)
        return dialog!!
      }
    }
  }

  internal inner class NUIPromptListener(private val callback: Callback, private val reactContext: ReactApplicationContext) :
    DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

    private var callbackConsumed = false

    override fun onClick(dialog: DialogInterface, which: Int) {
      if (!callbackConsumed) {
        if (reactContext.hasActiveReactInstance()) {
          when (which) {
            DialogInterface.BUTTON_POSITIVE -> callback.invoke("positive", getTextValue())
            DialogInterface.BUTTON_NEGATIVE -> callback.invoke("negative", getTextValue())
            DialogInterface.BUTTON_NEUTRAL -> callback.invoke("neutral", getTextValue())
          }

          callbackConsumed = true
        }
      }
    }

    override fun onDismiss(dialog: DialogInterface?) {
      if (!callbackConsumed) {
        if (reactContext.hasActiveReactInstance()) {
          callback.invoke("dismissed", getTextValue())
          callbackConsumed = true
        }
      }
    }

    private fun getTextValue(): WritableNativeArray{
      val firstInput = this@NUIPrompt.dialog!!.findViewById<TextInputEditText>(R.id.firstInput)

      val values = WritableNativeArray()
      values.pushString(firstInput.text.toString())

      if (options?.inputType == "login-password") {
        val secondInput = this@NUIPrompt.dialog!!.findViewById<TextInputEditText>(R.id.secondInput)
        values.pushString(secondInput.text.toString())
      }

      return values
    }
  }
}
