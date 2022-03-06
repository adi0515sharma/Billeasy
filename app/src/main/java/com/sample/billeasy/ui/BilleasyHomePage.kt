package com.sample.billeasy.ui

import android.Manifest
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sample.billeasy.R
import com.sample.billeasy.database.MovieDb
import com.sample.billeasy.database.Movies.Movie
import com.sample.billeasy.utils.NetworkConnectivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class BilleasyHomePage : AppCompatActivity() {

    lateinit var bhpViewmodel: BHPViewmodel
    var movieDb : MovieDb? = null
    lateinit var recyclerView : RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var download : FloatingActionButton
    lateinit var adapter: BilleasyHomeAdapter
    lateinit var no_found: TextView
    private var mNetworkReceiver: BroadcastReceiver? = null

    private var i_flag : Boolean = true
    private var v_count : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        movieDb = MovieDb.getDatabase(this)

        bhpViewmodel = ViewModelProviders.of(this).get(BHPViewmodel::class.java)
        bhpViewmodel.setUp(movieDb!!.getMoviesDao())

        mNetworkReceiver = NetworkConnectivity()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }

        recyclerView = findViewById(R.id.recycle_view)
        progressBar = findViewById(R.id.progress)
        download = findViewById<FloatingActionButton>(R.id.download)

        no_found = findViewById(R.id.no_found)
        download.setOnClickListener {
            fetch_movie_from_server()
        }
        adapter = BilleasyHomeAdapter(arrayListOf(), this)
        recyclerView.adapter = adapter


        fetch_movie_from_roomdb()

    }

    fun fetch_movie_from_server(){
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        no_found.visibility = View.GONE
        download.isEnabled = false
        v_count = 2
        bhpViewmodel.fetch_all_movie_from_server()
    }

    fun fetch_movie_from_roomdb(){
        bhpViewmodel.fetch_all_movie_list_from_roomdb()

        bhpViewmodel.get_all_movie_list().observe(
            this, Observer {

                progressBar.visibility = View.GONE

                if(it.size==0){

                    recyclerView.visibility = View.GONE

                    if(v_count>0){
                        progressBar.visibility = View.VISIBLE
                        no_found.visibility = View.GONE
                        v_count-=1
                    }
                    else{
                        no_found.visibility = View.VISIBLE
                    }

                }
                else{
                    recyclerView.visibility = View.VISIBLE
                    no_found.visibility = View.GONE

                    adapter.list_of_chat = it as ArrayList<Movie>
                    adapter.notifyDataSetChanged()
                }
                download.isEnabled = true
            }
        )

    }

    @Subscribe
    fun onCartItemAdd(network: String) {
        if(network.equals("Connected")){
            //checkPermission()

            if(i_flag){
                fetch_movie_from_server()
                i_flag = false
            }
            download.visibility = View.VISIBLE
        }
        else{
            download.visibility = View.INVISIBLE
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(mNetworkReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        EventBus.getDefault().unregister(this)
    }
}