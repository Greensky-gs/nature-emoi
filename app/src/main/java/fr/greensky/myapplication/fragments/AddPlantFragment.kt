package fr.greensky.myapplication.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.greensky.myapplication.MainActivity
import fr.greensky.myapplication.PlantModel
import fr.greensky.myapplication.PlantRepository
import fr.greensky.myapplication.R
import java.util.UUID


class AddPlantFragment(private val context: MainActivity) : Fragment() {
    private var photoFile: Uri? = null
    private var uploadedImg: ImageView? = null
    private var clicked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_plant, container, false)
        context.findViewById<TextView>(R.id.page_title).text = "Ajout d'une plante"

        uploadedImg = view.findViewById(R.id.view_plant_img)

        val pickupImage = view.findViewById<Button>(R.id.load_img_button)
        pickupImage.setOnClickListener{ pickupImage() }

        val confirmButton = view.findViewById<Button>(R.id.register_plant_confirm)
        confirmButton.setOnClickListener { confirmation(view) }

        return view
    }
    private fun confirmation(view: View) {
        if (clicked) return
        clicked = true

        val plantDescription = view.findViewById<EditText>(R.id.register_plant_description).text.toString()
        val plantName = view.findViewById<EditText>(R.id.register_plant_name).text.toString()
        val plantGrowing = view.findViewById<Spinner>(R.id.register_plant_growing).selectedItem.toString()
        val plantWatering = view.findViewById<Spinner>(R.id.register_plant_watering).selectedItem.toString()

        val testValues: Array<String?> = arrayOf(null, "")
        if (arrayOf(plantName, plantDescription, plantGrowing, plantWatering).any({ it in testValues })) {
            clicked = false
            return
        }

        val repo = PlantRepository()
        repo.uploadImage(photoFile!!) { uri ->
            val plant = PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDescription,
                uri.toString(),
                false,
                plantGrowing,
                plantWatering
            )

            repo.insertPlant(plant)
            context.loadFragment(HomeFragment(context))
            context.findViewById<BottomNavigationView>(R.id.navigation_bar).selectedItemId = R.id.home_page
        }


    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Select picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 47 && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) return;

            val image = data.data;
            photoFile = image
            uploadedImg?.setImageURI(image)
        }
    }
}