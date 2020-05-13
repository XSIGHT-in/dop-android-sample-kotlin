package `in`.xsight.sdk.android.sample.kotlin

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val PREFERENCE_KEY_USER_ID = "KEY_USER_ID"

const val BUNDLE_KEY = "KEY_BUNDLE"
const val PARCEL_KEY_CITY = "KEY_CITY"
const val PARCEL_KEY_PRODUCT = "KEY_PRODUCT"
const val PARCEL_KEY_REQTOBOOK = "KEY_REQUEST_TO_BOOK"
const val PARCEL_KEY_CHECKOUT = "KEY_CHECKOUT"

fun diffDateString(checkin: String, checkout: String): Long {
    val checkinDate = convertStringToDate(checkin)
    val checkoutDate = convertStringToDate(checkout)
    return diffDate(checkinDate, checkoutDate)
}

fun diffDate(checkin: Date, checkout: Date): Long {
    val diff: Long = checkout.time - checkin.time
    return TimeUnit.MILLISECONDS.toDays(diff)
}

fun convertStringToDate(s: String): Date {
    val df: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    return df.parse(s)
}