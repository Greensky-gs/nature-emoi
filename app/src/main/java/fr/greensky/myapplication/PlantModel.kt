package fr.greensky.myapplication

class PlantModel(
    val id: String = "plant0",
    val name: String = "Plante",
    val description: String = "Description",
    val imageUrl: String = "https://graven.yt/plante.jpg",
    var liked: Boolean = false,
    var growing: String = "Lente",
    var watering: String = "Faible"
)