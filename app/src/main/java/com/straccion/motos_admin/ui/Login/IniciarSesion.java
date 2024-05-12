package com.straccion.motos_admin.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.straccion.motos_admin.MainActivity;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.providers.AuthProvider;

public class IniciarSesion extends AppCompatActivity {

    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mbtnIniciar;
    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciar_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        mTextInputEmail = findViewById(R.id.txtInputEmail);
        mTextInputPassword = findViewById(R.id.txtInputPassword);
        mbtnIniciar = findViewById(R.id.btnIniciar);
        mAuthProvider = new AuthProvider();
        mbtnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuthProvider.getUserSesion() != null){
            Intent intent = new Intent(IniciarSesion.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void login(){
        String Email = mTextInputEmail.getText().toString();
        String Password = mTextInputPassword.getText().toString();

        mAuthProvider.Login(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(IniciarSesion.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(IniciarSesion.this, "El correo o contraseña no son correctas", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}