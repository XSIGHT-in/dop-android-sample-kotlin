package `in`.xsight.sdk.android.sample.kotlin

import `in`.xsight.sdk.android.sample.kotlin.dummy.CheckOut
import `in`.xsight.sdk.android.sample.kotlin.dummy.City
import `in`.xsight.sdk.android.sample.kotlin.dummy.Product
import `in`.xsight.sdk.android.sample.kotlin.dummy.RequestToBook
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.gruter.sdk.open.api.DOX
import com.gruter.sdk.open.model.XEvent
import com.gruter.sdk.open.model.XProperties
import kotlinx.android.synthetic.main.activity_request_to_book.*
import java.util.*

class RequestToBookActivity : AppCompatActivity() {

    // data from ProductActivity
    var cityInfo: City? = null
    var productInfo: Product? = null
    var reqToBookInfo: RequestToBook? = null

//    var rentFee: Double = 0.0
//    var totalFee: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_to_book)

        // Retrieve dummy data from CollectionActivity
        val bundle: Bundle? = intent.getBundleExtra(BUNDLE_KEY)
        cityInfo = bundle?.getParcelable<City>(PARCEL_KEY_CITY)
        productInfo = bundle?.getParcelable<Product>(PARCEL_KEY_PRODUCT)
        reqToBookInfo = bundle?.getParcelable<RequestToBook>(PARCEL_KEY_REQTOBOOK)

        setTitle(productInfo?.name ?: "ERROR")
        displayReqToBookInfo()

        // Purchase Button click listener
        purchaseButton.setOnClickListener {
            val checkOut = CheckOut(
                orderNumber = reqToBookInfo?.bookingId ?: "",
                curcy = "USD",
                timezone = "84",
                paymentType = "International card",
                paymentDate = Date(),
                didClickThankyouPage = "Click"
            )
            toPurchaseCompleteActivity(checkOut)
        }
    }

    override fun onStart() {
        super.onStart()

        /******************************
         * XSIGHT.in SDK
         */
        if (cityInfo == null || productInfo == null || reqToBookInfo == null) return

        DOX.setEventGroupName("Click_Request_to_Book")
        DOX.logEvent(
            XEvent.Builder()
                .setEventName("Click_Request_to_Book")
                .setProperties(
                    XProperties.Builder()
                        .set("xi_is_host", "Guest")
                        .set("xi_room_id", productInfo?.roomId)
                        .set("xi_host_id", productInfo?.hostId)
                        .set("xi_name", productInfo?.name)
                        .set("xi_search_id", productInfo?.searchId)
                        .set("xi_city_id", cityInfo?.cityId)
                        .set("xi_city_nm", cityInfo?.cityName)
                        .set("xi_type_of_accommodation", productInfo?.typeOfAccommodation)
                        .set("xi_room_type", productInfo?.roomType)
                        .set("xi_bedrooms", productInfo?.numOfBedRooms)
                        .set("xi_bathrooms", productInfo?.numOfBethrooms)
                        .set("xi_families", productInfo?.families)
                        .set("xi_kitchen_facillities", productInfo?.kitchenFacillities)
                        .set("xi_entertainment", productInfo?.entertainment)
                        .set("xi_room_facillities", productInfo?.roomFacillities)
                        .set("xi_facilities", productInfo?.facilities)
                        .set("xi_special_facilities", productInfo?.specialFacilities)
                        .set("xi_booking_type", productInfo?.bookingType)
                        .set("xi_cancel_policy", productInfo?.cancelPolicy)
                        .set("xi_discount_id", productInfo?.discountId)
                        .set("xi_discount_nm", productInfo?.discountName)
                        .set("xi_discount_percent", productInfo?.discountPercent)
                        .set("xi_roomrate_weekdays", productInfo?.roomrate_weekdays)
                        .set("xi_roomrate_weekend", productInfo?.roomrate_weekend)
                        .set("xi_number_of_review", productInfo?.numbOfReview)
                        .set("xi_review_rate", productInfo?.reviewRate)
                        .set("xi_booking_id", reqToBookInfo?.bookingId)
                        .set("xi_date_booking", getUnixTimestamp(reqToBookInfo?.dateBooking))
                        .set("xi_date_checkin", getUnixTimestamp(reqToBookInfo?.dateCheckin))
                        .set("xi_date_checkout", getUnixTimestamp(reqToBookInfo?.dateCheckout))
                        .set("xi_number_of_guest", reqToBookInfo?.numOfGuest)
                        .set("xi_rental_fee", reqToBookInfo?.rentalFee)
                        .set("xi_cleaning_fee", productInfo?.cleaningFee)
                        .set("xi_other_service_fee", productInfo?.otherServiceFee)
                        .set("xi_discount_apply", reqToBookInfo?.discountApply)
                        .set("xi_total_fee", reqToBookInfo?.totalFee)
                        .set("xi_request_to_book", reqToBookInfo?.actionRequestToBook)
                        .build()
                )
                .build()
        )
        /******************************/
    }

    private fun toPurchaseCompleteActivity(purchaseInfo: CheckOut) {
        val intent = Intent(this, PurchaseCompleteActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(PARCEL_KEY_CITY, this.cityInfo)
        bundle.putParcelable(PARCEL_KEY_PRODUCT, this.productInfo)
        bundle.putParcelable(PARCEL_KEY_REQTOBOOK, this.reqToBookInfo)
        bundle.putParcelable(PARCEL_KEY_CHECKOUT, purchaseInfo)
        intent.putExtra(BUNDLE_KEY, bundle)
        startActivity(intent)
    }

    private fun displayReqToBookInfo() {
        val txtRequestInfo = """
        Click_Request_to_Book
        ---------------------
        xi_booking_id: ${reqToBookInfo?.bookingId}
        xi_date_booking: ${reqToBookInfo?.dateBooking}
        xi_date_checkin: ${reqToBookInfo?.dateCheckin}
        xi_date_checkout: ${reqToBookInfo?.dateCheckout}
        xi_number_of_guest: ${reqToBookInfo?.numOfGuest}
        xi_rental_fee: ${reqToBookInfo?.rentalFee}
        xi_cleaning_fee: ${productInfo?.cleaningFee}
        xi_other_service_fee: ${productInfo?.otherServiceFee}
        xi_discount_apply: ${reqToBookInfo?.discountApply}
        xi_total_fee: ${reqToBookInfo?.totalFee}
        xi_request_to_book: ${reqToBookInfo?.actionRequestToBook}
        ---------------------
        xi_room_id: ${productInfo?.roomId}
        xi_host_id: ${productInfo?.hostId}
        xi_name: ${productInfo?.name}
        xi_search_id: ${productInfo?.searchId}
        xi_city_id: ${cityInfo?.cityId}
        xi_city_nm: ${cityInfo?.cityName}
        xi_type_of_accommodation: ${productInfo?.typeOfAccommodation}
        xi_room_type: ${productInfo?.roomType}
        xi_bedrooms: ${productInfo?.numOfBedRooms}
        xi_bathrooms: ${productInfo?.numOfBethrooms}
        xi_families: ${productInfo?.families}
        xi_kitchen_facillities: ${productInfo?.kitchenFacillities}
        xi_entertainment: ${productInfo?.entertainment}
        xi_room_facillities: ${productInfo?.roomFacillities}
        xi_facilities: ${productInfo?.facilities}
        xi_special_facilities: ${productInfo?.specialFacilities}
        xi_booking_type: ${productInfo?.bookingType}
        xi_cancel_policy: ${productInfo?.cancelPolicy}
        xi_discount_id: ${productInfo?.discountId}
        xi_discount_nm: ${productInfo?.discountName}
        xi_discount_percent: ${productInfo?.discountPercent}
        xi_roomrate_weekdays: ${productInfo?.roomrate_weekdays}
        xi_roomrate_weekend: ${productInfo?.roomrate_weekend}
        xi_number_of_review: ${productInfo?.numbOfReview}
        xi_review_rate: ${productInfo?.reviewRate}
        """
        requestToBookTextView.text = txtRequestInfo
    }

}