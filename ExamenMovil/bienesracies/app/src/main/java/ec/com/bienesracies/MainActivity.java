package ec.com.bienesracies;

import ec.com.bienesracies.network.ApiService;
import ec.com.bienesracies.adapters.PropiedadesAdapter;
import ec.com.bienesracies.network.RetrofitClient;
import ec.com.bienesracies.models.Propiedades;

import ec.com.bienesracies.models.ApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ApiService apiService;
    private RecyclerView rvPropiedades;
    private PropiedadesAdapter propiedadesAdapter;
    private EditText etId, etDireccion, etDescription, etPrecio, etAgenteId;
    private Button btnCreate, btnUpdate, btnDelete, btnListar, btnDetalles ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etDireccion = findViewById(R.id.etDireccion);
        etDescription = findViewById(R.id.etDescription);
        etPrecio = findViewById(R.id.etPrecio);
        etAgenteId = findViewById(R.id.etAgenteId);
        btnCreate = findViewById(R.id.btnCreate);
        btnDetalles = findViewById(R.id.btnDetalles);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnListar = findViewById(R.id.btnListar);
        rvPropiedades = findViewById(R.id.rvPropiedades);

        rvPropiedades.setLayoutManager(new LinearLayoutManager(this));
        apiService = RetrofitClient.getClient().create(ApiService.class);

        propiedadesAdapter = new PropiedadesAdapter(new ArrayList<>());
        rvPropiedades.setAdapter(propiedadesAdapter);

        btnCreate.setOnClickListener(v -> {
            String direccion = etDireccion.getText().toString();
            String descripcion = etDescription.getText().toString();
            String precio = etPrecio.getText().toString();
            int id_agente = Integer.parseInt(etAgenteId.getText().toString());
            Propiedades propiedades = new Propiedades(direccion, descripcion, precio, id_agente);
            create(propiedades);
        });

        btnDetalles.setOnClickListener(v -> {
            int Id;
            try {
                Id = Integer.parseInt(etId.getText().toString());
                detalles(Id);
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "ID de producto inválido.", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            int Id;
            try {
                Id = Integer.parseInt(etId.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "ID de la propiedad inválido.", Toast.LENGTH_SHORT).show();
                return;
            }

            String direccion = etDireccion.getText().toString();
            String descripcion = etDescription.getText().toString();
            String precio = etPrecio.getText().toString();
            int id_agente;

            try {
                id_agente = Integer.parseInt(etAgenteId.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "ID de agente inválido.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verificar si los campos están vacíos
            if (direccion.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
                Toast.makeText(MainActivity.this, "Todos los campos deben estar llenos.", Toast.LENGTH_SHORT).show();
                return;
            }

            Propiedades propiedades = new Propiedades(Id, direccion, descripcion, precio, id_agente);

            // Llamar al método de actualización
            update(Id, propiedades);
        });

        btnDelete.setOnClickListener(v -> {
            int Id = Integer.parseInt(etId.getText().toString());
            delete(Id);
        });

        btnListar.setOnClickListener(v -> {

            listar();
        });
    }

    private void clearFields() {
        etId.setText("");
        etDireccion.setText("");
        etDescription.setText("");
        etPrecio.setText("");
        etAgenteId.setText("");
    }

    private void create(Propiedades propiedades) {
        Call<Propiedades> call = apiService.createPropiedad(propiedades);
        call.enqueue(new Callback<Propiedades>() {
            @Override
            public void onResponse(Call<Propiedades> call, Response<Propiedades> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MainActivity.this, "Propiedad creada con éxito", Toast.LENGTH_SHORT).show();
                    clearFields(); // Limpia los campos después de la creación exitosa
                } else {
                    Toast.makeText(MainActivity.this, "Error al crear la propiedad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propiedades> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo en la comunicación: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void update(int Id, Propiedades propiedades) {
        Map<String, Object> body = new HashMap<>();
        body.put("id", Id);
        body.put("direccion", propiedades.getDireccion());
        body.put("descripcion", propiedades.getDescripcion());
        body.put("precio", propiedades.getPrecio());
        body.put("id_agente", propiedades.getId_agente());

        Call<Propiedades> call = apiService.actualizarPropiedad(body);
        call.enqueue(new Callback<Propiedades>() {
            @Override
            public void onResponse(Call<Propiedades> call, Response<Propiedades> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Propiedad actualizada con éxito", Toast.LENGTH_SHORT).show();
                    clearFields(); // Limpia los campos después de la creación exitosa
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(MainActivity.this, "Error al actualizar la propiedad: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error desconocido al actualizar la propiedad", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Propiedades> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo en la comunicación: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void delete(int Id) {

        Call<ApiResponse> call = apiService.eliminarPropiedad(Collections.singletonMap("id", Id));
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MainActivity.this, "Propiedad eliminada con éxito", Toast.LENGTH_SHORT).show();
                    clearFields(); // Limpia los campos después de la creación exitosa
                } else {
                    Toast.makeText(MainActivity.this, "Error al eliminar la propiedad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo en la comunicación: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listar() {
        Call<List<Propiedades>> call = apiService.listaPropiedad();
        call.enqueue(new Callback<List<Propiedades>>() {
            @Override
            public void onResponse(Call<List<Propiedades>> call, Response<List<Propiedades>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Propiedades> propiedades = response.body();
                    if (propiedades != null && !propiedades.isEmpty()) {
                        propiedadesAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Propiedades listadas con éxito", Toast.LENGTH_SHORT).show();
                        clearFields(); // Limpia los campos después de la creación exitosa
                    }else{
                        Toast.makeText(MainActivity.this, "No se encontraron propiedades", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error al listar propiedades", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Propiedades>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo en la comunicación: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void detalles(int Id) {
        Map<String, Integer> body = new HashMap<>();
        body.put("id", Id);

        Call<Propiedades> call = apiService.detallesPropiedad(body);
        call.enqueue(new Callback<Propiedades>() {
            @Override
            public void onResponse(Call<Propiedades> call, Response<Propiedades> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Propiedades propiedades = response.body();
                    if (propiedades != null) {
                        etDireccion.setText(propiedades.getDireccion());
                        etDescription.setText(propiedades.getDescripcion());
                        etPrecio.setText(propiedades.getPrecio());
                        etAgenteId.setText(String.valueOf(propiedades.getId_agente()));
                        Toast.makeText(MainActivity.this, "Propiedad obtenida con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Propiedad no encontrado.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error al leer la propiedad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propiedades> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo en la comunicación: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
