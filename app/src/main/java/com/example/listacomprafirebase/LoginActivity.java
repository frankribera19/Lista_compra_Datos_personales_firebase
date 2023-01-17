package com.example.listacomprafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegister;
    private Button btnLogin;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        inicializaComponentes();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Rellena los datos", Toast.LENGTH_SHORT).show();
                }else{
                    doRegister(email, password);
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if (email.isEmpty() && password.length() < 5){
                    Toast.makeText(LoginActivity.this, "Rellena los datos", Toast.LENGTH_SHORT).show();
                }else{
                    doLogin(email, password);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        updateUi(user);
    }

    private void doLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            user = mAuth.getCurrentUser();
                            updateUi(user);
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void doRegister(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            user = mAuth.getCurrentUser();
                            user.getUid();
                            updateUi(user);
                        }
                    }
                });
    }

    private void updateUi(FirebaseUser user) {
        if (user != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void inicializaComponentes() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
    }
}