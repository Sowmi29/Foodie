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

public class SignUp extends AppCompatActivity {
    ImageView back;
    Button login,signup;
    FirebaseAuth mAuth;
    String emailStr,nameStr,pwdStr;
    EditText name,email,pwd;
    CheckBox remember;

    public void onStart() {
        super.onStart();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean rememberMe = preferences.getBoolean("remember_me", false);

        if (rememberMe) {

            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {

                Intent i = new Intent(getApplicationContext(), Homescreen.class);
                startActivity(i);
                finish();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        login = findViewById(R.id.log);
        signup = findViewById(R.id.su);
        name = findViewById(R.id.name);
        email = findViewById(R.id.editTextTextEmailAddress);
        pwd = findViewById(R.id.editTextTextPassword);
        remember = findViewById(R.id.rem);
        mAuth=FirebaseAuth.getInstance();

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,Welcome.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameStr = name.getText().toString();
                emailStr = email.getText().toString();
                pwdStr=pwd.getText().toString();

                if(TextUtils.isEmpty(nameStr)){
                    return;
                }
                if(TextUtils.isEmpty(emailStr)){
                    return;
                }
                if(TextUtils.isEmpty(pwdStr)){
                    return;
                }


                mAuth.createUserWithEmailAndPassword(emailStr, pwdStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(SignUp.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();

                                    Intent i=new Intent(SignUp.this,Homescreen.class);
                                    startActivity(i);
                                    finish();
                                } else {

                                    Toast.makeText(SignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SignUp.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("remember_me", remember.isChecked());
                editor.apply();

            }
        });

    }
}