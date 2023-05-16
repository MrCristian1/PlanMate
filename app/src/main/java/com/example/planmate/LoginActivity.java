package com.example.planmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText correo;
    private EditText contrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);

        mAuth = FirebaseAuth.getInstance();

    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    public void iniciarSesion (View view){

            mAuth.signInWithEmailAndPassword(correo.getText().toString(), contrasena.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext() , "Autenticacion correcta", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                    Intent i = new Intent(getApplicationContext(), MenuActivty.class);
                                startActivity(i);


                                //updateUI(user)
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext() , "Authentication failed.", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                        }
                    });



        }






    //metodo boton inicio sesion
    public void InicioSesion(View view){
        Intent iniciosesion = new Intent(this, MenuActivty.class);
        startActivity(iniciosesion);
    }


    //metodo boton registrarse
    public void RegistroSesion(View view){
        Intent registrosesion = new Intent(this, RegistrarActivity.class);
        startActivity(registrosesion);
    }




}