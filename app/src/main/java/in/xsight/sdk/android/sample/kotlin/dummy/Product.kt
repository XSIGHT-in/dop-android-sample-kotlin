package `in`.xsight.sdk.android.sample.kotlin.dummy

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var roomId: Int,
    var name: String,     // xi_name: House name
    var searchId: Int,    // xi_search_id: Unique id of search result
    var typeOfAccommodation: String,  // xi_type_of_accommodation: Condominium, Villa, Studio APT, Entire House, Service APT, Other
    var roomType: String,     // xi_room_type: "Entire home" or "Private room"
    var numOfBedRooms: Int,   // xi_bedrooms: # of bedrooms
    var numOfBethrooms: Int,  // xi_bathrooms: # of bethrooms
    var families: String,    // xi_families: Babies, Toddlers welcome | Extra matteress | No smoking
    var kitchenFacillities: String,   // xi_kitchen_facillities: Oven | Microwave | Fridge | Stove
    var entertainment: String,   // xi_entertainment: Pet welcome | BBQ Grills | Natural surround | Beach view | Golf | Fishing | Pool | Private pool
    var roomFacillities: String, // xi_room_facillities: Balcony | Windows | Kitchen
    var facilities: String,      // xi_facilities: Wifi | Oven | Air-conditioning | Washing machine | Shampoo | Toiletories | Napkins | Bottled water | Towels | Toothpaste | Soap | Elevator | Hair dryer | Internet
    var specialFacilities: String,   // xi_special_facilities: Projector | Massage chair | Smart TV | Wine cabinets
    var bookingType: String,      // xi_booking_type: Request to book or Instant booking
    var cancelPolicy: String,     // xi_cancel_policy: Flexible, Moderate or Strict
    var discountId: Int,          // xi_discount_id: Unique ID of discount or promotion
    var discountName: String,     // xi_discount_nm
    var discountPercent: Double,   // xi_discount_percent
    var roomrate_weekdays: Double, // xi_roomrate_weekdays: Mon-Thu room rate
    var roomrate_weekend: Double,  // xi_roomrate_weekend: Fri-Sun room rate
    var numbOfReview: Int,        // xi_number_of_review
    var reviewRate: Double,        // xi_review_rate: avg. rates of reviews

    // To Be Calulated by below
    var hostId: String,           // xi_host_id: ex) host1111
    var unitFee: Double,           // calculation for xi_rental_fee
    var cleaningFee: Double,      // xi_cleaning_fee
    var otherServiceFee: Double   // xi_other_service_fee
): Parcelable