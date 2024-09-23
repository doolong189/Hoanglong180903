package com.hoanglong180903.vnc_project

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import java.text.SimpleDateFormat
import java.util.Date
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


        val sdf = SimpleDateFormat("hh:mm dd/M/yyyy")
        val currentDate = sdf.format(Date())
        Log.e("date", currentDate)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnChan = findViewById<Button>(R.id.btnChan)
        val btnLe = findViewById<Button>(R.id.btnLe)
        val imageViewKqua = findViewById<ImageView>(R.id.imageViewKqua)
        val tvTongTien = findViewById<TextView>(R.id.tvTongTien)
//        btnChan.setOnClickListener {
//            isOddClicked = true
//        }
//        btnLe.setOnClickListener {
//            isEvenClicked = true
//        }
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
        btnPlay.setOnClickListener {
            if (!isEvenClicked && !isOddClicked) {
                Toast.makeText(this, "Cả 2 nút chưa được click", Toast.LENGTH_SHORT).show()
            } else {
                listResult.add(kotlin.random.Random.nextBoolean())
                listResult.add(kotlin.random.Random.nextBoolean())
                listResult.add(kotlin.random.Random.nextBoolean())
                listResult.add(kotlin.random.Random.nextBoolean())
                for (result in listResult) {
                    if (result) {
                        listChan.add(result)
                        Log.e("zzz1", "" + listChan)
                    } else {
                        listLe.add(result)
                        Log.e("zzz2", "" + listLe)
                    }
                }
                Log.e("zzz", "" + listResult)
                btnPlay.text = "..."
                btnPlay.setBackgroundResource(R.color.orange)
                btnPlay.isEnabled = false
                val handler = Handler(Looper.getMainLooper())
                var index = 0
                val runnable = object : Runnable {
                    override fun run() {
                        if (index < list.size) {
                            imageViewKqua.setImageResource(list[index])
                            index++
                            handler.postDelayed(this, 1000)
                        } else {
                            val recyclerView = findViewById<RecyclerView>(R.id.rcChan)
                            recyclerView.layoutManager =
                                LinearLayoutManager(
                                    applicationContext,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            chanAdapter = ChanLeAdapter(listChan, true)
                            recyclerView.adapter = chanAdapter

                            val recyclerView1 = findViewById<RecyclerView>(R.id.rcLe)
                            recyclerView1.layoutManager =
                                LinearLayoutManager(
                                    applicationContext,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            leAdapter = ChanLeAdapter(listLe, false)
                            recyclerView1.adapter = leAdapter
                            if (listChan.size == listLe.size || listChan.isEmpty() || listLe.isEmpty()) {
                                Toast.makeText(applicationContext, "Chẵn", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(applicationContext, "Lẻ", Toast.LENGTH_LONG).show()
                            }
//                            tvRandom.text = randomNumber.toString()
//                            if (randomNumber % 2 == 0 && statusClick == "Chẵn") {
//                                Toast.makeText(applicationContext, "Bạn chọn chẵn - kết quả chẵn", Toast.LENGTH_LONG).show()
//                                tongTien += edNhapTien.text.toString().toInt()
//                            } else if (randomNumber % 2 == 0 && statusClick == "Lẻ") {
//                                tongTien -= edNhapTien.text.toString().toInt()
//                                Toast.makeText(applicationContext, "Bạn chọn chẵn - kết quả lẻ", Toast.LENGTH_LONG).show()
//                            } else if (randomNumber % 1 == 0 && statusClick ==  "Lẻ") {
//                                tongTien += edNhapTien.text.toString().toInt()
//                                Toast.makeText(applicationContext, "Bạn chọn lẻ - kết quả lẻ", Toast.LENGTH_LONG).show()
//                            } else if (randomNumber % 1 == 0 && statusClick == "Chẵn") {
//                                tongTien -= edNhapTien.text.toString().toInt()
//                                Toast.makeText(applicationContext, "Bạn chọn lẻ - kết quả chẵn", Toast.LENGTH_LONG).show()
//                            }
//                            tvTongTien.text = "Tổng số tiền: " + tongTien
                            btnPlay.text = "Play"
                            btnPlay.setBackgroundResource(R.color.green)
                            btnPlay.isEnabled = true
                            isOddClicked = false
                            isEvenClicked = false
                            btnChan.setBackgroundColor(Color.LTGRAY)
                            btnLe.setBackgroundColor(Color.LTGRAY)
                            tvTongTien.setOnClickListener {
                                listChan.clear()
                                listLe.clear()
                                listResult.clear()
                                recyclerView.layoutManager =
                                    LinearLayoutManager(
                                        applicationContext,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                chanAdapter = ChanLeAdapter(listChan, true)
                                recyclerView.adapter = chanAdapter

                                recyclerView1.layoutManager =
                                    LinearLayoutManager(
                                        applicationContext,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                leAdapter = ChanLeAdapter(listLe, false)
                                recyclerView1.adapter = leAdapter
                            }
                        }
                    }
                }
                handler.post(runnable)
            }
        }
    }

//        val btnPlay = findViewById<Button>(R.id.btnPlay)
//        val btnChan = findViewById<Button>(R.id.btnChan)
//        val btnLe = findViewById<Button>(R.id.btnLe)
//        val tvRandom = findViewById<TextView>(R.id.tvRandomNumber)
//        val tvTongTien = findViewById<TextView>(R.id.tvTongTien)
//        val edNhapTien = findViewById<EditText>(R.id.edNhapTien)
//        val imageChanLe = findViewById<ImageView>(R.id.imageChanLe)
//        tvTongTien.text = "Tổng số tiền: " + tongTien
//        btnPlay.setOnClickListener {
//            if (edNhapTien.text.toString() != "" && edNhapTien.text.toString().toInt() > tongTien) {
//                Toast.makeText(this, "Không nhập quá số tiền", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            } else if (edNhapTien.text.toString() == "") {
//                Toast.makeText(this, "Yêu cầu nhập số tiền", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//
//            } else if (statusClick == "") {
//                Toast.makeText(this, "Yêu cầu chọn chẵn hoặc lẻ", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            } else {
//                btnPlay.text = "..."
//                btnPlay.setBackgroundResource(R.color.orange)
//                btnPlay.isEnabled = false
//                val handler = Handler(Looper.getMainLooper())
//                var index = 0
//                val runnable = object : Runnable {
//                    override fun run() {
//                        if (index < list.size) {
//                            imageChanLe.setImageResource(list[index])
//                            index++
//                            handler.postDelayed(this, 1000)
//                        }else{
//                            val randomNumber = (0 until 100).random()
//                            tvRandom.text = randomNumber.toString()
//                            if (randomNumber % 2 == 0 && statusClick == "Chẵn") {
//                                Toast.makeText(applicationContext, "Bạn chọn chẵn - kết quả chẵn", Toast.LENGTH_LONG).show()
//                                tongTien += edNhapTien.text.toString().toInt()
//                            } else if (randomNumber % 2 == 0 && statusClick == "Lẻ") {
//                                tongTien -= edNhapTien.text.toString().toInt()
//                                Toast.makeText(applicationContext, "Bạn chọn chẵn - kết quả lẻ", Toast.LENGTH_LONG).show()
//                            } else if (randomNumber % 1 == 0 && statusClick ==  "Lẻ") {
//                                tongTien += edNhapTien.text.toString().toInt()
//                                Toast.makeText(applicationContext, "Bạn chọn lẻ - kết quả lẻ", Toast.LENGTH_LONG).show()
//                            } else if (randomNumber % 1 == 0 && statusClick == "Chẵn") {
//                                tongTien -= edNhapTien.text.toString().toInt()
//                                Toast.makeText(applicationContext, "Bạn chọn lẻ - kết quả chẵn", Toast.LENGTH_LONG).show()
//                            }
//                            tvTongTien.text = "Tổng số tiền: " + tongTien
//                            btnPlay.text = "Play"
//                            btnPlay.setBackgroundResource(R.color.green)
//                            btnPlay.isEnabled = true
//                            imageChanLe.visibility = View.GONE
//                        }
//                    }
//                }
//                handler.post(runnable)
//            }
//        }
}