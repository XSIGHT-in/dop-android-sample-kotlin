package `in`.xsight.sdk.android.sample.kotlin

import `in`.xsight.sdk.android.sample.kotlin.dummy.City
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check having logged in
        val lenUserId = loadUserId()?.length ?: 0
        isLoggedIn = (lenUserId > 0)
        displayLoginComponent(isLoggedIn)

        signInButton.setOnClickListener(this)
        signOutButton.setOnClickListener(this)
        collectionImageView1.setOnClickListener(this)
        collectionImageView2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            // OnClick Log-In
            R.id.signInButton -> {
                if (!isLoggedIn) {
                    isLoggedIn = true
                    displayLoginComponent(isLoggedIn)
                    saveUserId("XS19H71n123")
                }
            }
            // OnClick Log-Out
            R.id.signOutButton -> {
                if (isLoggedIn) {
                    isLoggedIn = false
                    displayLoginComponent(isLoggedIn)
                    saveUserId("")
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
