package `in`.xsight.sdk.android.sample.kotlin

import `in`.xsight.sdk.android.sample.kotlin.dummy.City
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import android.view.View
import com.gruter.sdk.open.api.DOX
import com.gruter.sdk.open.model.XEvent
import com.gruter.sdk.open.model.XIdentify
import com.gruter.sdk.open.model.XProperties
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /******************************
         * XSIGHT.in SDK
         */
        DOX.initialization(this)
        /******************************/

        // Check having logged in
        val userId = loadUserId() ?: ""
        isLoggedIn = userId.isNotEmpty()
        displayLoginComponent(isLoggedIn)
        DOX.setUserId(userId)

        signInButton.setOnClickListener(this)
        signOutButton.setOnClickListener(this)
        collectionImageView1.setOnClickListener(this)
        collectionImageView2.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        /******************************
         * XSIGHT.in SDK
         */
        DOX.setEventGroupName("Visit_Main_Page")
        DOX.logEvent(
            XEvent.Builder()
                .setEventName("Visit_Main_Page")
                .setProperties(
                    XProperties.Builder()
                        .set("xi_is_host", "Guest")
                        .build()
                )
                .build()
        )
        /******************************/
    }

    override fun onClick(v: View) {
        when (v.id) {
            // OnClick Log-In
            R.id.signInButton -> {
                if (!isLoggedIn) {
                    isLoggedIn = true
                    displayLoginComponent(isLoggedIn)
                    saveUserId("XS19H71n123")
                    /******************************
                     * XSIGHT.in SDK
                     */
//                    GlobalScope.launch {
                        DOX.setUserId("XS19H71n123")
                        Log.d("XSIGHT.in", "Start for Sign_In logEvent()")
                        DOX.logEvent(
                            XEvent.Builder()
                                .setEventName("Sign_In")
                                .setProperties(
                                    XProperties.Builder()
                                        .set("xi_email", "test@mail.com")
                                        .set("xi_gender", "F")
                                        .set("xi_timezone", "84")
                                        .set("xi_email", "test@mail.com")
                                        .set("xi_fb_id", "test@mail.com")
                                        .set("xi_google_id", "test@mail.com")
                                        .set("xi_status", "Active")
                                        .set("xi_is_host", "Guest")
                                        .build()
                                )
                                .build()
                        )
                        Log.d("XSIGHT.in", "Start for Sign_In userIdentify()")
                        DOX.userIdentify(
                            XIdentify.Builder()
                                .setOnce("user_id", "XS19H71n123")
                                .set("xi_email", "test@mail.com")
                                .set("xi_gender", "F")
                                .set("xi_timezone", "84")
                                .set("xi_email", "test@mail.com")
                                .set("xi_fb_id", "test@mail.com")
                                .set("xi_google_id", "test@mail.com")
                                .set("xi_status", "Active")
                                .set("xi_is_host", "Guest")
                                .build()
                        )

//                    }
                    /******************************/
                }
            }
            // OnClick Log-Out
            R.id.signOutButton -> {
                if (isLoggedIn) {
                    isLoggedIn = false
                    displayLoginComponent(isLoggedIn)
                    saveUserId("")

                    /******************************
                     * XSIGHT.in SDK
                     */
                    DOX.setUserId("")
                    Log.d("XSIGHT.in", "Start for Sign_Out logEvent()")
                    DOX.logEvent(
                        XEvent.Builder()
                            .setEventName("Sign_Out")
                            .setProperties(
                                XProperties.Builder()
                                    .set("xi_is_host", "Guest")
                                    .build()
                            )
                            .build()
                    )
                    /******************************/
                }
            }
            R.id.collectionImageView1 -> {
                val cityInfo = City(
                    cityId = 99910,
                    cityName = "Paris",
                    collectionId = 90000,
                    collectionName = "Best Seller",
                    themeId = 99991,
                    themeName = "Near By Eiffel Tower"
                )
                toCollectionActivity(cityInfo)
            }
            R.id.collectionImageView2 -> {
                val cityInfo = City(
                    cityId = 99920,
                    cityName = "Ho Chi Minh City",
                    collectionId = 92000,
                    collectionName = "Hot List",
                    themeId = 99992,
                    themeName = "District 1"
                )
                toCollectionActivity(cityInfo)
            }
        }
    }

    private fun toCollectionActivity(cityInfo: City) {
        val intent = Intent(this, CollectionActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(PARCEL_KEY_CITY, cityInfo)
        intent.putExtra(BUNDLE_KEY, bundle)
        startActivity(intent)
    }

    private fun saveUserId(userId: String) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putString(PREFERENCE_KEY_USER_ID, userId).apply()
    }

    private fun loadUserId(): String? {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        return pref.getString(PREFERENCE_KEY_USER_ID, null)
    }

    private fun displayLoginComponent(loggedIn:Boolean) {
        signInButton.isEnabled = !loggedIn
        signOutButton.isEnabled = loggedIn
        emailEditText.isEnabled = !loggedIn
        passwordEditText.isEnabled = !loggedIn
    }
}
