package fr.greensky.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.greensky.myapplication.fragments.AddPlantFragment
import fr.greensky.myapplication.fragments.CollectionFragment
import fr.greensky.myapplication.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this))

        val navigation = findViewById<BottomNavigationView>(R.id.navigation_bar)
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.home_page -> {
                    loadFragment(HomeFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_plant_page -> {
                    loadFragment(AddPlantFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    public fun loadFragment(fragment: Fragment) {
        val repo = PlantRepository()
        repo.updateData {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)

            transaction.commit()
        }
    }
}