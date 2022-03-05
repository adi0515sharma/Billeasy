package com.sample.billeasy.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.billeasy.R
import com.sample.billeasy.database.Movies.Movie

class BilleasyHomeAdapter constructor(var list_of_chat : ArrayList<Movie>, var context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        var view_holder : RecyclerView.ViewHolder = MovieClass(view)
        return view_holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var movieClass : MovieClass = holder as MovieClass
        movieClass.title.text = list_of_chat.get(position).title
        movieClass.desc.text = list_of_chat.get(position).overview

    }

    override fun getItemCount(): Int {
        return list_of_chat.size
    }

    class MovieClass constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var title : TextView
        lateinit var desc : TextView
        lateinit var date : TextView

        init {
            title = itemView.findViewById(R.id.title)
            desc = itemView.findViewById(R.id.desc)
        }
    }
}