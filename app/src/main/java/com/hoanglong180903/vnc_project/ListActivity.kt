package com.hoanglong180903.vnc_project

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.hoanglong180903.vnc_project.model.Ban
import com.hoanglong180903.vnc_project.model.HoaDon
import com.hoanglong180903.vnc_project.model.LichSuDatBan
import com.hoanglong180903.vnc_project.model.SanPham
import com.hoanglong180903.vnc_project.viewmodel.RetrofitViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListActivity : AppCompatActivity() {
    private val listBan: MutableList<Ban> = ArrayList()
//    private val listSanPham: MutableList<SanPham> = ArrayList()

    //    private val listHoaDon : MutableList<HoaDon> = ArrayList()
//    private val listBan  = ArrayList<Ban>()
    private val listSanPham  = ArrayList<SanPham>()
    private val listHoaDon = ArrayList<HoaDon>()
    private val listLichSuDatBan = ArrayList<LichSuDatBan>()
    private lateinit var retrofitViewModel: RetrofitViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_item)
        replaceFragment(ListFragment())
        askNotificationPermission()
    }

    private fun init(){
        retrofitViewModel = ViewModelProvider(this, RetrofitViewModel.RetrofitViewModelFactory(application))[RetrofitViewModel::class.java]
    }

    private fun replaceFragment(fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.main_frame, fragment)
        transaction.commit()
    }

    fun getListBan(): MutableList<Ban> {
        val listBan: MutableList<Ban> = mutableListOf(
            Ban(0, "Bàn 1", false),
            Ban(1, "Bàn 2", false),
            Ban(2, "Bàn 3", false),
            Ban(3, "Bàn 4", false)
        )
        return listBan
    }

    fun setBan(item: Ban) {
        listBan.add(item)
    }

    fun getListSanPham(): List<SanPham> {
        val listSanPham: List<SanPham> = listOf(
            SanPham(0, "Bia", "",7000, 0),
            SanPham(1, "CoCa","", 10000, 0),
            SanPham(2, "Fanta","", 5000, 0),
            SanPham(3, "Pepsi","", 10000, 0),
            SanPham(4, "Mixue","", 10000, 0),
            SanPham(5, "Chivas", "",10000, 0),
            SanPham(6, "ABC", "",10000, 0),
            SanPham(7, "XYZ", "",10000, 0),
        )
        return listSanPham
    }
//    fun getListSanPham(): MutableList<SanPham> {
//        val listSanPham: MutableList<SanPham> = mutableListOf(
//            SanPham(0, "Bia", 7000, 0),
//            SanPham(1, "CoCa", 10000, 0),
//            SanPham(2, "Fanta", 5000, 0),
//            SanPham(3, "Pepsi", 10000, 0)
//        )
//        return listSanPham
//    }

    fun setSanPham(item: SanPham) {
        listSanPham.add(item)
    }

    fun getListHoaDon(): MutableList<HoaDon> {
        return listHoaDon
    }

    fun setHoaDon(item: HoaDon) {
        listHoaDon.add(item)
    }

    fun setUpdateHoaDon(item: HoaDon, position : Int) {
        listHoaDon.set(position,item)
    }

    fun getListLichSuDatBan(): MutableList<LichSuDatBan> {
        return listLichSuDatBan
    }

    fun setLichSuDatBan(item: LichSuDatBan) {
        listLichSuDatBan.add(item)
    }

     fun requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED){

            }else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)){

            }else{
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }else{
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FirebaseLog", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                Log.e("FirebaseLog", "Fetching FCM registration token failed $token")

            })
        }
    }
    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FirebaseLog", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                Log.e("FirebaseLog", "Fetching FCM registration token failed $token")

            })
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }else{
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FirebaseLog", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                Log.e("FirebaseLog", "Fetching FCM registration token failed $token")

            })
        }
    }
}