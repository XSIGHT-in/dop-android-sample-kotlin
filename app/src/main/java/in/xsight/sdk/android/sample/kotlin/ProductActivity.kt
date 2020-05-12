package `in`.xsight.sdk.android.sample.kotlin

import `in`.xsight.sdk.android.sample.kotlin.dummy.City
import `in`.xsight.sdk.android.sample.kotlin.dummy.Product
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_product.*
import java.text.SimpleDateFormat
import java.util.*

class ProductActivity : AppCompatActivity(), View.OnClickListener {
    var cityInfo: City? = null
    var productInfo: Product? = null
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // Retrieve dummy data from CollectionActivity
        val bundle: Bundle? = intent.getBundleExtra(BUNDLE_KEY)
        cityInfo = bundle?.getParcelable<City>(PARCEL_KEY_CITY)
        productInfo = bundle?.getParcelable<Product>(PARCEL_KEY_PRODUCT)

        // Display product image by roomID
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

            }
        }
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
}
