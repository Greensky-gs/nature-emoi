package fr.greensky.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.greensky.myapplication.MainActivity
import fr.greensky.myapplication.PlantRepository
import fr.greensky.myapplication.R
import fr.greensky.myapplication.adapter.PlantAdapter
import fr.greensky.myapplication.adapter.PlantItemDecoration

class CollectionFragment(private val context: MainActivity) : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_collection, container, false)
        context.findViewById<TextView>(R.id.page_title).text = "Ma collection"

        val recycler = view?.findViewById<RecyclerView>(R.id.collection_recycler)
        recycler?.adapter = PlantAdapter(context, PlantRepository.Singleton.plantList.filter { it.liked }, R.layout.item_vertical_plant)
        recycler?.layoutManager = LinearLayoutManager(context)
        recycler?.addItemDecoration(PlantItemDecoration())

        return view
    }
}