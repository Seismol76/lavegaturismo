package com.example.lavegaturismo

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val spotDao = db.spotDao()
    private val eventDao = db.eventDao()
    private val userDao = db.userDao()

    val touristSpots = mutableStateOf(listOf<SpotEntity>())
    val culturalEvents = mutableStateOf(listOf<EventEntity>())
    val favoriteSpots = mutableStateOf(listOf<SpotEntity>())
    val favoriteEvents = mutableStateOf(listOf<EventEntity>())
    val isDataLoaded = mutableStateOf(false)

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            Log.d("AppViewModel", "Iniciando carga de datos")
            try {
                // Insertar usuario por defecto
                if (userDao.getUser("William", "1234") == null) {
                    userDao.insertUser(UserEntity("William", "1234"))
                    Log.d("AppViewModel", "Usuario por defecto insertado")
                }

                // Puntos turísticos
                if (spotDao.getAllSpots().isEmpty()) {
                    val initialSpots = listOf(
                        SpotEntity(
                            name = "Catedral de La Vega",
                            description = "Una impresionante catedral de estilo gótico en el corazón de La Vega.",
                            imageResId = R.drawable.catedral_la_vega,
                            history = "Construida en 1916, es un símbolo de la fe dominicana.",
                            creator = "Arquitecto desconocido",
                            events = "Misas y festivales religiosos",
                            funFact = "Su campana es una de las más antiguas del país.",
                            latitude = 19.2236,
                            longitude = -70.5296
                        ),
                        SpotEntity(
                            name = "Parque Duarte",
                            description = "Un parque histórico en el centro de La Vega, ideal para relajarse.",
                            imageResId = R.drawable.parque_duarte,
                            history = "Nombrado en honor a Juan Pablo Duarte, fundado en el siglo XIX.",
                            creator = "Municipio de La Vega",
                            events = "Conciertos y ferias",
                            funFact = "Tiene una estatua centenaria de Duarte.",
                            latitude = 19.2218,
                            longitude = -70.5302
                        ),
                        SpotEntity(
                            name = "Museo del Carnaval",
                            description = "Exhibe la historia del famoso Carnaval de La Vega.",
                            imageResId = R.drawable.museo_carnaval,
                            history = "Inaugurado en 2001 para preservar la cultura carnavalera.",
                            creator = "Asociación Carnavalera",
                            events = "Exposiciones temporales",
                            funFact = "Tiene máscaras de más de 50 años.",
                            latitude = 19.2220,
                            longitude = -70.5290
                        ),
                        SpotEntity(
                            name = "Cerro de La Vega",
                            description = "Un mirador con vistas panorámicas de la ciudad.",
                            imageResId = R.drawable.cerro_la_vega,
                            history = "Lugar sagrado para los taínos antes de la colonización.",
                            creator = "Naturaleza",
                            events = "Caminatas y eventos al aire libre",
                            funFact = "Se dice que esconde cuevas taínas.",
                            latitude = 19.2300,
                            longitude = -70.5200
                        ),
                        SpotEntity(
                            name = "Río Camú",
                            description = "Río ideal para actividades al aire libre y relajación.",
                            imageResId = R.drawable.rio_camu,
                            history = "Usado históricamente para agricultura y transporte.",
                            creator = "Naturaleza",
                            events = "Picnics y festivales acuáticos",
                            funFact = "Sus aguas son cristalinas en verano.",
                            latitude = 19.2400,
                            longitude = -70.5400
                        )
                    )
                    spotDao.insertSpots(initialSpots)
                    Log.d("AppViewModel", "Spots insertados")
                }

                // Eventos culturales
                if (eventDao.getAllEvents().isEmpty()) {
                    val initialEvents = listOf(
                        EventEntity(
                            name = "Carnaval de La Vega",
                            month = "Febrero",
                            day = 15,
                            description = "El carnaval más famoso de RD con desfiles coloridos.",
                            history = "Inició en el siglo XVI como celebración religiosa."
                        ),
                        EventEntity(
                            name = "Festival de la Virgen de las Mercedes",
                            month = "Septiembre",
                            day = 24,
                            description = "Celebración religiosa con procesiones.",
                            history = "Honra a la patrona de La Vega desde el siglo XVII."
                        ),
                        EventEntity(
                            name = "Feria Agropecuaria",
                            month = "Abril",
                            day = 10,
                            description = "Exposición de productos agrícolas locales.",
                            history = "Creada en los años 80 para promover la agricultura."
                        ),
                        EventEntity(
                            name = "Festival de la Cosecha",
                            month = "Junio",
                            day = 5,
                            description = "Celebración de la temporada de cosecha.",
                            history = "Tradición campesina desde el siglo XX."
                        ),
                        EventEntity(
                            name = "Noche de Velas",
                            month = "Diciembre",
                            day = 20,
                            description = "Evento navideño con velas y música.",
                            history = "Inició en 2010 como evento comunitario."
                        )
                    )
                    eventDao.insertEvents(initialEvents)
                    Log.d("AppViewModel", "Eventos insertados")
                }

                touristSpots.value = spotDao.getAllSpots()
                culturalEvents.value = eventDao.getAllEvents()
                favoriteSpots.value = spotDao.getFavoriteSpots()
                favoriteEvents.value = eventDao.getFavoriteEvents()
                isDataLoaded.value = true
                Log.d("AppViewModel", "Datos cargados: ${touristSpots.value.size} spots, ${culturalEvents.value.size} eventos")
            } catch (e: Exception) {
                Log.e("AppViewModel", "Error al cargar datos", e)
            }
        }
    }

    fun handleAuth(
        username: String,
        password: String,
        isRegistering: Boolean,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (isRegistering) {
                    if (userDao.getUser(username, password) == null) {
                        userDao.insertUser(UserEntity(username, password))
                        onSuccess()
                    } else {
                        onError("El usuario ya existe")
                    }
                } else {
                    val user = userDao.getUser(username, password)
                    if (user != null) {
                        onSuccess()
                    } else {
                        onError("Usuario o contraseña incorrectos")
                    }
                }
            } catch (e: Exception) {
                onError("Error: ${e.message}")
            }
        }
    }

    fun toggleSpotFavorite(spot: SpotEntity) {
        viewModelScope.launch {
            val newFavoriteState = !spot.isFavorite
            spotDao.updateFavorite(spot.id, newFavoriteState)
            touristSpots.value = spotDao.getAllSpots()
            favoriteSpots.value = spotDao.getFavoriteSpots()
        }
    }

    fun toggleEventFavorite(event: EventEntity) {
        viewModelScope.launch {
            val newFavoriteState = !event.isFavorite
            eventDao.updateFavorite(event.id, newFavoriteState)
            culturalEvents.value = eventDao.getAllEvents()
            favoriteEvents.value = eventDao.getFavoriteEvents()
        }
    }
}
