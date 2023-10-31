package com.lseguel.tareas;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> listaElementos;
    private ArrayAdapter<String> adaptador;
    private EditText campoTexto;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoTexto = findViewById(R.id.editText);
        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnEditar = findViewById(R.id.btnEditar);
        Button btnActualizar = findViewById(R.id.btnActualizar);
        Button btnEliminar = findViewById(R.id.btnEliminar);
        Button btnMostrarTodos = findViewById(R.id.btnMostrarTodos);
        listView = findViewById(R.id.listView);

        listaElementos = new ArrayList<>();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaElementos);
        listView.setAdapter(adaptador);

        btnAgregar.setOnClickListener(v -> {
            String elemento = campoTexto.getText().toString();
            listaElementos.add(elemento);
            adaptador.notifyDataSetChanged();
        });

        btnEditar.setOnClickListener(v -> {
            String elemento = campoTexto.getText().toString();
            int posicion = listaElementos.indexOf(elemento);
            if (posicion != -1) {
                campoTexto.setText(listaElementos.get(posicion));
            } else {
                Toast.makeText(this, "Elemento no encontrado", Toast.LENGTH_SHORT).show();
            }
        });

        btnActualizar.setOnClickListener(v -> {
            String elemento = campoTexto.getText().toString();
            int posicion = listaElementos.indexOf(elemento);
            if (posicion != -1) {
                listaElementos.set(posicion, elemento);
                adaptador.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Elemento no encontrado", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminar.setOnClickListener(v -> {
            String elemento = campoTexto.getText().toString();
            listaElementos.remove(elemento);
            adaptador.notifyDataSetChanged();
        });

        btnMostrarTodos.setOnClickListener(v -> {
            // Aquí simplemente actualizamos el adaptador ya que todos los elementos ya están en la lista
            adaptador.notifyDataSetChanged();
        });
    }
}
