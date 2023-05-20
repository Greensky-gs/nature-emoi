package fr.greensky.myapplication

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.greensky.myapplication.PlantRepository.Singleton.databaseRef
import fr.greensky.myapplication.PlantRepository.Singleton.plantList
import fr.greensky.myapplication.PlantRepository.Singleton.storageRef
import java.util.Properties
import java.util.UUID

class PlantRepository {
    object Singleton {
        private val BUCKET_URL: String = Properties().getProperty("bucketURL")
        val database = FirebaseDatabase.getInstance(Properties().getProperty("databaseURL"))

        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)
        val databaseRef = database.getReference("plants")
        val plantList = arrayListOf<PlantModel>()
    }

    fun updateData(callback: () -> Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                plantList.clear()
                for (sn in snapshot.children) {
                    val plant = sn.getValue(PlantModel::class.java)

                    if (plant != null) {
                        plantList.add(plant)
                    }
                }

                callback()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun deletePlant(plant: PlantModel) {
        val parts = plant.imageUrl.substringAfterLast("/", "")
        val ref = parts.substringBeforeLast(".jpg", "") + ".jpg"

        if (!storageRef.child(ref).equals(null)) storageRef.child(ref).delete()
        databaseRef.child(plant.id).removeValue()
    }
    fun updatePlant(plant: PlantModel) {
        databaseRef.child(plant.id).setValue(plant)
    }
    fun insertPlant(plant: PlantModel) {
        databaseRef.child(plant.id).setValue(plant)
    }
    fun uploadImage(file: Uri, callback: (uri: Uri) -> Unit) {
        if (file != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageRef.child(fileName)
            val uploadTask = ref.putFile(file)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }

                return@Continuation ref.downloadUrl
            }).addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    callback(downloadUri)
                }
            }
        }

    }
}