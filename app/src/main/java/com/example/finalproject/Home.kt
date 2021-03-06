package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.databinding.FragmentNoteBinding
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter_h: RecyclerViewAdapterOfHome? = null
    private lateinit var sqliteHelper_c: SQLiteHelper_c

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val img_home:ImageView = view.findViewById(R.id.imageView_home)
        img_home.setImageResource(R.drawable.home)

        layoutManager = LinearLayoutManager(view.context)
        val recycleView:RecyclerView = view.findViewById(R.id.recyclerview_home)
        recycleView.layoutManager = layoutManager

        adapter_h = RecyclerViewAdapterOfHome()
        recycleView.adapter = adapter_h

        sqliteHelper_c = SQLiteHelper_c(view.context)
        getChecklist(getNow())

        adapter_h!!.setOnClickItem {
            val intent = Intent(view.context,record_checklist::class.java).apply {  }
            startActivity(intent)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        getChecklist(getNow())
    }

    private fun getNow(): String {
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            return SimpleDateFormat("yyyy-MM-dd").format(Date())
        } else {
            var tms = Calendar.getInstance()
            return tms.get(Calendar.YEAR).toString() + "-" + tms.get(Calendar.MONTH)
                .toString() + "-" + tms.get(Calendar.DAY_OF_MONTH).toString()
        }
    }
    private fun getChecklist(date: String) {
        val checklistList = sqliteHelper_c.getAllChecklist(date)
        adapter_h?.addItems(checklistList)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}