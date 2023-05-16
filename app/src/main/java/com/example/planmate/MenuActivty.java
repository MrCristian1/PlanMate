package com.example.planmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;


public class MenuActivty extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activty);

        mAuth = FirebaseAuth.getInstance();

    }

    //metodo boton ver tareas pendientes
    public void VerTareas(View view){
        Intent vertareas = new Intent(this, MainActivity.class);
        startActivity(vertareas);
    }

    //metodo boton para crear tarea
    public void CrearTarea(View view){
        Intent creartarea = new Intent(this, CrearTareaActivity.class);
        startActivity(creartarea);
    }


    //metodo boton para asignar materia
    public void AsignarMateria(View view){
        Intent asignarmateria = new Intent(this, AsignarMateriaActivity.class);
        startActivity(asignarmateria);
    }



    public void cerrarSesion(View view) {
        mAuth.signOut();
        // Se redirige a una actividad de inicio de sesión.
        // Por ejemplo:
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}