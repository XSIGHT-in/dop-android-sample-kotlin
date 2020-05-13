package `in`.xsight.sdk.android.sample.kotlin

import `in`.xsight.sdk.android.sample.kotlin.dummy.CheckOut
import `in`.xsight.sdk.android.sample.kotlin.dummy.City
import `in`.xsight.sdk.android.sample.kotlin.dummy.Product
import `in`.xsight.sdk.android.sample.kotlin.dummy.RequestToBook
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_purchase_complete.*

class PurchaseCompleteActivity : AppCompatActivity() {

    // data from ProductActivity
    var cityInfo: City? = null
    var productInfo: Product? = null
    var reqToBookInfo: RequestToBook? = null
    var purchaseInfo: CheckOut? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_complete)

        // Retrieve dummy data from RequestToBookActivity
        val bundle: Bundle? = intent.getBundleExtra(BUNDLE_KEY)
        cityInfo = bundle?.getParcelable<City>(PARCEL_KEY_CITY)
        productInfo = bundle?.getParcelable<Product>(PARCEL_KEY_PRODUCT)
        reqToBookInfo = bundle?.getParcelable<RequestToBook>(PARCEL_KEY_REQTOBOOK)
        purchaseInfo = bundle?.getParcelable<CheckOut>(PARCEL_KEY_CHECKOUT)

        displayPurchaseInfo()

        backToMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun displayPurchaseInfo() {
        val txtPurchaseInfo = """
        Purchase COMPLETE
        ---------------------
        xi_booking_id: ${reqToBookInfo?.bookingId}
        xi_date_booking: ${reqToBookInfo?.dateBooking}
        xi_date_checkin: ${reqToBookInfo?.dateCheckin}
        xi_date_checkout: ${reqToBookInfo?.dateCheckout}
        xi_number_of_guest: ${reqToBookInfo?.numOfGuest}
        xi_rental_fee: ${purchaseInfo?.rentFee}
        xi_cleaning_fee: ${productInfo?.cleaningFee}
        xi_other_service_fee: ${productInfo?.otherServiceFee}
        xi_discount_apply: ${reqToBookInfo?.discountApply}
        totalAmount: ${purchaseInfo?.totalFee}
        xi_payment_type: ${purchaseInfo?.paymentType}
        xi_payment_date: ${purchaseInfo?.paymentDate}
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
        """
        compeleteTextView.text = txtPurchaseInfo
    }
}
