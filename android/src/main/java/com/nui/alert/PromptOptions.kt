package com.nui.alert

import com.facebook.react.bridge.ReadableMap

class PromptOptions(options: ReadableMap) {
  var isCancelable: Boolean = false
  var title: String = ""
  var headerAlignmentStyle: Int
  var message: String = ""
  var positiveButtonText: String = ""
  var negativeButtonText: String = ""
  var neutralButtonText: String = ""
  var icon: String? = null
  var inputType: String = "plain-text"
  var defaultValue: String = ""
  var placeholder: String = ""

  init {
    if (options.hasKey("cancelable")) {
      isCancelable = options.getBoolean("cancelable")
    }
    if (options.hasKey("title")) {
      title = options.getString("title") ?: ""
    }
    headerAlignmentStyle = if (options.hasKey("headerAlignment")) {
      when (options.getString("headerAlignment")) {
        "center" -> com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered
        else -> com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog
      }
    } else {
      com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog
    }
    if (options.hasKey("message")) {
      message = options.getString("message") ?: ""
    }
    if (options.hasKey("items")) {
      val items = options.getArray("items")

      items?.toArrayList()?.forEach {
        if (it !is HashMap<*, *>) return@forEach

        val position = it["position"]

        when (position) {
          "positive" -> positiveButtonText = it["text"] as String
          "negative" -> negativeButtonText = it["text"] as String
          "neutral" -> neutralButtonText = it["text"] as String
        }
      }

      if (options.hasKey("icon")) {
        icon = options.getString("icon")
      }

      if (options.hasKey("type")) {
        inputType = options.getString("type") ?: "plain-text"
      }

      if (options.hasKey("defaultValue")) {
        defaultValue = options.getString("defaultValue") ?: ""
      }

      if (options.hasKey("placeholder")) {
        placeholder = options.getString("placeholder") ?: ""
      }
    }
  }
}
