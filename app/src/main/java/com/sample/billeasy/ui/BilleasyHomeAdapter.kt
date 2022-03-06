package com.sample.billeasy.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.billeasy.R
import com.sample.billeasy.database.Movies.Movie
import com.sample.billeasy.utils.Urls

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
        movieClass.pop.text = list_of_chat.get(position).popularity
        movieClass.release.text = list_of_chat.get(position).release_date
        Glide
            .with(context)
            .load("${Urls.IMG_URL}${list_of_chat.get(position).backdrop_path}")
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_broken_image_24)
            .into(movieClass.poster);
    }

    override fun getItemCount(): Int {
        return list_of_chat.size
    }

    class MovieClass constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var title : TextView
        lateinit var desc : TextView
        lateinit var pop : TextView

        lateinit var release : TextView

        lateinit var poster : ImageView
        lateinit var date : TextView

        init {
            title = itemView.findViewById(R.id.title)
            desc = itemView.findViewById(R.id.desc)
            poster = itemView.findViewById(R.id.poster)
            pop = itemView.findViewById(R.id.pop)
            release = itemView.findViewById(R.id.release)
        }
    }
}