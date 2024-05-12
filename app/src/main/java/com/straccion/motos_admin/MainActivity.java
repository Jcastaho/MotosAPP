package com.straccion.motos_admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.straccion.motos_admin.databinding.ActivityMainBinding;
import com.straccion.motos_admin.ui.inicio.HomeFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    SharedPreferences sharedPreferences;
    String email="";
    String password="";
    String correo="";
    String contra="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        //prueba();
        sharedPreferences = this.getSharedPreferences("ingreso" , Context.MODE_PRIVATE);
        //login();
        ingresar();
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void ingresar() {
        sharedPreferences = this.getSharedPreferences("ingreso", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("correo", "error").equals("error")) {
            login();
        } else {
            createUser();
        }
    }
    private void login() {
        String usuario = sharedPreferences.getString("correo", "error");
        String contraseña = sharedPreferences.getString("contra", "error");

        if (!usuario.equals("error")) {
            mAuth.signInWithEmailAndPassword(usuario, contraseña).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Inicio de sesión exitoso
                    iniciar();
                } else {
                    // Manejar errores de inicio de sesión aquí
                    // Por ejemplo, podrías mostrar un mensaje al usuario
                }
            });
        } else {
            // Si no hay usuario almacenado, inicia el flujo para crear uno nuevo
        }
    }

    private void createUser(){
        int length = 10;
        String randomString = generarLetrayNumeroRandom(length);
        if(nombreEquipo() == null){
            email = randomString + "@gmail.com";
            password = "654123";
        }else {
            email = nombreMarca() + nombreEquipo() + randomString + "@gmail.com";
            password = "654123";
        }
        correo = email;
        contra = password;
        mAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", correo);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("correo", correo);
                    editor.putString("contra", contra);
                    editor.commit();
                    mFirestore.collection("Usuarios").document(id).set(map);
                }else{
                }
                ingresar();
            }
        });
    }
    public void iniciar(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        ActionBar actionBar = ((AppCompatActivity) this).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_mimotoideal, R.id.nav_estadisticaFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    public static String nombreEquipo() {
        return Build.MODEL;
    }
    public static String nombreMarca() {
        return Build.MANUFACTURER;
    }
    public static String generarLetrayNumeroRandom(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }

        return randomString.toString();
    }
}