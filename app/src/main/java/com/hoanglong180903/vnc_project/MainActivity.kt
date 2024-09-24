package com.hoanglong180903.vnc_project

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoanglong180903.vnc_project.adapter.ChanLeAdapter
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var tongTien = 10000
    private var isButton1Active = false
    private var isButton2Active = false
    private var statusClick: String = "" //false là chẵn // true là lẻ
    private var isEvenClicked = false // Cờ cho nút chẵn
    private var isOddClicked = false
    private val listResult = mutableListOf<Boolean>()
    private val listChan = mutableListOf<Boolean>()
    private val listLe = mutableListOf<Boolean>()
    private lateinit var chanAdapter: ChanLeAdapter
    private lateinit var leAdapter: ChanLeAdapter
    val list = mutableListOf<Int>(
        R.drawable.baseline_circle_24,
        R.drawable.baseline_circle_24_1,
        R.drawable.baseline_circle_24_2,
        R.drawable.baseline_circle_24_3,
        R.drawable.baseline_circle_24_4,
        R.drawable.baseline_circle_24_5,
        R.drawable.baseline_circle_24_6,
    )

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //new


        val sdf = SimpleDateFormat("HH:mm dd/M/yyyy")
        val currentDate = sdf.format(Date())
        Log.e("date", currentDate)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnChan = findViewById<Button>(R.id.btnChan)
        val btnLe = findViewById<Button>(R.id.btnLe)
        val imageViewKqua = findViewById<ImageView>(R.id.imageViewKqua)
        val tvTongTien = findViewById<TextView>(R.id.tvTongTien)
        val edNhapTien = findViewById<EditText>(R.id.edNhapTien)
        val symbols = DecimalFormatSymbols(Locale.US) // Sử dụng Locale.US để có dấu phẩy
        val df = DecimalFormat("#,###", symbols)
        df.roundingMode = RoundingMode.CEILING
        val formattedNumber = df.format(tongTien)
        tvTongTien.text = currentDate + "\n" + "$formattedNumber"
        edNhapTien.addTextChangedListener(object : TextWatcher {
            private var current = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

//                if (s.toString() != current) {
//                    val userInput = s.toString().replace(nonDigits,"")
//                    if (userInput.length <= 12) {
//                        current = userInput.chunked(3).joinToString(",")
//                        s!!.filters = arrayOfNulls<InputFilter>(0)
//                    }
//                    s!!.replace(0,  s.length, current, 0, current.length)
//                }
                if (s.toString() != current) {
                    val userInput = s.toString().replace(nonDigits, "").replace(",", "")

                    // Validate length ignoring commas
                    val lengthWithoutCommas = userInput.length

                    if (lengthWithoutCommas <= 12) {
                        // Format the number
                        current = formatNumber(userInput)
                        s!!.filters = arrayOfNulls<InputFilter>(0)
                    }

                    s!!.replace(0, s.length, current, 0, current.length)
                }
            }
        })
        btnPlay.setOnClickListener {
            val input = edNhapTien.text.toString()
            // Check if the input is empty
            if (input.isEmpty()) {
                edNhapTien.error = "Không được để trống!"
                return@setOnClickListener
            }
//            val inputReplace = edNhapTien.text.toString().replace(",", "").length
//            // Check if the input exceeds 12 digits
//            if (inputReplace > 12) {
//                edNhapTien.error = "Không được nhập quá 12 số!"
//                return@setOnClickListener
//            }

            // Convert input to integer and check if it exceeds tongTien
            val enteredAmount = edNhapTien.text.toString().replace(",", "").toIntOrNull()
            if (enteredAmount == null || enteredAmount > tongTien) {
                edNhapTien.error = "Số tiền không được lớn hơn $tongTien!"
                return@setOnClickListener
            }

            // Proceed with the rest of your code
            Toast.makeText(this, "Hợp lệ! Tiếp tục chơi!", Toast.LENGTH_SHORT).show()
            tvTongTien.text = edNhapTien.text.toString()
        }


        btnChan.setOnClickListener {
            if (!isButton1Active || !isOddClicked) {
                btnChan.setBackgroundColor(Color.RED)
                btnLe.setBackgroundColor(Color.LTGRAY)
                isButton1Active = true
                isButton2Active = false
                statusClick = "Chẵn"
                isOddClicked = true
            }
        }
        btnLe.setOnClickListener {
            if (!isButton2Active || !isEvenClicked) {
                btnLe.setBackgroundColor(Color.RED)
                btnChan.setBackgroundColor(Color.LTGRAY)
                isButton2Active = true
                isButton1Active = false
                statusClick = "Lẻ"
                isEvenClicked = true
            }
        }
    }

    companion object {
        private val nonDigits = Regex("[^\\d]")
    }


    private fun formatNumber(input: String): String {
        val cleanedInput = input.replace(",", "") // Remove existing commas

        return when {
            cleanedInput.length <= 3 -> cleanedInput // No formatting needed
            else -> {
                // Split the cleaned input into parts
                val lastThreeDigits = cleanedInput.takeLast(3)
                val remainingDigits = cleanedInput.dropLast(3)

                // Add commas to the remaining part (from the end)
                val formattedRemaining = remainingDigits.reversed().chunked(3).joinToString(",").reversed()

                // Combine the formatted parts
                if (formattedRemaining.isNotEmpty()) {
                    "$formattedRemaining,$lastThreeDigits"
                } else {
                    lastThreeDigits
                }
            }
        }
    }
}