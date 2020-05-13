package `in`.xsight.sdk.android.sample.kotlin

import `in`.xsight.sdk.android.sample.kotlin.dummy.CheckOut
import `in`.xsight.sdk.android.sample.kotlin.dummy.City
import `in`.xsight.sdk.android.sample.kotlin.dummy.Product
import `in`.xsight.sdk.android.sample.kotlin.dummy.RequestToBook
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_request_to_book.*
import java.util.*

class RequestToBookActivity : AppCompatActivity() {

    // data from ProductActivity
    var cityInfo: City? = null
    var productInfo: Product? = null
    var reqToBookInfo: RequestToBook? = null

    var rentFee: Double = 0.0
    var totalFee: Double = 0.0

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
                rentFee = this.rentFee,
                totalFee = this.totalFee,
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
        rentFee = calcRentFee(
            productInfo?.unitFee ?: 0.0,
            reqToBookInfo?.roomNight ?: 0)
        totalFee = calcTotalFee(
            rentFee,
            productInfo?.cleaningFee ?: 0.0,
            productInfo?.otherServiceFee ?: 0.0)

        val txtRequestInfo = """
        Click_Request_to_Book
        ---------------------
        xi_booking_id: ${reqToBookInfo?.bookingId}
        xi_date_booking: ${reqToBookInfo?.dateBooking}
        xi_date_checkin: ${reqToBookInfo?.dateCheckin}
        xi_date_checkout: ${reqToBookInfo?.dateCheckout}
        xi_number_of_guest: ${reqToBookInfo?.numOfGuest}
        xi_rental_fee: $rentFee
        xi_cleaning_fee: ${productInfo?.cleaningFee}
        xi_other_service_fee: ${productInfo?.otherServiceFee}
        xi_discount_apply: ${reqToBookInfo?.discountApply}
        xi_total_fee: $totalFee
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

    private fun calcRentFee(unitFee: Double, roomNight: Long): Double {
        return if (unitFee <= 0 || roomNight <= 0) 0.0 else unitFee * roomNight
    }

    private fun calcTotalFee(rentFee: Double, cleaningFee: Double, otherFee: Double): Double {
        return rentFee + cleaningFee + otherFee
    }
}