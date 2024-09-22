package com.hoanglong180903.vnc_project

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.hoanglong180903.vnc_project.adapter.BanAdapter
import com.hoanglong180903.vnc_project.adapter.HoaDonAdapter
import com.hoanglong180903.vnc_project.adapter.SanPhamAdapter
import com.hoanglong180903.vnc_project.listener.OnClickItemBan
import com.hoanglong180903.vnc_project.listener.OnClickItemHoaDon
import com.hoanglong180903.vnc_project.listener.OnClickItemSanPham
import com.hoanglong180903.vnc_project.model.Ban
import com.hoanglong180903.vnc_project.model.HoaDon
import com.hoanglong180903.vnc_project.model.SanPham


class ListFragment : Fragment(), OnClickItemBan, OnClickItemSanPham, OnClickItemHoaDon {
    private lateinit var hoaDonAdapter: HoaDonAdapter
    private lateinit var banAdapter: BanAdapter
    private lateinit var sanPhamAdapter: SanPhamAdapter
    private lateinit var rcHoaDon: RecyclerView
    private lateinit var flBtn: FloatingActionButton
    private var banTemp = Ban()
    private lateinit var hoaDonTemp: HoaDon
    private var listSanPhamTemp = ArrayList<SanPham>()
    private var tongTien = 0
    private var tamThoiList: List<SanPham>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initVariable()
    }

    private fun initView(view: View) {
        rcHoaDon = view.findViewById(R.id.rcHoaDon)
        flBtn = view.findViewById(R.id.floatBtn)
    }

    private fun initVariable() {
        rcHoaDon.layoutManager = LinearLayoutManager(context)
        hoaDonAdapter =
            HoaDonAdapter(requireContext(), (activity as ListActivity).getListHoaDon(), this)
        rcHoaDon.adapter = hoaDonAdapter
        Log.e("list_hoa_don", "" + (activity as ListActivity).getListHoaDon())
        flBtn.setOnClickListener { dialog() }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rcHoaDon)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun dialog() {
        val builder = AlertDialog.Builder(context)
        val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_dat_ban, null)
        builder.setView(view)
        val dialog: Dialog = builder.create()
        dialog.show()
        val window = dialog.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window.attributes
        windowAttributes.gravity = Gravity.CENTER
        window.attributes = windowAttributes
        val rcBan = view.findViewById<RecyclerView>(R.id.dl_hoa_don_rcBan)
        val rcSanPham = view.findViewById<RecyclerView>(R.id.dl_hoa_don_rcSanPham)
        val btnCancel = view.findViewById<Button>(R.id.dl_hoa_don_btnCancel)
        val btnSave = view.findViewById<Button>(R.id.dl_hoa_don_btnSave)
        //bàn
        rcBan.layoutManager = GridLayoutManager(context, 4)
        banAdapter = BanAdapter(
            requireContext(),
            (activity as ListActivity).getListBan(),
            this,
            (activity as ListActivity).getListHoaDon()
        )
        rcBan.adapter = banAdapter

        //sanpham
        rcSanPham.layoutManager = LinearLayoutManager(context)
        sanPhamAdapter =
            SanPhamAdapter(requireContext(), (activity as ListActivity).getListSanPham(), this)
        rcSanPham.adapter = sanPhamAdapter
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        for (x in listSanPhamTemp) {
            tongTien += (x.giaTien * x.soLuong)
        }
        btnSave.setOnClickListener {
            if (banTemp.ten == ""){
                Toast.makeText(requireContext(),"Xin mời chọn số bàn",Toast.LENGTH_LONG).show()
            }else if (listSanPhamTemp.isEmpty()){
                Toast.makeText(requireContext(),"Xin mời chọn món ăn",Toast.LENGTH_LONG).show()
            }else{
                val listAddSanPham = ArrayList<SanPham>()
                listAddSanPham.addAll(listSanPhamTemp)
                hoaDonTemp = HoaDon(
                    (activity as ListActivity).getListHoaDon().size,
                    banTemp,
                    listAddSanPham,
                    tongTien,
                    false
                )
                (activity as ListActivity).setHoaDon(hoaDonTemp)
                initVariable()
                hoaDonAdapter.notifyDataSetChanged()
                listSanPhamTemp.clear()
                dialog.dismiss()
            }
        }
    }

    override fun onClickItem(item: Ban, position: Int) {
        banTemp = Ban(item.id, item.ten, true)
    }

    override fun onClickItem(item: SanPham, position: Int) {
        val existingItem = listSanPhamTemp.find { it.id == item.id }

        if (existingItem != null) {
            if (item.soLuong > 0) {
                existingItem.soLuong = item.soLuong
            } else if (item.soLuong == 0) {
                listSanPhamTemp.remove(existingItem)
            }
        } else {
            listSanPhamTemp.add(SanPham(item.id, item.ten, item.giaTien, item.soLuong))
        }
        Log.e("list_sp_temp", listSanPhamTemp.toString())
    }


    override fun onClickItem(item: HoaDon, position: Int) {
        listSanPhamTemp.addAll(item.sanPhams)
        dialogSuaDatBan(item, position)
    }


    @SuppressLint("MissingInflatedId")
    private fun dialogSuaDatBan(item: HoaDon, position: Int) {
        val builder = AlertDialog.Builder(context)
        val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_sua_dat_ban, null)
        builder.setView(view)
        val dialog: Dialog = builder.create()
        dialog.show()
        val window = dialog.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window.attributes
        windowAttributes.gravity = Gravity.CENTER
        window.attributes = windowAttributes
        val btnThanhToan = view.findViewById<Button>(R.id.dl_cthd_btnThanhToan)
        val btnHuyBo = view.findViewById<Button>(R.id.dl_cthd_btnHuyBo)
        val btnCapNhat = view.findViewById<Button>(R.id.dl_cthd_btnCapNhat)
        val tvEditBan = view.findViewById<TextView>(R.id.dl_cthd_tvBan)
        val rcEditSanPham = view.findViewById<RecyclerView>(R.id.dl_cthd_rcSanPham)


        tvEditBan.text = item.ban.ten
        //
        val tamThoiList = themSanPhamVaoListTamThoi(item, (activity as ListActivity).getListSanPham())
        Log.e("tamThoiList",""+tamThoiList)
        //sanpham
        rcEditSanPham.layoutManager = LinearLayoutManager(context)
        sanPhamAdapter =
            SanPhamAdapter(requireContext(), tamThoiList, this)
        rcEditSanPham.adapter = sanPhamAdapter


        btnThanhToan.setOnClickListener {
            (activity as ListActivity).getListHoaDon()[position] =
                HoaDon(item.id, item.ban, item.sanPhams, item.tongTien, true)
            initVariable()
            hoaDonAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        btnHuyBo.setOnClickListener {
            dialog.dismiss()
        }
        btnCapNhat.setOnClickListener {
            val listAddSanPham = ArrayList<SanPham>()
            listAddSanPham.addAll(listSanPhamTemp)
            hoaDonTemp = HoaDon(
                (activity as ListActivity).getListHoaDon().size,
                banTemp,
                listAddSanPham,
                tongTien,
                false
            )
            (activity as ListActivity).setUpdateHoaDon(hoaDonTemp,position)
            initVariable()
            hoaDonAdapter.notifyDataSetChanged()
            listSanPhamTemp.clear()
            dialog.dismiss()
        }
    }


    private val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            private var p: Paint = Paint().apply {
                isAntiAlias = true
                style = Paint.Style.FILL
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Thông báo!")
                    .setMessage("Bạn có chắc chắn muốn xóa không?")
                    .setPositiveButton("Đồng ý") { dialog, which ->
                        (activity as ListActivity).getListHoaDon().removeAt(position)
                        hoaDonAdapter.notifyDataSetChanged()
                        dialog.dismiss()
                    }

                    .setNegativeButton("Hủy bỏ") { dialog, which ->
                        dialog.dismiss()
                        initVariable()
                    }
                    .setCancelable(false)
                alertDialog.show()
            }

//            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//                    val itemView = viewHolder.itemView
//                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
//                    val width = height / 3
//
//                    if (dX < 0) {
//                        p.color = Color.RED
//                        val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
//                        c.drawRect(background, p)
////                        val icon = BitmapFactory.decodeResource(requireActivity().resources, R.drawable.baseline_add_circle_24)
//                        val icon = getBitmapFromVectorDrawable(context!!, R.drawable.baseline_add_circle_24)
//                        if (icon != null) {
//                            val margin = (dX / 5 - width) / 2
//                            val iconDest = RectF(
//                                itemView.right.toFloat() + margin,
//                                itemView.top.toFloat() + width,
//                                itemView.right.toFloat() + (margin + width),
//                                itemView.bottom.toFloat() - width
//                            )
//                            c.drawBitmap(icon, null, iconDest, p)
//                        } else {
//                            Log.e("Failed_icon", "Failed to load icon for add action.")
//                        }
//                    }
//                    if (dX < 0) {
//                        p.color = Color.BLUE
//                        val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), itemView.left.toFloat() + dX, itemView.bottom.toFloat())
//                        c.drawRect(background, p)
//                        val icon = getBitmapFromVectorDrawable(context!!, R.drawable.baseline_remove_circle_24)
//                        if (icon != null) {
//                            val margin = (dX / 5 - width) / 2
//                            val iconDest = RectF(
//                                itemView.right.toFloat() + margin,
//                                itemView.top.toFloat() + width,
//                                itemView.right.toFloat() + (margin + width),
//                                itemView.bottom.toFloat() - width
//                            )
//                            c.drawBitmap(icon, null, iconDest, p)
//                        } else {
//                            Log.e("Failed_icon", "Failed to load icon for add action.")
//                        }
//                    }
//                } else {
//                    c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
//                }
//                super.onChildDraw(c, recyclerView, viewHolder, dX / 5, dY, actionState, isCurrentlyActive)
//            }
        }
    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, drawableId) ?: return null
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    private fun themSanPhamVaoListTamThoi(hoaDon: HoaDon, sanPhamList: List<SanPham>): List<SanPham> {
        val tamThoiList = mutableListOf<SanPham>()

        sanPhamList.forEach { sanPham ->
            val sanPhamTrung = hoaDon.sanPhams.find { it.id == sanPham.id }

            if (sanPhamTrung != null) {
                tamThoiList.add(sanPham.copy(soLuong = sanPhamTrung.soLuong))
            } else {
                tamThoiList.add(sanPham.copy(soLuong = 0))
            }
        }
        return tamThoiList
    }

}