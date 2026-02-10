package com.himan.bookexpo.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.himan.bookexpo.R
import com.himan.bookexpo.data.remote.BookApi
import com.himan.bookexpo.data.remote.RetrofitHelper
import com.himan.bookexpo.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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