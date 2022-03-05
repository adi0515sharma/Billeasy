package com.sample.billeasy.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.sample.billeasy.R
import com.sample.billeasy.database.MovieDb


class BilleasyHomePage : AppCompatActivity() {

    lateinit var bhpViewmodel: BHPViewmodel
    var movieDb : MovieDb? = null
    lateinit var recyclerView : RecyclerView
    lateinit var adapter: BilleasyHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        recyclerView = findViewById(R.id.recycle_view)
        adapter = BilleasyHomeAdapter(arrayListOf(), this)
        recyclerView.adapter = adapter
    }

    fun fetch_movie_from_server(){
        bhpViewmodel.fetch_all_movie_from_server()
    }

    fun fetch_movie_from_roomdb(){
        bhpViewmodel.fetch_all_movie_list_from_roomdb()
        bhpViewmodel.get_all_movie_list().observe(
            this, Observer {

            }
        )
    }

    fun checkPermission() : Boolean{
        var write = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        var read = (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        if(!write){
            ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.WRITE_EXTERNAL_STORAGE ), 1);
        }
        else if(!read){
            ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.READ_EXTERNAL_STORAGE ), 2);
        }
        else{
            movieDb = MovieDb.getDatabase(this)
            bhpViewmodel = ViewModelProviders.of(this).get(BHPViewmodel::class.java)
            bhpViewmodel.setUp(movieDb!!.getMoviesDao())

            fetch_movie_from_roomdb()

        }
        return true
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission()
            } else {
                Toast.makeText(this, "Please allowed this to add movie to your database", Toast.LENGTH_LONG)
            }
        }
        if(requestCode == 2){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission()
            } else {
                Toast.makeText(this, "Please allowed this to read movie to your database", Toast.LENGTH_LONG)
            }
        }
    }
}