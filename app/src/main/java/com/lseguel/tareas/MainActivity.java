package com.lseguel.tareas;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> listaElementos;
    private ArrayAdapter<Item> adaptador;
    private ArrayList<Item> listaEditar;
    private ArrayAdapter<Item> adaptadorEditar;
    private EditText campoTexto;
    private int contadorId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoTexto = findViewById(R.id.editText);
        EditText campoId = findViewById(R.id.editTextNumber);
        EditText campoActualizar = findViewById(R.id.editTextActualizar);
        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnEditar = findViewById(R.id.btnEditar);
        Button btnActualizar = findViewById(R.id.btnActualizar);
        Button btnEliminar = findViewById(R.id.btnEliminar);
        Button btnMostrarTodos = findViewById(R.id.btnMostrarTodos);
        ListView listView = findViewById(R.id.listView);
        listView.setVisibility(View.GONE);
        CheckBox estado = findViewById(R.id.estado);

        Spinner spinnerFiltro = findViewById(R.id.spinnerFiltro);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.opciones_filtro, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltro.setAdapter(adapter);


        listaElementos = new ArrayList<>();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaElementos);
        listView.setAdapter(adaptador);

        listaEditar = new ArrayList<>();
        adaptadorEditar = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaEditar);


        btnAgregar.setOnClickListener(v -> {
            String texto = campoTexto.getText().toString();
            String prioridadSeleccionada = obtenerPrioridadSeleccionada();
            boolean esSeleccionado = estado.isChecked();

            Item item = new Item(contadorId++, texto, prioridadSeleccionada, esSeleccionado);
            listaElementos.add(item);
            adaptador.notifyDataSetChanged();

            campoTexto.setText("");
            estado.setChecked(false);
        });

        btnEditar.setOnClickListener(v -> {
            try {
                int idBuscado = Integer.parseInt(campoId.getText().toString());
                Item elementoBuscado = null;

                for (Item item : listaElementos) {
                    if (item.getId() == idBuscado) {
                        elementoBuscado = item;
                        break;
                    }
                }

                if (elementoBuscado != null) {
                    listaEditar.clear();
                    listaEditar.add(elementoBuscado);
                    listView.setAdapter(adaptadorEditar);
                    listView.setVisibility(View.VISIBLE);
                    adaptadorEditar.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "ID no encontrado", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Por favor, introduce un ID válido", Toast.LENGTH_SHORT).show();
            }
        });


        btnActualizar.setOnClickListener(v -> {
            try {
                int idActualizacion = Integer.parseInt(campoId.getText().toString());

                Item elementoActualizar = null;
                for (Item item : listaElementos) {
                    if (item.getId() == idActualizacion) {
                        elementoActualizar = item;
                        break;
                    }
                }


                if (elementoActualizar != null) {
                    String nuevoDato = campoActualizar.getText().toString();
                    elementoActualizar.actualizar(nuevoDato);
                    adaptador.notifyDataSetChanged();
                    Toast.makeText(this, "Elemento actualizado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "ID no encontrado", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Por favor, introduce un ID válido", Toast.LENGTH_SHORT).show();
            }
        });

        // Boton Eliminar
        btnEliminar.setOnClickListener(v -> {
            try {
                int idAEliminar = Integer.parseInt(campoId.getText().toString());
                for (int i = 0; i < listaElementos.size(); i++) {
                    if (listaElementos.get(i).getId() == idAEliminar) {
                        listaElementos.remove(i);
                        adaptador.notifyDataSetChanged();
                        Toast.makeText(this, "Elemento eliminado", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(this, "ID no encontrado", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Por favor, introduce un ID válido", Toast.LENGTH_SHORT).show();
            }
            campoId.setText("");
        });

        //Botón Mostrar Todos
        btnMostrarTodos.setOnClickListener(v -> {
            spinnerFiltro.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);  // Muestra el ListView

            listView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();  // Actualiza el adaptador (opcional si ya lo estás haciendo en otro lugar)
        });

        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        // Ordenar A - Z
                        Collections.sort(listaElementos, (item1, item2) -> item1.getTexto().compareTo(item2.getTexto()));
                        break;
                    case 1:
                        // Ordenar Z - A
                        Collections.sort(listaElementos, (item1, item2) -> item2.getTexto().compareTo(item1.getTexto()));
                        break;
                    case 2:
                        // Ordenar ID de menor a mayor
                        Collections.sort(listaElementos, Comparator.comparingInt(Item::getId));
                        break;
                    case 3:
                        // Ordenar ID de mayor a menor
                        Collections.sort(listaElementos, (item1, item2) -> item2.getId() - item1.getId());
                        break;
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No hacer nada aquí
            }
        });

    }

    private String obtenerPrioridadSeleccionada() {
        RadioGroup prioridad = findViewById(R.id.prioridad);
        int idSeleccionado = prioridad.getCheckedRadioButtonId();
        if (idSeleccionado == R.id.prioridadAlta) {
            return "Alta";
        } else if (idSeleccionado == R.id.prioridadMedia) {
            return "Media";
        } else if (idSeleccionado == R.id.prioridadBaja) {
            return "Baja";
        } else {
            return "Sin prioridad";
        }
    }
}
