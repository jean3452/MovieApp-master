package com.apimovies.movieapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apimovies.movieapp.Adapters.UpcomingMovieAdapter;
import com.apimovies.movieapp.ApiClient;
import com.apimovies.movieapp.Model.MovieResponse1;
import com.apimovies.movieapp.Model.Upcoming;
import com.apimovies.movieapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingFragment extends Fragment {

    UpcomingMovieAdapter upcomingMovieAdapter;
    RecyclerView recyclerView;
    List<Upcoming> upcomings;
    String apiKey = "YOUR API KEY";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming,container,false);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager( getContext()));

        getMovieData(apiKey);

        return view;
    }

    public void getMovieData(String apiKey){
        Call<MovieResponse1> call = ApiClient.getInstance().getApi().getUpcomingMovies(apiKey);
        call.enqueue(new Callback<MovieResponse1>() {
            @Override
            public void onResponse(Call<MovieResponse1> call, Response<MovieResponse1> response) {
                if (response.isSuccessful() && response.body() != null){
                    upcomings = response.body().getResults();
                    upcomingMovieAdapter = new UpcomingMovieAdapter(getContext(),upcomings);
                    recyclerView.setAdapter(upcomingMovieAdapter);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse1> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
