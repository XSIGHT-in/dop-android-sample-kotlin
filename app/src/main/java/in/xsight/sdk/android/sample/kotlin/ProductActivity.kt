package `in`.xsight.sdk.android.sample.kotlin

import `in`.xsight.sdk.android.sample.kotlin.dummy.City
import `in`.xsight.sdk.android.sample.kotlin.dummy.Product
import `in`.xsight.sdk.android.sample.kotlin.dummy.RequestToBook
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.gruter.sdk.open.api.DOX
import com.gruter.sdk.open.model.XEvent
import com.gruter.sdk.open.model.XProperties
import kotlinx.android.synthetic.main.activity_product.*
import java.text.SimpleDateFormat
import java.util.*

class ProductActivity : AppCompatActivity(), View.OnClickListener {

    var cal = Calendar.getInstance()

    // data from CollectionActivity
    var cityInfo: City? = null
    var productInfo: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // Retrieve dummy data from CollectionActivity
        val bundle: Bundle? = intent.getBundleExtra(BUNDLE_KEY)
        cityInfo = bundle?.getParcelable<City>(PARCEL_KEY_CITY)
        productInfo = bundle?.getParcelable<Product>(PARCEL_KEY_PRODUCT)

        // Display product image and name
        when (productInfo?.roomId) {
            80001 -> productImageView.setImageResource(R.drawable.prod_1)
            80002 -> productImageView.setImageResource(R.drawable.prod_2)
            80003 -> productImageView.setImageResource(R.drawable.prod_1)
            80004 -> productImageView.setImageResource(R.drawable.prod_2)
        }
        setTitle(productInfo?.name ?: "ERROR")

        checkinSelectionButton.setOnClickListener(this)
        checkoutSelectionButton.setOnClickListener(this)
        requestToBookButton.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        /******************************
         * XSIGHT.in SDK
         */
        if (cityInfo == null || productInfo == null) return
        Log.d("XSIGHT.in", "Start for View_PDP logEvent()")
        DOX.setEventGroupName("View_PDP")
        DOX.logEvent(
            XEvent.Builder()
                .setEventName("View_PDP")
                .setProperties(
                    XProperties.Builder()
                        .set("xi_is_host", "Guest")
                        .set("xi_room_id", productInfo?.roomId)
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
                        .build()
                )
                .build()
        )
        /******************************/
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.checkinSelectionButton -> {
                val dateSetListener = getDateSetListener(checkinTextView)
                showDatePickerDialog(dateSetListener)
            }
            R.id.checkoutSelectionButton -> {
                // create an OnDateSetListener
                val dateSetListener = getDateSetListener(checkoutTextView)
                showDatePickerDialog(dateSetListener)
            }
            R.id.requestToBookButton -> {
                val checkinString = checkinTextView.text.toString()
                val checkoutString = checkoutTextView.text.toString()
                val diffDays = diffDateString(checkinString, checkoutString)
                if (diffDays <= 0) {
                    // CheckIn date should be earlier than checkOut date
                    showDialog("Check-In should be earlier than Check-Out")
                } else {
                    // Go to RequestToBookActivity with corresponding data
                    val reqToBookInfo = genRequestToBookInfo(numOfGuest = 4, roomNight = diffDays)
                    toRequestToBookActivity(reqToBookInfo)
                }
            }
        }
    }

    private fun genRequestToBookInfo(numOfGuest: Int, roomNight: Long): RequestToBook {
        val rentFee = calcRentFee(productInfo?.unitFee ?: 0.0, roomNight)
        val totalFee = calcTotalFee(
            rentFee,
            productInfo?.cleaningFee ?: 0.0,
            productInfo?.otherServiceFee ?: 0.0)

        return RequestToBook(
            roomNight = roomNight,
            bookingId = "SAMPLEIDA",
            dateBooking = Date(),
            dateCheckin = convertStringToDate(checkinTextView.text.toString()),
            dateCheckout = convertStringToDate(checkoutTextView.text.toString()),
            numOfGuest = numOfGuest,
            discountApply = false.toString(),
            rentalFee = rentFee,
            totalFee = totalFee,
            actionRequestToBook = "Click"
        )
    }


    private fun calcRentFee(unitFee: Double, roomNight: Long): Double {
        return if (unitFee <= 0 || roomNight <= 0) 0.0 else unitFee * roomNight
    }

    private fun calcTotalFee(rentFee: Double, cleaningFee: Double, otherFee: Double): Double {
        return rentFee + cleaningFee + otherFee
    }

    private fun toRequestToBookActivity(reqToBookInfo: RequestToBook) {
        val intent = Intent(this, RequestToBookActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(PARCEL_KEY_CITY, this.cityInfo)
        bundle.putParcelable(PARCEL_KEY_PRODUCT, this.productInfo)
        bundle.putParcelable(PARCEL_KEY_REQTOBOOK, reqToBookInfo)
        intent.putExtra(BUNDLE_KEY, bundle)
        startActivity(intent)
    }

    private fun getDateSetListener(dateTextView: TextView): DatePickerDialog.OnDateSetListener {
        // create an OnDateSetListener
        return DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView(dateTextView)
        }
    }

    private fun updateDateInView(dateTextView: TextView) {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateTextView!!.text = sdf.format(cal.getTime())
    }

    private fun showDatePickerDialog(dateSetListener: DatePickerDialog.OnDateSetListener) {
        DatePickerDialog(this@ProductActivity,
            dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setCancelable(false)
            .setNegativeButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = builder.create()
        alert.setTitle("Wooops!")
        alert.show()
    }
}
