package fr.greensky.myapplication

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.greensky.myapplication.adapter.PlantAdapter

class PlantPopup(private val adapter: PlantAdapter, private val plant: PlantModel) : Dialog(adapter.context) {
    val repo = PlantRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plant_details)

        setupComponent()
        setupStarButton()
        setupCloseButton()
        setupDeleteButton()
        setupLikeButton()
    }

    private fun setupStarButton() {
        val btn = findViewById<ImageView>(R.id.star_button)
        if (plant.liked) {
            btn.setImageResource(R.drawable.ic_star)
        } else {
            btn.setImageResource(R.drawable.ic_unstar)
        }
    }
    private fun setupLikeButton() {
        findViewById<ImageView>(R.id.star_button).setOnClickListener {
            plant.liked = !plant.liked
            repo.updatePlant(plant)

            setupStarButton()
        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener {
            repo.deletePlant(plant)
            dismiss()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            dismiss()
        }
    }

    private fun setupComponent() {
        val plantImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(plant.imageUrl)).into(plantImage)

        findViewById<TextView>(R.id.popup_plant_name).text = plant.name
        findViewById<TextView>(R.id.popup_description_content).text = plant.description
        findViewById<TextView>(R.id.popup_growing_content).text = plant.growing;
        findViewById<TextView>(R.id.popup_watering_content).text = plant.watering;
    }
}