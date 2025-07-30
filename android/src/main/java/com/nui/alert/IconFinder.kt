package com.nui.alert

import android.content.Context

class IconFinder(val context: Context) {
  fun getId(iconName: String): Int {
    val resourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
    return resourceId
  }
}
