package `in`.xsight.sdk.android.sample.kotlin.dummy
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    var cityId: Int,
    var cityName: String,
    var collectionId: Int,
    var collectionName: String,
    var themeId: Int,
    var themeName: String
) : Parcelable