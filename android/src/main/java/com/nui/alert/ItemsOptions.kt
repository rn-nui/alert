package com.nui.alert

import com.facebook.react.bridge.ReadableMap

class ItemsOptions(options: ReadableMap) {
  var isCancelable: Boolean = false
  var title: String = ""
  var headerAlignmentStyle: Int
  var icon: String? = null
  private var itemsList = mutableListOf<String>()
  var items: Array<String> = arrayOf()
  var cancelButtonText: String = "Cancel"

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

    if (options.hasKey("items")) {
      val items = options.getArray("items")

      items?.toArrayList()?.forEach {
        if (it !is String) return@forEach

        this.itemsList.add(it)
      }

      this.items = this.itemsList.toTypedArray()
    }

    if (options.hasKey("icon")) {
      icon = options.getString("icon")
    }

    if (options.hasKey("cancelButtonText")) {
      cancelButtonText = options.getString("cancelButtonText") ?: "Cancel"
    }
  }
}
