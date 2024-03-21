package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    ImageView back;
    EditText email, password;
    FirebaseAuth mAuth;
    Button login, signup;
    CheckBox remember;

    public void onStart() {
        super.onStart();
        // Check if "Remember Me" preference is set
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean rememberMe = preferences.getBoolean("remember_me", false);

        if (rememberMe) {
            // User has selected "Remember Me" during signup
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                // User is signed in, proceed to home screen
                Intent i = new Intent(getApplicationContext(), Homescreen.class);
                startActivity(i);
                finish();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        remember = findViewById(R.id.rem);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.log);
        signup = findViewById(R.id.su);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Welcome.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();

        if (TextUtils.isEmpty(emailStr)) {
            email.setError("Email is required.");
            return;
        }
        if (TextUtils.isEmpty(passwordStr)) {
            password.setError("Password is required.");
            return;
        }

        mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "Login successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, Homescreen.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("remember_me", remember.isChecked());
        editor.apply();

    }
}