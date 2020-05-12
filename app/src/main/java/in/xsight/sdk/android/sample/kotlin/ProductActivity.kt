package `in`.xsight.sdk.android.sample.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // Set collection name as title
        val collectionName = intent.getStringExtra(INTENT_EXTRA_COLLECTION)
        setTitle(collectionName)
    }
}
