package com.example.planmate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.widget.Button;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class DetallesTareaActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private TextView etTareaTitulo;
    private TextView etTareaDetalles;
    private TextView etNombreProfesor;
    private TextView etFechaEntrega;
    private TextView etCategoria;
    private TextView etMateria;
    private Spinner spinnerCategoria;
    private Spinner spinnerMateria;
    private Button btnActualizarTarea;
    private Tarea tarea;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_tarea);
        setTitle("Detalles Tarea");
        firestore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        etTareaTitulo = findViewById(R.id.tv_titulo_tarea);
        etTareaDetalles = findViewById(R.id.tv_detalles_tarea);
        etNombreProfesor = findViewById(R.id.tv_nombre_profesor);
        etFechaEntrega = findViewById(R.id.tv_fecha);
        etCategoria = findViewById(R.id.tv_categoria);
        etMateria = findViewById(R.id.tv_materia);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("tarea")) {
            Tarea tareaSeleccionada = (Tarea) getIntent().getSerializableExtra("tarea");
            String tareaId = tareaSeleccionada.getId();

            DocumentReference tareaRef = firestore.collection("tareas").document(tareaId);

            // se obtienen los datos del documento de la tarea
            tareaRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    tarea = documentSnapshot.toObject(Tarea.class);

                    //se utilizan los datos de la tarea para mostrarlos en las vistas correspondientes
                    etTareaTitulo.setText(tarea.getTitulo());
                    etTareaDetalles.setText(tarea.getDetalles_tarea());
                    etCategoria.setText(tarea.getCategoria());
                    etNombreProfesor.setText(tarea.getNombre_profesor());
                    etMateria.setText(tarea.getMateria());
                    etFechaEntrega.setText(tarea.getFecha_entrega());


                } else {
                    Toast.makeText(DetallesTareaActivity.this, "La tarea no existe en la base de datos", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(DetallesTareaActivity.this, "Error al obtener los datos de la tarea", Toast.LENGTH_SHORT).show();
            });
        }



        Button btnTerminarTarea = findViewById(R.id.btn_terminar);
        btnTerminarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarTarea();
            }
        });

        Button editarTareaButton = findViewById(R.id.btnActualizarTarea);
        editarTareaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String tareaId = obtenerTareaId();


                // se obtienen  los datos de la tarea
                String tareaId = obtenerTareaId(); // Método para obtener el ID de la tarea actual
                TextView titulo = obtenerTituloTarea(); // Método para obtener el título de la tarea actual
                TextView detalles = obtenerDetallesTarea(); // Método para obtener los detalles de la tarea actual
                TextView profesor = obtenerProfesorTarea(); // Método para obtener el nombre del profesor de la tarea actual
                TextView categoria = obtenerCategoriaTarea(); // Método para obtener la categoría de la tarea actual
                TextView materia = obtenerMateriaTarea(); // Método para obtener la materia de la tarea actual
                TextView fechaEntrega = obtenerFechaEntregaTarea(); // Método para obtener la fecha de entrega de la tarea actual


                Intent intent = new Intent(DetallesTareaActivity.this, CrearTareaActivity.class);
                intent.putExtra("tareaId", tareaId); // Pasa el ID de la tarea a la actividad CrearTareaActivity
                intent.putExtra("editarTarea", true); // Indica que se está editando la tarea
                startActivity(intent);

                // se crea un Intent para iniciar la actividad CrearTareaActivity
                Intent editarTareaIntent = new Intent(DetallesTareaActivity.this, CrearTareaActivity.class);

                // se agregan los datos de la tarea como extras en el Intent
                editarTareaIntent.putExtra("tareaId", tareaId);
                editarTareaIntent.putExtra("titulo", titulo.getText().toString());
                editarTareaIntent.putExtra("detalles", detalles.getText().toString());
                editarTareaIntent.putExtra("profesor", profesor.getText().toString());
                editarTareaIntent.putExtra("categoria", categoria.getText().toString());
                editarTareaIntent.putExtra("materia", materia.getText().toString());
                editarTareaIntent.putExtra("fechaEntrega", fechaEntrega.getText().toString());


                // Iniciar la actividad CrearTareaActivity
                startActivity(editarTareaIntent);
            }
        });
    }




    private TextView obtenerTituloTarea() {
        // El título de la tarea está almacenado en una variable llamada "etTareaTitulo"
        return etTareaTitulo;
    }

    private TextView obtenerDetallesTarea() {
        return etTareaDetalles;
    }

    private TextView obtenerFechaEntregaTarea() {
        return etFechaEntrega;
    }

    private TextView obtenerCategoriaTarea() {
        return etCategoria;
    }

    private TextView obtenerMateriaTarea() {
        return etMateria;
    }

    private TextView obtenerProfesorTarea() {
        return etNombreProfesor;
    }

    private String obtenerTareaId() {
        // se obtiene el ID de la tarea actual desde Firebase Firestore
        return  getIntent().getStringExtra("tareaId");
    }


    public void cerrarSesion(View view) {
        mAuth.signOut();
        // Se redirige a una actividad de inicio de sesión.
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void eliminarTarea() {
        String tareaTitulo = etTareaTitulo.getText().toString();


        // se realiza una consulta en la colección "tareas" para buscar la tarea por su título
        firestore.collection("tareas")
                .whereEqualTo("titulo", tareaTitulo)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // se obtiene la referencia del documento de la tarea encontrada
                        DocumentReference tareaRef = queryDocumentSnapshots.getDocuments().get(0).getReference();

                        // se elimina la tarea
                        tareaRef.delete()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(DetallesTareaActivity.this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(DetallesTareaActivity.this, "Error al eliminar la tarea", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(DetallesTareaActivity.this, "La tarea no existe en la base de datos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DetallesTareaActivity.this, "Error al buscar la tarea", Toast.LENGTH_SHORT).show();
                });
    }

    public void AtrasDetallesTarea(View view) {
        Intent atrasDetalles = new Intent(this, MainActivity.class);
        startActivity(atrasDetalles);
    }



}