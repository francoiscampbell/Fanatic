package ca.carleton.elec3907.grouph.fpvtest.ext

import android.util.Log

/**
 * Created by francois on 16-03-04.
 */
fun Any.log(text: String) = logv(text)

fun Any.logv(text: String) = Log.v(javaClass.simpleName, text)
fun Any.logd(text: String) = Log.d(javaClass.simpleName, text)
fun Any.logi(text: String) = Log.i(javaClass.simpleName, text)
fun Any.logw(text: String) = Log.w(javaClass.simpleName, text)
fun Any.loge(text: String) = Log.e(javaClass.simpleName, text)
fun Any.logwtf(text: String) = Log.wtf(javaClass.simpleName, text)

fun Any.log(text: String, t: Throwable) = logv(text, t)
fun Any.logv(text: String, t: Throwable) = Log.v(javaClass.simpleName, text, t)
fun Any.logd(text: String, t: Throwable) = Log.d(javaClass.simpleName, text, t)
fun Any.logi(text: String, t: Throwable) = Log.i(javaClass.simpleName, text, t)
fun Any.logw(text: String, t: Throwable) = Log.w(javaClass.simpleName, text, t)
fun Any.loge(text: String, t: Throwable) = Log.e(javaClass.simpleName, text, t)
fun Any.logwtf(text: String, t: Throwable) = Log.wtf(javaClass.simpleName, text, t)