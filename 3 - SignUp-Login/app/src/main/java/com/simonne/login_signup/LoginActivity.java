package com.simonne.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button login, lcancel;
    EditText lusername, lpassword;
    TextView attempt;
    int counter = 3;
    String name, user, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.loginButton2);
        lcancel = (Button)findViewById(R.id.cancelButton2);
        lusername = (EditText)findViewById(R.id.username2);
        lpassword = (EditText)findViewById(R.id.password2);
        attempt = (TextView)findViewById(R.id.attempt);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            name = bundle.getString("name");
            user = bundle.getString("user");
            passwd = bundle.getString("password");
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String luser = lusername.getText().toString();
                String lpasswd = lpassword.getText().toString();

                if(luser.equals(user) && lpasswd.equals(passwd)){
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, LoginSuccessActivity.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                    attempt.setVisibility(View.VISIBLE);
                    counter--;

                    if(counter == 0){
                        Toast.makeText(getApplicationContext(), "3 Failed Login Attempts!! Login Disabled.", Toast.LENGTH_SHORT).show();
                        login.setEnabled(false);
                    }
                    else
                        attempt.setText(counter + " more attempts left!");
                }
            }
        });

        lcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}