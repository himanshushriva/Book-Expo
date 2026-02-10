package com.himan.bookexpo.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.himan.bookexpo.R
import com.himan.bookexpo.data.remote.BookApi
import com.himan.bookexpo.data.remote.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val api = RetrofitHelper.getRetrofitInstance().create(BookApi::class.java)

        // Testing the api whether it is working or not
        lifecycleScope.launch {
            /*val response = withContext(Dispatchers.IO) {
                api.getRecentBooks()
            }*/

            try {
                Log.d(TAG, "onCreate: ${api.getRecentBooks().body()}")
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: ${e.cause}")
            }
        }
    }
}