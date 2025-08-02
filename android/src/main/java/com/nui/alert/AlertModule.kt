package com.nui.alert

import androidx.fragment.app.DialogFragment
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
    private var alertToShow: DialogFragment? = null

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

    fun showNewAlert(arguments: ReadableMap, actionCallback: Callback, type: String) {
      UiThreadUtil.assertOnUiThread()

      dismissExisting()

      val alert = when (type) {
        "prompt" -> {
          NUIPrompt(arguments, actionCallback, reactApplicationContext)
        }
        "items" -> {
          NUIItems(arguments, actionCallback, reactApplicationContext)
        }
        "singleChoice" -> {
          NUISingleChoice(arguments, actionCallback, reactApplicationContext)
        }
        "multiChoice" -> {
          NUIMultiChoice(arguments, actionCallback, reactApplicationContext)
        }
        else -> {
          NUIAlert(arguments, actionCallback, reactApplicationContext)
        }
      }

      if (isInForeground && !fragmentManager.isStateSaved) {
        alert.show(fragmentManager, FRAGMENT_TAG)
      } else {
        alertToShow = alert
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

    UiThreadUtil.runOnUiThread { fragmentManagerHelper.showNewAlert(options, actionCallback, "alert") }
  }

  override fun prompt(options: ReadableMap, actionCallback: Callback, errorCallback: Callback) {
    val fragmentManagerHelper = this.fragmentManagerHelper
    if (fragmentManagerHelper == null) {
      errorCallback.invoke("Tried to show an alert while not attached to an Activity")
      return
    }

    UiThreadUtil.runOnUiThread { fragmentManagerHelper.showNewAlert(options, actionCallback, "prompt") }
  }

  override fun items(options: ReadableMap, actionCallback: Callback, errorCallback: Callback) {
    val fragmentManagerHelper = this.fragmentManagerHelper
    if (fragmentManagerHelper == null) {
      errorCallback.invoke("Tried to show an alert while not attached to an Activity")
      return
    }

    UiThreadUtil.runOnUiThread { fragmentManagerHelper.showNewAlert(options, actionCallback, "items") }
  }

  override fun singleChoice(options: ReadableMap, actionCallback: Callback, errorCallback: Callback) {
    val fragmentManagerHelper = this.fragmentManagerHelper
    if (fragmentManagerHelper == null) {
      errorCallback.invoke("Tried to show an alert while not attached to an Activity")
      return
    }

    UiThreadUtil.runOnUiThread { fragmentManagerHelper.showNewAlert(options, actionCallback, "singleChoice") }
  }

  override fun multiChoice(options: ReadableMap, actionCallback: Callback, errorCallback: Callback) {
    val fragmentManagerHelper = this.fragmentManagerHelper
    if (fragmentManagerHelper == null) {
      errorCallback.invoke("Tried to show an alert while not attached to an Activity")
      return
    }

    UiThreadUtil.runOnUiThread { fragmentManagerHelper.showNewAlert(options, actionCallback, "multiChoice") }
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
