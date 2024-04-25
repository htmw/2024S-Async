package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPassword;
    Button btnSignIn;
    TextView tvSignUpHere;

    private TextView btnGoogle;
    private GoogleSignInClient client;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnGoogle = findViewById(R.id.btnGoogle);
        etEmail = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        tvSignUpHere = findViewById(R.id.tvSignUp);
        btnSignIn = findViewById(R.id.buttonSignIn);

        mAuth= FirebaseAuth.getInstance();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this,options);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = client.getSignInIntent();
                startActivityForResult(intent, 1234);

            }
        });

        btnSignIn.setOnClickListener(view -> {
            loginUser();
        });

        tvSignUpHere.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }
    private void loginUser(){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User Logged in succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this, "Registration Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "User Logged in succesfully", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }catch (ApiException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
    }
}