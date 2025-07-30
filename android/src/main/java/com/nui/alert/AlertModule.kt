package com.nui.alert

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.UiThreadUtil
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = AlertModule.NAME)
class AlertModule(reactContext: ReactApplicationContext) :
  NativeAlertSpec(reactContext), LifecycleEventListener {
  private var isInForeground = false

  override fun getName(): String {
    return NAME
  }

  private inner class Helper(private val fragmentManager: FragmentManager) {
    private var alertToShow: NUIAlert? = null

    fun showPendingAlert() {
      UiThreadUtil.assertOnUiThread()
      val alertToShow = alertToShow ?: return
      dismissExisting()
      alertToShow.show(fragmentManager, FRAGMENT_TAG)
      this.alertToShow = null
    }

    fun dismissExisting() {
      if (!isInForeground) {
        return
      }
      val oldFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG) as NUIAlert?
      if (oldFragment?.isResumed == true) {
        oldFragment.dismiss()
      }
    }

    fun showNewAlert(arguments: ReadableMap, actionCallback: Callback?) {
      UiThreadUtil.assertOnUiThread()

      dismissExisting()

      val actionListener =
        if (actionCallback != null) NUIAlertListener(actionCallback, reactApplicationContext) else null

      val options = AlertOptions(arguments)
      val nuiAlert = NUIAlert(options, actionListener)

      if (isInForeground && !fragmentManager.isStateSaved) {
        nuiAlert.show(fragmentManager, FRAGMENT_TAG)
      } else {
        alertToShow = nuiAlert
      }
    }
  }

  override fun initialize() {
    reactApplicationContext.addLifecycleEventListener(this)
  }

  override fun onHostPause() {
    isInForeground = false
  }

  override fun onHostDestroy(): Unit = Unit

  override fun onHostResume() {
    isInForeground = true
    this.fragmentManagerHelper?.showPendingAlert()
  }

  override fun alert(options: ReadableMap, actionCallback: Callback, errorCallback: Callback) {
    val fragmentManagerHelper = this.fragmentManagerHelper
    if (fragmentManagerHelper == null) {
      errorCallback.invoke("Tried to show an alert while not attached to an Activity")
      return
    }

    UiThreadUtil.runOnUiThread { fragmentManagerHelper.showNewAlert(options, actionCallback) }
  }

  private val fragmentManagerHelper: Helper?
    get() {
      val activity = reactApplicationContext.currentActivity
      if (activity !is FragmentActivity) {
        return null
      }
      return Helper(activity.supportFragmentManager)
    }

  override fun invalidate() {
    reactApplicationContext.removeLifecycleEventListener(this)
    super.invalidate()
  }

  companion object {
    const val NAME: String = NativeAlertSpec.NAME
    internal const val FRAGMENT_TAG: String = "com.nui.Alert"
  }
}
