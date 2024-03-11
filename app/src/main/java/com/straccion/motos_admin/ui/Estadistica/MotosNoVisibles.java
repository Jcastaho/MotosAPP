package com.straccion.motos_admin.ui.Estadistica;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.adapters.PostsAdapters;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.providers.PostProvider;


public class MotosNoVisibles extends Fragment {


    View mview;
    RecyclerView mRecyclerView;
    PostProvider mpostProvider;
    PostsAdapters mPostsAdapters;
    LinearLayout lnlProgressBar;

    int numberOfColumns = 0;

    public MotosNoVisibles() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_motos_no_visibles, container, false);

        mRecyclerView = mview.findViewById(R.id.reciclerViewNoVisibles);
        lnlProgressBar = mview.findViewById(R.id.lnlProgressBarNoVisible);
        lnlProgressBar.setVisibility(View.VISIBLE);

        mpostProvider = new PostProvider();

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            numberOfColumns = 2;
        } else {
            numberOfColumns = 4;
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        mRecyclerView.setLayoutManager(gridLayoutManager);


        return mview;
    }


    @Override
    public void onStart() {
        super.onStart();
        int tiempoMostrandoProgressBar = 1500;
        Query query = mpostProvider.getAll3();

        FirestoreRecyclerOptions<PostAuteco> options = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(query, PostAuteco.class).build();

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

        mPostsAdapters = new PostsAdapters(options, getContext(), navController, 1);
        mRecyclerView.setAdapter(mPostsAdapters);
        mPostsAdapters.startListening();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lnlProgressBar.setVisibility(View.GONE);

            }
        }, tiempoMostrandoProgressBar);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPostsAdapters.stopListening();
        lnlProgressBar.setVisibility(View.GONE);
    }
}