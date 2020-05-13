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
import android.view.View
import android.widget.TextView
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
        return RequestToBook(
            roomNight = roomNight,
            bookingId = "SAMPLEIDA",
            dateBooking = Date(),
            dateCheckin = convertStringToDate(checkinTextView.text.toString()),
            dateCheckout = convertStringToDate(checkoutTextView.text.toString()),
            numOfGuest = numOfGuest,
            discountApply = false.toString(),
            actionRequestToBook = "Click"
        )
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
