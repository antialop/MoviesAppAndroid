package com.example.moviesapp.ui.search.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.ui.domain.SearchMovieItem
import com.squareup.picasso.Picasso

class SearchMovieViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemMovieBinding.bind(view)

    fun bind(searchMovieItem: SearchMovieItem,onItemSelected: (String) -> Unit) {
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500"+searchMovieItem.poster)
            .into(binding.ivMovie)
        binding.root.setOnClickListener {
            //Cada vez que se pulse la lista le pasamos el id
            onItemSelected(searchMovieItem.id)
        }
    }

}