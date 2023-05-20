package fr.greensky.myapplication.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.greensky.myapplication.MainActivity
import fr.greensky.myapplication.PlantModel
import fr.greensky.myapplication.PlantPopup
import fr.greensky.myapplication.PlantRepository
import fr.greensky.myapplication.R
import fr.greensky.myapplication.fragments.CollectionFragment
import fr.greensky.myapplication.fragments.HomeFragment

class PlantAdapter(public val context: MainActivity, private val plantList: List<PlantModel>, private val layoutId: Int) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName: TextView? = view.findViewById(R.id.name_item)
        val plantDescription: TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = plantList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = PlantRepository()

        val current = plantList[position]
        Glide.with(context).load(Uri.parse(current.imageUrl)).into(holder.plantImage)

        holder.plantName?.text = current.name
        holder.plantDescription?.text = current.description

        if (current.liked) {
          holder.starIcon.setImageResource(R.drawable.ic_star)
        } else {
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        holder.starIcon.setOnClickListener{
            current.liked = !current.liked
            repo.updatePlant(current)

            var fragment: Fragment?
            val nav = context.findViewById<BottomNavigationView>(R.id.navigation_bar)
            if (nav.selectedItemId == R.id.home_page) {
                fragment = HomeFragment(context)
            } else {
                fragment = CollectionFragment(context)
            }
            context.loadFragment(fragment)
        }

        holder.itemView.setOnClickListener {
            PlantPopup(this, current).show()
        }
    }
}