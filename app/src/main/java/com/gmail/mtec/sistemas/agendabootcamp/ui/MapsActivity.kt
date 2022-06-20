package com.gmail.mtec.sistemas.agendabootcamp.ui

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.gmail.mtec.sistemas.agendabootcamp.R
import com.gmail.mtec.sistemas.agendabootcamp.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

//import com.gmail.mtec.sistemas.agendabootcamp.ui.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocationUpdate: Location




    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 41

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //implementado em aula
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //metodos implementado na aula
        mMap.uiSettings.isZoomControlsEnabled = true // apresentar o botao de zoom no mapa
        mMap.setOnMarkerClickListener(this)//clicar no map e da opcao de navegar com o pino

        //depois que o mapa for montado solicitar permissao de localizacao do usuario
        requestPermissionUseMapAndSetupLocationUser()
    }

    //metodo implemtado na classe principal -> GoogleMap.OnMarkerClickListener
    override fun onMarkerClick(p0: Marker) = false


    private fun requestPermissionUseMapAndSetupLocationUser() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
            return
        }


        mMap.isMyLocationEnabled = true

        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocationUpdate = location

                val currentLatLong = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))

                placeMarkerOnMap(currentLatLong)

                recursosGeraisMap()
            }
        }

    }

    private fun recursosGeraisMap() {
        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN //alterar tipo de terreno
    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOption = MarkerOptions().position(location)

        //usando para pegar a localização real
        val titleAddress = getCurrentAdress(location)

        markerOption.title(titleAddress)
        mMap.addMarker(markerOption)

    }


    //essa biblioteca GEOCODE já existe no google maps, e trás todos esses atributos já configurado nela
    // PARA PEGAR O ENDEREÇO DO USUARIO
    private fun getCurrentAdress(latLng: LatLng): String{
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1)// é usado 1, porque tras varios resultados proximo, e com 1 ele tras apenas o primeiro da lista
        val address = addresses[0].getAddressLine(0)
        val city = addresses[0].locality
        val state = addresses[0].adminArea
        val country = addresses[0].countryName
        val postalCode = addresses[0].postalCode

        return address
    }





}