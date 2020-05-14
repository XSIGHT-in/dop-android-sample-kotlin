package `in`.xsight.sdk.android.sample.kotlin

import `in`.xsight.sdk.android.sample.kotlin.dummy.City
import `in`.xsight.sdk.android.sample.kotlin.dummy.Product
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gruter.sdk.open.api.DOX
import com.gruter.sdk.open.model.XEvent
import com.gruter.sdk.open.model.XProperties
import kotlinx.android.synthetic.main.activity_collection.*

class CollectionActivity : AppCompatActivity(), View.OnClickListener  {

    var cityInfo: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        // Retrieve dummy data from MainActivity
        val bundle: Bundle? = intent.getBundleExtra(BUNDLE_KEY)
        cityInfo = bundle?.getParcelable<City>(PARCEL_KEY_CITY)

        // Set collection name as title
        setTitle(cityInfo?.cityName ?: "ERROR")

        productImageView1.setOnClickListener(this)
        productImageView2.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        /******************************
         * XSIGHT.in SDK
         */
        DOX.setEventGroupName("View_Collection")
        DOX.logEvent(
            XEvent.Builder()
                .setEventName("View_Collection")
                .setProperties(
                    XProperties.Builder()
                        .set("xi_is_host", "Guest")
                        .set("xi_city_id", cityInfo?.cityId)
                        .set("xi_city_nm", cityInfo?.cityName)
                        .set("xi_collection_id", cityInfo?.collectionId)
                        .set("xi_collection_nm", cityInfo?.collectionName)
                        .set("xi_theme_id", cityInfo?.themeId)
                        .set("xi_theme_nm", cityInfo?.themeName)
                        .build()
                )
                .build()
        )
        /******************************/
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.productImageView1 -> {
                val productInfo: Product = genProductInfo(
                    cityId = this.cityInfo?.cityId ?: 0,
                    productIndex = 1
                )
                toProductActivity(productInfo)
            }
            R.id.productImageView2 -> {
                val productInfo: Product = genProductInfo(
                    cityId = this.cityInfo?.cityId ?: 0,
                    productIndex = 2
                )
                toProductActivity(productInfo)
            }
        }
    }

    private fun toProductActivity(productInfo: Product) {
        val intent = Intent(this, ProductActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(PARCEL_KEY_CITY, this.cityInfo)
        bundle.putParcelable(PARCEL_KEY_PRODUCT, productInfo)
        intent.putExtra(BUNDLE_KEY, bundle)
        startActivity(intent)
    }


    private fun genProductInfo(cityId: Int, productIndex: Int): Product {
        var productInfo: Product
        if (cityId == 99910) {  // Paris
            if (productIndex == 1) { // First product
                productInfo = Product(
                    roomId = 80001,
                    name = "Charming new studio near Eiffel Tower (G36)",
                    searchId = 11241230,
                    typeOfAccommodation = "Studio APT",   // Seperated by '|'
                    roomType = "Private room",
                    numOfBedRooms = 1,
                    numOfBethrooms = 1,
                    families = "No smoking",
                    kitchenFacillities = "Microwave | Fridge",   // Seperated by '|'
                    entertainment = "BBQ Grills | Golf",    // Seperated by '|'
                    roomFacillities = "Balcony",
                    facilities = "Napkins | Soap | Elevator | Hair dryer | Internet",
                    specialFacilities = "",  // If you don't have any, send it as empty string NOT nil
                    bookingType = "Instant booking",
                    cancelPolicy = "Strict",
                    discountId = 11245876,
                    discountName = "JANUARY",
                    discountPercent = 8.0,
                    roomrate_weekdays = 92.2,
                    roomrate_weekend = 110.8,
                    numbOfReview = 13,
                    reviewRate = 4.2,
                    hostId = "host9990",
                    unitFee = 92.2,
                    cleaningFee = 5.0,
                    otherServiceFee = 6.5
                )
            } else {    // Second product
                productInfo = Product(
                    roomId = 80002,
                    name = "Logement entier grand luxe foch Maillot paris 16e",
                    searchId = 11245830,
                    typeOfAccommodation = "Entire House",   // Seperated by '|'
                    roomType = "Private room",
                    numOfBedRooms = 2,
                    numOfBethrooms = 1,
                    families = "Extra matteress | No smoking",
                    kitchenFacillities = "Microwave | Fridge",   // Seperated by '|'
                    entertainment = "",    // If you don't have any, send it as empty string NOT nil
                    roomFacillities = "Balcony",
                    facilities = "Napkins | Bottled water | Towels | Toothpaste | Soap | Elevator | Hair dryer | Internet",
                    specialFacilities = "",  // If you don't have any, send it as empty string NOT nil
                    bookingType = "Instant booking",
                    cancelPolicy = "Moderate",
                    discountId = 0,
                    discountName = "",
                    discountPercent = 0.0,
                    roomrate_weekdays = 72.0,
                    roomrate_weekend = 72.0,
                    numbOfReview = 2,
                    reviewRate = 4.5,
                    hostId = "host9990",
                    unitFee = 72.0,
                    cleaningFee = 4.0,
                    otherServiceFee = 4.2
                )
            }
        } else {    // HCMc
            if (productIndex == 1) { // First product
                productInfo = Product(
                    roomId = 80003,
                    name = "BlaBla Orange 05 - Phòng cặp đôi lãng mạn",
                    searchId = 11245876,
                    typeOfAccommodation = "Studio APT | Entire House",   // Seperated by '|'
                    roomType = "Private room",
                    numOfBedRooms = 2,
                    numOfBethrooms = 1,
                    families = "No smoking",
                    kitchenFacillities = "Microwave | Fridge",   // Seperated by '|'
                    entertainment = "BBQ Grills | Beach view | Golf",    // Seperated by '|'
                    roomFacillities = "Balcony",
                    facilities = "Bottled water | Towels | Toothpaste | Soap | Elevator | Hair dryer | Internet",
                    specialFacilities = "",  // If you don't have any, send it as empty string NOT nil
                    bookingType = "Instant booking",
                    cancelPolicy = "Strict",
                    discountId = 11245876,
                    discountName = "JANUARY",
                    discountPercent = 8.0,
                    roomrate_weekdays = 9.58,
                    roomrate_weekend = 16.58,
                    numbOfReview = 3,
                    reviewRate = 4.5,
                    hostId = "host9999",
                    unitFee = 50.8,
                    cleaningFee = 5.0,
                    otherServiceFee = 6.5
                )
            } else {    // Second product
                productInfo = Product(
                    roomId = 80004,
                    name = "LANDMARK 81",
                    searchId = 11245876,
                    typeOfAccommodation = "Studio APT | Entire House",   // Seperated by '|'
                    roomType = "Private room",
                    numOfBedRooms = 4,
                    numOfBethrooms = 1,
                    families = "No smoking",
                    kitchenFacillities = "Microwave | Fridge",   // Seperated by '|'
                    entertainment = "BBQ Grills | Beach view | Golf",   // Seperated by '|'
                    roomFacillities = "Balcony",
                    facilities = "Napkins | Bottled water | Towels | Toothpaste | Soap | Elevator | Hair dryer | Internet",
                    specialFacilities = "",  // If you don't have any, send it as empty string NOT nil
                    bookingType = "Instant booking",
                    cancelPolicy = "Strict",
                    discountId = 11245899,
                    discountName = "COVID-19",
                    discountPercent = 43.0,
                    roomrate_weekdays = 9.58,
                    roomrate_weekend = 16.58,
                    numbOfReview = 3,
                    reviewRate = 4.5,
                    hostId = "host9998",
                    unitFee = 10.2,
                    cleaningFee = 5.0,
                    otherServiceFee = 6.5
                )
            }
        }

        return productInfo
    }
}
