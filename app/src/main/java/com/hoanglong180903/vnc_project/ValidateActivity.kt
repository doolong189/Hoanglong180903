package com.hoanglong180903.vnc_project

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class ValidateActivity : AppCompatActivity() {
    private lateinit var phoneNumberInput: TextInputEditText
    private lateinit var phoneNumberLayout: TextInputLayout
    private lateinit var autoCompleteTextView : AutoCompleteTextView

    private lateinit var viewPager: ViewPager2
    private lateinit var dotsIndicator: WormDotsIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate)
        validate()
        getData()
        slideShow()
    }
    private fun validate(){
        val submitButton = findViewById<Button>(R.id.submitButton)
        phoneNumberInput = findViewById(R.id.phoneNumberInput)
        phoneNumberLayout = findViewById(R.id.phoneNumberLayout)
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        submitButton.setOnClickListener {
            validatePhoneNumber()
        }
    }

    private fun validatePhoneNumber() {
        val phoneNumber = phoneNumberInput.text.toString().trim()
        if (phoneNumber.isEmpty()) {
            phoneNumberLayout.error = "Vui lòng nhập số điện thoại"
        } else if (!isValidPhoneNumber(phoneNumber)) {
            phoneNumberLayout.error = "Số điện thoại không hợp lệ"
        } else {
            phoneNumberLayout.error = null
            Toast.makeText(this, "Phone number submitted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        val phonePattern = Regex("^\\d{10,11}$")
        return phone.matches(phonePattern)
    }

    private fun getData(){
        val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this,""+autoCompleteTextView.text.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun slideShow(){
        viewPager = findViewById(R.id.viewPager)
        dotsIndicator = findViewById(R.id.dotsIndicator)
        val images = listOf(R.drawable.baseline_circle_24, R.drawable.baseline_circle_24_4,
            R.drawable.baseline_circle_24_6
        )
        val adapter = SliderAdapter(images)
//        viewPager.adapter = adapter
//        dotsIndicator.setViewPager2(viewPager)
//        dotsIndicator.attachTo(viewPager)
        viewPager.adapter = adapter
        dotsIndicator.attachTo(viewPager)
        //

//        val handler = Handler(Looper.getMainLooper())
//        val runnable = object : Runnable {
//            override fun run() {
//                val currentItem = viewPager.currentItem
//                val nextItem = if (currentItem == images.size - 1) 0 else currentItem + 1
//                viewPager.currentItem = nextItem
//                handler.postDelayed(this, 3000)  // 3 seconds delay
//            }
//        }
//        handler.postDelayed(runnable, 3000)

    }


}