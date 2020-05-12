package `in`.xsight.sdk.android.sample.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        displayLoginComponent(isLoggedIn)

        signInButton.setOnClickListener(this)
        signOutButton.setOnClickListener(this)
        collectionImageView1.setOnClickListener(this)
        collectionImageView2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.signInButton -> {
                if (!isLoggedIn) {
                    isLoggedIn = true
                    displayLoginComponent(isLoggedIn)
                }
            }
            R.id.signOutButton -> {
                if (isLoggedIn) {
                    isLoggedIn = false
                    displayLoginComponent(isLoggedIn)
                }
            }
            R.id.collectionImageView1 -> {
                val intent = Intent(this, ProductActivity::class.java)
                intent.putExtra(INTENT_EXTRA_COLLECTION, COLLECTION_PARIS)
                startActivity(intent)
            }
            R.id.collectionImageView2 -> {
                val intent = Intent(this, ProductActivity::class.java)
                intent.putExtra(INTENT_EXTRA_COLLECTION, COLLECTION_HCMC)
                startActivity(intent)
            }
        }
    }

    private fun saveUserId(userId: String) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
    }

    private fun displayLoginComponent(loggedIn:Boolean) {
        signInButton.isEnabled = !loggedIn
        signOutButton.isEnabled = loggedIn
        emailEditText.isEnabled = !loggedIn
        passwordEditText.isEnabled = !loggedIn
    }
}
