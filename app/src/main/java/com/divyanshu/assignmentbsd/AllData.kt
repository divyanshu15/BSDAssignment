package com.divyanshu.assignmentbsd

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AllData : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var tvlong : TextView
    lateinit var tvlati : TextView
    lateinit var tvAddress: TextView
    lateinit var db: DBHelper
    lateinit var recyclerView: RecyclerView
    lateinit var longitude:ArrayList<String>
    lateinit var latitude:ArrayList<String>
    lateinit var address:ArrayList<String>
    lateinit var btnview:ArrayList<Button>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_data)
        //initiating fusedlocationproviderclient

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        tvlong=findViewById(R.id.tvlong)
        tvlati=findViewById(R.id.tvlati)
        tvAddress=findViewById(R.id.tvAddress)
        recyclerView=findViewById(R.id.RecyclerView)
        db = DBHelper(this, null)
        longitude = ArrayList<String>()
        latitude = ArrayList<String>()
        address = ArrayList<String>()
        btnview = ArrayList<Button>()



        //val btnview = findViewById<Button>(R.id.btnview)


        val adapter = MyAdapter(longitude,latitude,address,btnview)
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        displaydata()
        getCurrentLocation()
    }
    private fun displaydata() {
        val cursor: Cursor? = db.getLocation()
        if (cursor?.count == 0) {
            Toast.makeText(this, "No entry exists", Toast.LENGTH_SHORT).show()
            return
        } else {
            while (cursor?.moveToNext() == true) {
                longitude.add(cursor.getString(1))
                latitude.add(cursor.getString(2))
                address.add(cursor.getString(3))
            }
        }
    }
    //fun to get current location
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(){
        if (checkPermissions())
        {
            if(isLocationEnabled())
            {
                // will get longitude and latitude here
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location?=task.result
                    if (location==null)
                    {
                        Toast.makeText(this,"Null", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(this,"Successfully got location", Toast.LENGTH_SHORT).show()
                        var long = location.longitude.toString()
                        var lati = location.latitude.toString()
                        var addres = getAddress(location.latitude,location.longitude)
                        tvlati.text=getString(R.string.Latitude,lati)
                        tvlong.text=getString(R.string.Longitude,long)
                        tvAddress.text=getString(R.string.Address,addres)



                        val btnsave = findViewById<AppCompatButton>(R.id.btnSave)
                        btnsave.setOnClickListener{
                            //db = DBHelper(this, null)
                            db.addLocation(locModel(longitude = long, latitude = lati, address = addres))
                            Toast.makeText(this, " added to database", Toast.LENGTH_LONG).show()



                        }


                    }

                }
            }
            else
            {
                Toast.makeText(this,"Turn on Location", Toast.LENGTH_SHORT).show()
                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else
        {
            requestPermission()
        }
    }
    // fun to check device location
    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }
    // fun to request permission
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION)
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }
    private fun checkPermissions():Boolean
    {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        return false
    }
    // fun to get the cuurent address using geocoder
    private fun getAddress(lat:Double,long:Double):String{
        var Locality = ""
        var geocoder = Geocoder(this, Locale.getDefault())
        var Adress = geocoder.getFromLocation(lat,long,1)

        Locality = Adress.get(0).getAddressLine(0)
        return Locality
    }
    // function to check the status of permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode== PERMISSION_REQUEST_ACCESS_LOCATION)
        {
            if(grantResults.isNotEmpty()&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(applicationContext,"Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }
            else
            {
                Toast.makeText(applicationContext,"Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}