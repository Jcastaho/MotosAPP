package com.straccion.motos_admin.ui.inicio;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.adapters.PostsAdapters;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.providers.PostProvider;
import com.straccion.motos_admin.ui.addmotos.GalleryFragment;

public class HomeFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    View mview;

    RecyclerView mRecyclerView;
    PostProvider mpostProvider;
    PostsAdapters mPostsAdapters;
    LinearLayout lnlProgressBar;
    MaterialSearchBar mSearchBar;


    int numberOfColumns = 0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public HomeFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reservaFiltro.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mview = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = mview.findViewById(R.id.reciclerViewHome);
        lnlProgressBar = mview.findViewById(R.id.lnlProgressBar);
        MenuItem searchItem = mview.findViewById(R.id.action_search);
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
        Query query = mpostProvider.getAll();

        FirestoreRecyclerOptions<PostAuteco> options = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(query, PostAuteco.class).build();

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

        mPostsAdapters = new PostsAdapters(options, getContext(), navController);
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
    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}