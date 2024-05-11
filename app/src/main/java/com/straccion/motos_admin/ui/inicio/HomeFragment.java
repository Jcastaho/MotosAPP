package com.straccion.motos_admin.ui.inicio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.adapters.PostsAdapters;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.providers.PostProvider;
import com.straccion.motos_admin.ui.addmotos.GalleryFragment;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;

public class HomeFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    View mview;
    RecyclerView mRecyclerView;
    PostProvider mpostProvider;
    PostsAdapters mPostsAdapters;
    LinearLayout lnlProgressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    SharedPreferences sharedPreferences;
    Button botonBuscar;
    EditText edtBuscar;
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
        botonBuscar = mview.findViewById(R.id.botonBuscar);
        edtBuscar = mview.findViewById(R.id.edtBuscar);

        lnlProgressBar.setVisibility(View.VISIBLE);
        mpostProvider = new PostProvider();
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        //prueba();
        sharedPreferences = getContext().getSharedPreferences("ingreso", Context.MODE_PRIVATE);
        if (login() != 1) {

        }
        deleteCache();
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            numberOfColumns = 2;
        } else {
            numberOfColumns = 4;
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //prueba();

        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
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
                } else {
                    String valorBuscar = edtBuscar.getText().toString();
                    if (!valorBuscar.isEmpty()) {
                        int tiempoMostrandoProgressBar = 1500;
                        Query query2 = mpostProvider.getAllConsulta2(valorBuscar.toUpperCase());

                        query2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (!querySnapshot.isEmpty()) {
                                        FirestoreRecyclerOptions<PostAuteco> options2 = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(query2, PostAuteco.class).build();
                                        NavController navController2 = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

                                        mPostsAdapters = new PostsAdapters(options2, getContext(), navController2, 0);
                                        mRecyclerView.setAdapter(mPostsAdapters);
                                        mPostsAdapters.startListening();

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                lnlProgressBar.setVisibility(View.GONE);

                                            }
                                        }, tiempoMostrandoProgressBar);
                                    } else {
                                        Query query3 = mpostProvider.getAllConsulta(valorBuscar.toUpperCase());
                                        FirestoreRecyclerOptions<PostAuteco> options3 = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(query3, PostAuteco.class).build();
                                        NavController navController3 = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

                                        mPostsAdapters = new PostsAdapters(options3, getContext(), navController3, 0);
                                        mRecyclerView.setAdapter(mPostsAdapters);
                                        mPostsAdapters.startListening();

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                lnlProgressBar.setVisibility(View.GONE);

                                            }
                                        }, tiempoMostrandoProgressBar);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorBuscar = edtBuscar.getText().toString();
                if (!valorBuscar.isEmpty()) {
                    int tiempoMostrandoProgressBar = 1500;
                    Query query2 = mpostProvider.getAllConsulta2(valorBuscar.toUpperCase());

                    query2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (!querySnapshot.isEmpty()) {
                                    FirestoreRecyclerOptions<PostAuteco> options2 = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(query2, PostAuteco.class).build();
                                    NavController navController2 = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

                                    mPostsAdapters = new PostsAdapters(options2, getContext(), navController2, 0);
                                    mRecyclerView.setAdapter(mPostsAdapters);
                                    mPostsAdapters.startListening();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            lnlProgressBar.setVisibility(View.GONE);

                                        }
                                    }, tiempoMostrandoProgressBar);
                                } else {
                                    Query query3 = mpostProvider.getAllConsulta(valorBuscar.toUpperCase());
                                    FirestoreRecyclerOptions<PostAuteco> options2 = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(query3, PostAuteco.class).build();
                                    NavController navController2 = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

                                    mPostsAdapters = new PostsAdapters(options2, getContext(), navController2, 0);
                                    mRecyclerView.setAdapter(mPostsAdapters);
                                    mPostsAdapters.startListening();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            lnlProgressBar.setVisibility(View.GONE);

                                        }
                                    }, tiempoMostrandoProgressBar);
                                }
                            }
                        }
                    });

                }else {
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
            }
        });
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
                botonBuscar.setVisibility(View.VISIBLE);
                edtBuscar.setVisibility(View.VISIBLE);

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
    public void onPause() {
        super.onPause();
        // Limpiar el contenido del EditText
        edtBuscar.setText("");
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

    private int login() {
        int ingreso = 0;
        String usuario = sharedPreferences.getString("correo", "error");
        String contrasena = sharedPreferences.getString("contra", "error");

        mAuth.signInWithEmailAndPassword(usuario, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                } else {
                }
            }
        });

        return ingreso;
    }
}







