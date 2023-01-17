package com.example.listacomprafirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.listacomprafirebase.adapters.ProductosAdapter;
import com.example.listacomprafirebase.modelos.Producto;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listacomprafirebase.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.time.chrono.JapaneseChronology;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private List<Producto> productos;
    private ProductosAdapter adapter;
    private RecyclerView.LayoutManager lm;

    private FirebaseDatabase database;
    private DatabaseReference refUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        database = FirebaseDatabase.getInstance("https://lista-de-la-compra-fireb-98c60-default-rtdb.europe-west1.firebasedatabase.app/");
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lista-productos");

        productos = new ArrayList<>();
        adapter = new ProductosAdapter(productos, R.layout.producto_view_holder, this, refUser);
        lm = new LinearLayoutManager(this);

        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productos.clear();
                if (snapshot.exists()){
                    GenericTypeIndicator<ArrayList<Producto>> gti = new GenericTypeIndicator<ArrayList<Producto>>() {};
                    List<Producto> temp = snapshot.getValue(gti);
                    productos.clear();
                    productos.addAll(temp);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.contentMain.recycler.setAdapter(adapter);
        binding.contentMain.recycler.setLayoutManager(lm);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertCrearProducto().show();
            }
        });
    }

    /**
     * Especifica que menú se va a cargar en la Actividad
     * @param menu -> es el hueco donde aparece el menú
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    /**
     * Discriminar las diferentes acciones en base al elemento del menú seleccionado
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.btnLogOut) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return true;
    }

    private AlertDialog alertCrearProducto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear producto");
        builder.setCancelable(false);
        View alertView = getLayoutInflater().inflate(R.layout.producto_alert, null);
        EditText txtNombre = alertView.findViewById(R.id.txtNombreAlert);
        EditText txtCantidad = alertView.findViewById(R.id.txtCantidadAlert);
        EditText txtPrecio = alertView.findViewById(R.id.txtPrecioAlert);
        builder.setView(alertView);

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("CREAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre = txtNombre.getText().toString();
                String cantidad = txtCantidad.getText().toString();
                String precio = txtPrecio.getText().toString();

                if (!nombre.isEmpty() && !cantidad.isEmpty() && !precio.isEmpty()){
                    productos.add(new Producto(nombre, Integer.parseInt(cantidad), Float.parseFloat(precio)));
                    //adapter.notifyItemRangeInserted(productos.size()-1);
                    refUser.setValue(productos);
                }
            }
        });

        return builder.create();

    }
}