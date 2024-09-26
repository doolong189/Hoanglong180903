package com.hoanglong180903.vnc_project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hoanglong180903.vnc_project.adapter.HoaDonAdapter
import com.hoanglong180903.vnc_project.adapter.LichSuDatBanAdapter
import com.hoanglong180903.vnc_project.listener.OnClickItemHoaDon
import com.hoanglong180903.vnc_project.model.HoaDon
import com.hoanglong180903.vnc_project.model.LichSuDatBan


class LichSuDatBanFragment : Fragment(), OnClickItemHoaDon {
    private lateinit var rcLichSuDatBan: RecyclerView
    private lateinit var lichSuDatBanAdapter: LichSuDatBanAdapter
    private lateinit var btnBack: FloatingActionButton
    private lateinit var btnFilter: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lich_su_dat_ban, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        initVariable()
    }

    private fun init(view: View) {
        rcLichSuDatBan = view.findViewById(R.id.rc_lichSu)
        btnBack = view.findViewById(R.id.lichSu_btnBack)
        btnFilter = view.findViewById(R.id.lichSu_btnFilter)
    }

    private fun initVariable() {
        rcLichSuDatBan.layoutManager = LinearLayoutManager(context)
        lichSuDatBanAdapter =
            LichSuDatBanAdapter(requireContext(), (activity as ListActivity).getListLichSuDatBan(), this)
        rcLichSuDatBan.adapter = lichSuDatBanAdapter
        btnBack.setOnClickListener { replaceFragment(ListFragment()) }
        btnFilter.setOnClickListener {
//            rcLichSuDatBan.layoutManager = LinearLayoutManager(context)
//            lichSuDatBanAdapter =
//                LichSuDatBanAdapter(requireContext(), (activity as ListActivity).getListLichSuDatBan(), this)
//            rcLichSuDatBan.adapter = lichSuDatBanAdapter
            val filterList = timKiemTheoNgay((activity as ListActivity).getListLichSuDatBan(),"10/10/2024")
            Log.e("filter",""+filterList)
            lichSuDatBanAdapter =
                LichSuDatBanAdapter(requireContext(), filterList, this)
            rcLichSuDatBan.adapter = lichSuDatBanAdapter
            lichSuDatBanAdapter.notifyDataSetChanged()
        }
        Log.e("zzz",""+(activity as ListActivity).getListLichSuDatBan())
    }

    override fun onClickItem(item: HoaDon, position: Int) {
        TODO("Not yet implemented")
    }

    private fun replaceFragment(fragment: Fragment) {
        val manager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.main_frame, fragment)
        transaction.commit()
    }

    fun timKiemTheoNgay(list : MutableList<LichSuDatBan> , strDate : String) : List<LichSuDatBan>{
        val listTemp = mutableListOf<LichSuDatBan>()
        list.forEach {
            val item = it.hoadons.find { it.ngayTao == strDate }
            if (item != null){
                listTemp.add(it)
            }else{
                listTemp.remove(it)
            }
        }
        return listTemp
    }
}