package com.hoanglong180903.vnc_project

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var tongTien = 10000
    private var isButton1Active = false
    private var isButton2Active = false
    private var statusClick : String = "" //false là chẵn // true là lẻ
    val list = mutableListOf<Int>(
        R.drawable.baseline_commute_24,
        R.drawable.baseline_coronavirus_24,
        R.drawable.baseline_connecting_airports_24,

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

        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnChan = findViewById<Button>(R.id.btnChan)
        val btnLe = findViewById<Button>(R.id.btnLe)
        val tvRandom = findViewById<TextView>(R.id.tvRandomNumber)
        val tvTongTien = findViewById<TextView>(R.id.tvTongTien)
        val edNhapTien = findViewById<EditText>(R.id.edNhapTien)
        val imageChanLe = findViewById<ImageView>(R.id.imageChanLe)
        tvTongTien.text = "Tổng số tiền: " + tongTien
        btnPlay.setOnClickListener {
            if (edNhapTien.text.toString() != "" && edNhapTien.text.toString().toInt() > tongTien) {
                Toast.makeText(this, "Không nhập quá số tiền", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (edNhapTien.text.toString() == "") {
                Toast.makeText(this, "Yêu cầu nhập số tiền", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else if (statusClick == "") {
                Toast.makeText(this, "Yêu cầu chọn chẵn hoặc lẻ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                btnPlay.text = "..."
                btnPlay.setBackgroundResource(R.color.orange)
                btnPlay.isEnabled = false
                val handler = Handler(Looper.getMainLooper())
                var index = 0
                val runnable = object : Runnable {
                    override fun run() {
                        if (index < list.size) {
                            imageChanLe.setImageResource(list[index])
                            index++
                            handler.postDelayed(this, 1000)
                        }else{
                            val randomNumber = (0 until 100).random()
                            tvRandom.text = randomNumber.toString()
                            if (randomNumber % 2 == 0 && statusClick == "Chẵn") {
                                Toast.makeText(applicationContext, "Bạn chọn chẵn - kết quả chẵn", Toast.LENGTH_LONG).show()
                                tongTien += edNhapTien.text.toString().toInt()
                            } else if (randomNumber % 2 == 0 && statusClick == "Lẻ") {
                                tongTien -= edNhapTien.text.toString().toInt()
                                Toast.makeText(applicationContext, "Bạn chọn chẵn - kết quả lẻ", Toast.LENGTH_LONG).show()
                            } else if (randomNumber % 1 == 0 && statusClick ==  "Lẻ") {
                                tongTien += edNhapTien.text.toString().toInt()
                                Toast.makeText(applicationContext, "Bạn chọn lẻ - kết quả lẻ", Toast.LENGTH_LONG).show()
                            } else if (randomNumber % 1 == 0 && statusClick == "Chẵn") {
                                tongTien -= edNhapTien.text.toString().toInt()
                                Toast.makeText(applicationContext, "Bạn chọn lẻ - kết quả chẵn", Toast.LENGTH_LONG).show()
                            }
                            tvTongTien.text = "Tổng số tiền: " + tongTien
                            btnPlay.text = "Play"
                            btnPlay.setBackgroundResource(R.color.green)
                            btnPlay.isEnabled = true
                            imageChanLe.visibility = View.GONE
                        }
                    }
                }
                handler.post(runnable)
            }
        }
        btnChan.setOnClickListener {
            if (!isButton1Active) {
                btnChan.setBackgroundColor(Color.RED)
                btnLe.setBackgroundColor(Color.LTGRAY)
                isButton1Active = true
                isButton2Active = false
                statusClick = "Chẵn"
            }
        }

        btnLe.setOnClickListener {
            if (!isButton2Active) {
                btnLe.setBackgroundColor(Color.RED)
                btnChan.setBackgroundColor(Color.LTGRAY)
                isButton2Active = true
                isButton1Active = false
                statusClick = "Lẻ"
            }
        }
    }
}