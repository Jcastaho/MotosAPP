package com.straccion.motos_admin.ui.Login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.straccion.motos_admin.R;

public class InicioSesion extends Fragment {

    FirebaseAuth mAuth;
    View mview;

    public InicioSesion() {
        // Required empty public constructor
    }

    public static InicioSesion newInstance(String param1, String param2) {
        InicioSesion fragment = new InicioSesion();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);

        mAuth = FirebaseAuth.getInstance();
        return mview;
    }

}