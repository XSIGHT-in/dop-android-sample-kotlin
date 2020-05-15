# Android Sample Guide (Kotlin) for Luxstay

## SDK V2 Installation

### Download SDK v2.0
**ATTENTION: From version 2.0, use "android-sdk-2.x.x.aar" only.**

1. Download SDK v2.0.2 file via [this link](dop-android-sdk-2.0.2/dop-android-sdk-2.0.2.aar).
2. Using Android Studio -> File -> New Module -> Import .JAR/ .AAR Package
![description](./readme.blob/installation.png)
3. Import Module dop-android-sdk-2.0.2.aar you get above.

### Dependencies: 'app/build.gradle'

> See [/app/build.gradle](app/build.gradle#L51)

```groovy
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    ...
    ...
    // ADD for XSIGHTin SDK
    // ###################################################
    implementation project(':dop-android-sdk-2.0.2')

    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0+'
    implementation 'androidx.annotation:annotation:1.1.0'
    implementation 'android.arch.work:work-runtime:1.0.1'
    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.6.2'
    implementation 'com.android.support:support-annotations:28.0.0'
    implementation 'com.google.android.gms:play-services-ads-identifier:17.0.0'
    implementation 'com.android.installreferrer:installreferrer:1.1'
    implementation 'commons-net:commons-net:3.6'

    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.5'
    implementation 'com.couchbase.lite:couchbase-lite-android:2.6.0'
    // JSON Parsing
    implementation 'com.squareup.retrofit2:converter-gson:2.6.2'
    implementation 'com.google.code.gson:gson:2.8.6'
    // ###################################################
}
```

### Config 'app/build.gradle'

> See [/app/build.gradle](app/build.gradle#L24)

```groovy
android {
    ...
    ...
    // ADD for XSIGHTin SDK
    // ###################################################
    compileOptions {
        sourceCompatibility = 1.8
        targetCompatibility = 1.8
    }
    // ###################################################
}
```

### Config xml value: 'app/res/values/strings.xml'

> See [/app/src/main/res/values/strings.xml](app/src/main/res/values/strings.xml#L17)

```xml
<resources>
    ...
    <string-array name="dotAuthorizationKey">
        <item name="useMode">3</item>
        <item name="domain">https://api.xsight.in/</item> // DOT END POINT
        <item name="serviceNumber">301</item>
        <item name="isDebug">false</item>
        <item name="accessToken">YOUR_ACCESS_TOKEN</item>
        <item name="domain_x">https://api.xsight.in/</item> // DOX END POINT
        <item name="isShowDebugLog">false</item>
    </string-array>
</resources>
```

### Enable network connection (android > 9)
> See [/app/src/main/AndroidManifest.xml](app/src/main/AndroidManifest.xml#L11)
```xml
<!-- AndroidManifest.xml -->
...
<application
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:networkSecurityConfig="@xml/network_security_config"
        android:theme="@style/AppTheme">
```
android:networkSecurityConfig="@xml/network_security_config"

> See [/app/src/main/res/xml/network_security_config.xml](app/src/main/res/values/strings.xml#L17)

```xml
<!-- app/res/xml/network_security_config.xml -->
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">xsight.in</domain>
        <trust-anchors>
            <certificates src="system" />
        </trust-anchors>
    </domain-config>
</network-security-config>
```

## Implementation

### SDK Init
Apply the code for initializing the SDK to the onCreate(Bundle savedInstanceState)

> If you want to make clear about the details of each event property, see [MainActivity.kt](app/src/main/java/in/xsight/sdk/android/sample/kotlin/MainActivity.kt#L27) in sample project.

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init XSIGHTin SDK
        DOX.initialization(this)
    }
```

### SDK Default events
New_Install, Open_App, New_Session

### Sign-Up
When a sign-up action occurs, logEvent() is called for collection of the event itself, and userIdentify() is performed simultaneously for gathering user information.

```kotlin
DOX.setEventGroupName("Sign_Up")
DOX.userIdentify(
    XIdentify.Builder()
        .setOnce("user_id", this.user.userId)
        .set("xi_email", /* String */)
        .set("xi_gender", /* String */)     // 'F' or 'M'
        .set("xi_timezone", /* String */)   // Country Phone Code
        .set("xi_email", /* String */)
        .set("xi_fb_id", /* String */)
        .set("xi_google_id", /* String */)
        .set("xi_status", /* String */)     // "Active" or "Inactive"
        .set("xi_created_at", /* Long */)   // Unix Timestamp: Date().time / 1000L
        .set("xi_is_host", /* String */)    // "Guest" or "Host"
        .build()
)
```
### Sign-In
The userIdentify() function is invoked when user information is created, deleted, or changed, however, in this site, is also called from Sign_In to collect information from users who have previously subscribed.

> If you want to make clear about the details of each event property, see [MainActivity.kt](app/src/main/java/in/xsight/sdk/android/sample/kotlin/MainActivity.kt#L74) in sample project.


```kotlin
DOX.setUserId(this.user.userId)

DOX.setEventGroupName("Sign_In")
DOX.userIdentify(
    XIdentify.Builder()
        .setOnce("user_id", this.user.userId)
        .set("xi_email", /* String */)
        .set("xi_gender", /* String */)     // 'F' or 'M'
        .set("xi_timezone", /* String */)   // Country Phone Code
        .set("xi_email", /* String */)
        .set("xi_fb_id", /* String */)
        .set("xi_google_id", /* String */)
        .set("xi_status", /* String */)     // "Active" or "Inactive"
        .set("xi_is_host", /* String */)    // "Guest" or "Host"
        .build()
)

DOX.setEventGroupName("Sign_In")
DOX.logEvent(
    XEvent.Builder()
        .setEventName("Sign_In")
        .setProperties(
            XProperties.Builder()
                .set("xi_email", /* String */)
                .set("xi_gender", /* String */)     // 'F' or 'M'
                .set("xi_timezone", /* String */)   // Country Phone Code
                .set("xi_email", /* String */)
                .set("xi_fb_id", /* String */)
                .set("xi_google_id", /* String */)
                .set("xi_status", /* String */)     // "Active" or "Inactive"
                .set("xi_is_host", /* String */)    // "Guest" or "Host"
                .build()
        )
        .build()
)
```

### Visit_Main_Page

> If you want to make clear about the details of each event property, see [MainActivity.kt](app/src/main/java/in/xsight/sdk/android/sample/kotlin/MainActivity.kt#L51) in sample project.

```kotlin
DOX.setEventGroupName("Visit_Main_Page")
DOX.logEvent(
    XEvent.Builder()
        .setEventName("Visit_Main_Page")
        .setProperties(
            XProperties.Builder()
                .set("xi_is_host", /* String */)    // "Guest" or "Host"
                .build()
        )
        .build()
)
```

### View_Collection

> If you want to make clear about the details of each event property, see [CollectionActivity.kt](app/src/main/java/in/xsight/sdk/android/sample/kotlin/CollectionActivity.kt#L43) in sample project.

```kotlin
DOX.setEventGroupName("View_Collection")
DOX.logEvent(
    XEvent.Builder()
        .setEventName("View_Collection")
        .setProperties(
            XProperties.Builder()
                .set("xi_is_host", /* String */)    // "Guest" or "Host"
                .set("xi_city_id", /* Int */)
                .set("xi_city_nm", /* String */)
                .set("xi_collection_id", /* Int */)
                .set("xi_collection_nm", /* String */)
                .set("xi_theme_id", /* Int */)
                .set("xi_theme_nm", /* String */)
                .build()
        )
    .build()
)
```


### View_PDP

> If you want to make clear about the details of each event property, see [ProductActivity.kt](app/src/main/java/in/xsight/sdk/android/sample/kotlin/ProductActivity.kt#L61) and [Product.kt](app/src/main/java/in/xsight/sdk/android/sample/kotlin/dummy/Product.kt) in sample project.

```kotlin
DOX.setEventGroupName("View_PDP")
DOX.logEvent(
    XEvent.Builder()
        .setEventName("View_PDP")
        .setProperties(
            XProperties.Builder()
                .set("xi_is_host", /* String */)    // "Guest" or "Host"
                .set("xi_room_id", /* Int */)
                .set("xi_name", /* String */)
                .set("xi_search_id", /* Int */)
                .set("xi_city_id", /* Int */)
                .set("xi_city_nm", /* String */)
                .set("xi_type_of_accommodation", /* String */)
                .set("xi_room_type", /* String */)
                .set("xi_bedrooms", /* Int */)
                .set("xi_bathrooms", /* Int */)
                .set("xi_families", /* String */)
                .set("xi_kitchen_facillities", /* String */)
                .set("xi_entertainment", /* String */)
                .set("xi_room_facillities", /* String */)
                .set("xi_facilities", /* String */)
                .set("xi_special_facilities", /* String */)
                .set("xi_booking_type", /* String */)
                .set("xi_cancel_policy", /* String */)
                .set("xi_discount_id", /* Int */)
                .set("xi_discount_nm", /* String */)
                .set("xi_discount_percent", /* Double */)
                .set("xi_roomrate_weekdays", /* Double */)
                .set("xi_roomrate_weekend", /* Double */)
                .set("xi_number_of_review", /* Int */)
                .set("xi_review_rate", /* Double */)
                .build()
        )
        .build()
)
```

### Click_Request_to_Book

> If you want to make clear about the details of each event property, see [RequestToBookActivity.kt](app/src/main/java/in/xsight/sdk/android/sample/kotlin/RequestToBookActivity.kt#L64) and [RequestToBook.kt](app/src/main/java/in/xsight/sdk/android/sample/kotlin/dummy/RequestToBook.kt) in sample project.

```kotlin
DOX.setEventGroupName("Click_Request_to_Book")
DOX.logEvent(
    XEvent.Builder()
        .setEventName("Click_Request_to_Book")
        .setProperties(
            XProperties.Builder()
                .set("xi_is_host", /* String */)    // "Guest" or "Host"
                .set("xi_room_id", /* Int */)
                .set("xi_host_id", /* String */)
                .set("xi_name", /* String */)
                .set("xi_search_id", /* Int */)
                .set("xi_city_id", /* Int */)
                .set("xi_city_nm", /* String */)
                .set("xi_type_of_accommodation", /* String */)
                .set("xi_room_type", /* String */)
                .set("xi_bedrooms", /* Int */)
                .set("xi_bathrooms", /* Int */)
                .set("xi_families", /* String */)
                .set("xi_kitchen_facillities", /* String */)
                .set("xi_entertainment", /* String */)
                .set("xi_room_facillities", /* String */)
                .set("xi_facilities", /* String */)
                .set("xi_special_facilities", /* String */)
                .set("xi_booking_type", /* String */)
                .set("xi_cancel_policy", /* String */)
                .set("xi_discount_id", /* Int */)
                .set("xi_discount_nm", /* String */)
                .set("xi_discount_percent", /* Double */)
                .set("xi_roomrate_weekdays", /* Double */)
                .set("xi_roomrate_weekend", /* Double */)
                .set("xi_number_of_review", /* Int */)
                .set("xi_review_rate", /* Double */)
                .set("xi_booking_id", /* String */)
                .set("xi_date_booking", /* Long */)  // Unix Timestamp: Date().time / 1000L
                .set("xi_date_checkin", /* Long */)  // Unix Timestamp: Date().time / 1000L
                .set("xi_date_checkout", /* Long */)  // Unix Timestamp: Date().time / 1000L
                .set("xi_number_of_guest", /* Int */)
                .set("xi_rental_fee", /* Double */)
                .set("xi_cleaning_fee", /* Double */)
                .set("xi_other_service_fee", /* Double */)
                .set("xi_discount_apply", /* String */)
                .set("xi_total_fee", /* Double */)
                .set("xi_request_to_book", /* String */)
                .build()
        )
        .build()
)
```

### Check_Out

> If you want to make clear about the details of each event property, see [PurchaseCompleteActivity.kt](app/src/main/java/in/xsight/sdk/android/sample/kotlin/PurchaseCompleteActivity.kt#L56) and [CheckOut.kt](app/src/main/java/in/xsight/sdk/android/sample/kotlin/dummy/CheckOut.kt) in sample project.

```kotlin
DOX.setEventGroupName("Check_Out")
DOX.logRevenue(
    XRevenue.Builder()
        .setRevenueType("Check_Out")
        .setCurrency(/* String */)
        .setOrderNo(/* String */)
        .setProperties(
            XProperties.Builder()
                .set("xi_is_host", /* String */)    // "Guest" or "Host"
                .set("totalAmount", /* Double */)
                .set("xi_timezone", /* String */)
                .set("xi_room_id", /* Int */)
                .set("xi_name", /* String */)
                .set("xi_search_id", /* Int */)
                .set("xi_city_id", /* Int */)
                .set("xi_city_nm", /* String */)
                .set("xi_type_of_accommodation", /* String */)
                .set("xi_room_type", /* String */)
                .set("xi_bedrooms", /* Int */)
                .set("xi_bathrooms", /* Int */)
                .set("xi_families", /* String */)
                .set("xi_kitchen_facillities", /* String */)
                .set("xi_entertainment", /* String */)
                .set("xi_room_facillities", /* String */)
                .set("xi_facilities", /* String */)
                .set("xi_special_facilities", /* String */)
                .set("xi_booking_type", /* String */)
                .set("xi_cancel_policy", /* String */)
                .set("xi_discount_id", /* Int */)
                .set("xi_discount_nm", /* String */)
                .set("xi_discount_percent", /* Double */)
                .set("xi_roomrate_weekdays", /* Double */)
                .set("xi_roomrate_weekend", /* Double */)
                .set("xi_number_of_review", /* Int */)
                .set("xi_review_rate", /* Double */)
                .set("xi_booking_id", /* String */)
                .set("xi_date_booking", /* Long */)  // Unix Timestamp: Date().time / 1000L
                .set("xi_date_checkin", /* Long */)  // Unix Timestamp: Date().time / 1000L
                .set("xi_date_checkout", /* Long */)  // Unix Timestamp: Date().time / 1000L
                .set("xi_number_of_guest", /* Int */)
                .set("xi_rental_fee", /* Double */)
                .set("xi_cleaning_fee", /* Double */)
                .set("xi_other_service_fee", /* Double */)
                .set("xi_discount_apply", /* String */)
                .set("xi_payment_type", /* String */)
                .set("xi_payment_date", /* Long */)  // Unix Timestamp: Date().time / 1000L
                .set("xi_thankyoupage", /* String */)
                .build()
        )
        .build()
)
```