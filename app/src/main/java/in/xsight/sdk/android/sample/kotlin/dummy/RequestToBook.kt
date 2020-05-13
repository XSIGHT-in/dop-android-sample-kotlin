package `in`.xsight.sdk.android.sample.kotlin.dummy

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class RequestToBook(
    var roomNight: Long,
    var bookingId: String,    // xi_booking_id: Random Generated
    var dateBooking: Date,    // xi_date_booking
    var dateCheckin: Date,    // xi_date_checkin
    var dateCheckout: Date,   // xi_date_checkout
    var numOfGuest: Int,         // xi_number_of_guest
    var discountApply: String,    // xi_discount_apply: True or False
    var actionRequestToBook: String // xi_request_to_book: Click | NA
) : Parcelable