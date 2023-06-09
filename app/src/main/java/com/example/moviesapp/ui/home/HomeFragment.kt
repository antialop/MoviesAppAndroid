package com.example.moviesapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.ui.domain.MovieItem
import com.example.moviesapp.ui.domain.TopRatedMovieItem
import com.example.moviesapp.ui.home.recyclerview.PopularMoviesAdapter
import com.example.moviesapp.ui.home.recyclerview.TopRatedMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PopularMoviesAdapter
    private lateinit var adapterRated: TopRatedMoviesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewModel.popularMovie.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
        viewModel.upcomingMovie.observe(viewLifecycleOwner, Observer {
            adapterRated.updateList(it)
        })

        adapter = PopularMoviesAdapter(onItemSelected =  { navigateToDetail(it) }, addWatchlistMovie ={addWatchlistPopularMovieToDataBase(it)}, removeWatchlisMovie = {removeWatchlistMovieToDataBase(it)})
        adapterRated = TopRatedMoviesAdapter(onItemSelected =  { navigateToDetail(it) }, addWatchlistMovie ={addWatchlistRatedMovieToDataBase(it)}, removeWatchlistMovie = {removeWatchlistMovieToDataBase(it)})
        binding.rvPopularMovie.setHasFixedSize(true)
        binding.rvTopRatedMovie.setHasFixedSize(true)
        binding.rvPopularMovie.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        binding.rvTopRatedMovie.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        binding.rvPopularMovie.adapter = adapter
        binding.rvTopRatedMovie.adapter = adapterRated
        viewModel.allPopularMovies()
        viewModel.allUpcomingMovies()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun navigateToDetail(id:String) {
        val myInformacion = Bundle()
        myInformacion.putString("id",id)
        findNavController().navigate(R.id.detailFragment,myInformacion)
    }
    private fun addWatchlistPopularMovieToDataBase(popularMovieItem: MovieItem){
        viewModel.insertWatchlistPopularMovie(popularMovieItem)

    }
    private fun addWatchlistRatedMovieToDataBase(topRatedMovieItem: TopRatedMovieItem){
        viewModel.insertWatchlistRatedMovie(topRatedMovieItem)
    }
    private fun removeWatchlistMovieToDataBase(popularMovie: String){
        viewModel.deleteWatchlistMovie(popularMovie)
    }


}