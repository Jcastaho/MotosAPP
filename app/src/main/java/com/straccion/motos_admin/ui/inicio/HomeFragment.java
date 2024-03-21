package com.straccion.motos_admin.ui.inicio;

import android.content.Context;
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

import java.io.File;

public class HomeFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    View mview;

    RecyclerView mRecyclerView;
    PostProvider mpostProvider;
    PostsAdapters mPostsAdapters;
    LinearLayout lnlProgressBar;

    int numberOfColumns = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mview = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = mview.findViewById(R.id.reciclerViewHome);
        lnlProgressBar = mview.findViewById(R.id.lnlProgressBar);
        MenuItem searchItem = mview.findViewById(R.id.action_search);
        lnlProgressBar.setVisibility(View.VISIBLE);

        deleteCache();
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

        mPostsAdapters = new PostsAdapters(options, getContext(), navController, 0);
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
    public void deleteCache() {
        try {
            long cacheSize = getCacheSize(getContext().getCacheDir());
            long maxSizeBytes = 50 * 1024 * 1024; // 120 megabytes

            if (cacheSize > maxSizeBytes) {
                File cacheDir = getContext().getCacheDir();
                if (cacheDir != null && cacheDir.isDirectory()) {
                    deleteDir(cacheDir);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private long getCacheSize(File dir) {
        long size = 0;
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    size += file.length();
                }
            }
        }
        return size;
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        return dir != null && dir.delete();
    }
}