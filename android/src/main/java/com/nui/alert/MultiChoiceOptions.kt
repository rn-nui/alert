package com.nui.alert

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap

class MultiChoiceOptions(options: ReadableMap) {
  var isCancelable: Boolean = false
  var title: String = ""
  var headerAlignmentStyle: Int
  var icon: String? = null
  private var itemsList = mutableListOf<String>()
  var items: Array<String> = arrayOf()
  var positiveText: String = ""
  var negativeText: String = ""
  var neutralText: String = ""
  var defaultSelections = mutableListOf<Boolean>()

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

    if (options.hasKey("positiveText")) {
      positiveText = options.getString("positiveText") ?: "Ok"
    }

    if (options.hasKey("negativeText")) {
      negativeText = options.getString("negativeText") ?: "Cancel"
    }

    if (options.hasKey("neutralText")) {
      neutralText = options.getString("neutralText") ?: ""
    }

    if (options.hasKey("defaultSelections")) {
      options.getArray("defaultSelections")?.toArrayList()?.forEachIndexed { index, item ->
        if (item !is Boolean) {
          defaultSelections.add(index, false)
        } else {
          defaultSelections.add(index, item)
        }
      }
    }
  }
}
