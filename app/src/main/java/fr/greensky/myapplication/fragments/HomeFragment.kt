package fr.greensky.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.greensky.myapplication.MainActivity
import fr.greensky.myapplication.PlantRepository.Singleton.plantList
import fr.greensky.myapplication.R
import fr.greensky.myapplication.adapter.PlantAdapter
import fr.greensky.myapplication.adapter.PlantItemDecoration

class HomeFragment(private val context: MainActivity) : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)
        context.findViewById<TextView>(R.id.page_title).text = "À découvrir"

        val recycler = view?.findViewById<RecyclerView>(R.id.views_recycle)
        recycler?.adapter = PlantAdapter(context, plantList.filter{ !it.liked }, R.layout.item_horizontal_plant)

        val verticalRecycler = view?.findViewById<RecyclerView>(R.id.vertical_recycle_view)
        verticalRecycler?.adapter = PlantAdapter(context, plantList, R.layout.item_vertical_plant)
        verticalRecycler?.addItemDecoration(PlantItemDecoration())

        return view
    }
}