package `in`.xsight.sdk.android.sample.kotlin.dummy

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class CheckOut(
    var orderNumber:String,  // ordNo: same as xi_booking_id
    var curcy:String,    // curcy: USD, VND, KRW, etc.
    var timezone:String,     // xi_timezone: Country phone code. ex) 84
    var paymentType:String,  // xi_payment_type: International card
    var paymentDate: Date,    // xi_payment_date
    var didClickThankyouPage:String // xi_thankyoupage: Click / NA
): Parcelable