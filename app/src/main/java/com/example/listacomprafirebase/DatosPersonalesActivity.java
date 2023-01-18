package com.example.listacomprafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listacomprafirebase.modelos.DatosPersonales;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatosPersonalesActivity extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtApellidos;
    private EditText txtTelefono;
    private Button btnGuardarDatos;

    private FirebaseDatabase database;

    private DatabaseReference refUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        inicializaVista();
        verDatos();

        btnGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNombre.getText().toString().isEmpty() || txtApellidos.getText().toString().isEmpty() || txtTelefono.getText().toString().isEmpty()){
                    Toast.makeText(DatosPersonalesActivity.this, "RELLENA LOS CAMPOS", Toast.LENGTH_SHORT).show();
                }else{
                    String nombre = txtNombre.getText().toString();
                    String apellidos = txtApellidos.getText().toString();
                    int telefono = Integer.parseInt(txtTelefono.getText().toString());

                    DatosPersonales dp = new DatosPersonales(nombre, apellidos, telefono);
                    refUser.setValue(dp);
                    startActivity(new Intent(DatosPersonalesActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void verDatos(){
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    txtNombre.setText(snapshot.child("nombre").getValue().toString());
                    txtApellidos.setText(snapshot.child("apellidos").getValue().toString());
                    txtTelefono.setText(snapshot.child("num_telefono").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializaVista() {
        txtNombre = findViewById(R.id.txtNombreDatos);
        txtApellidos = findViewById(R.id.txtApellidosDatos);
        txtTelefono = findViewById(R.id.txtTelefonoDatos);
        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);
        database = FirebaseDatabase.getInstance("https://lista-de-la-compra-fireb-98c60-default-rtdb.europe-west1.firebasedatabase.app/");
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Datos-Personales");
    }
}