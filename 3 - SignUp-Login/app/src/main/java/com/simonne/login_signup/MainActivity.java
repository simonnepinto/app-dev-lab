package com.simonne.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button signIn, cancel;
    EditText username, password, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn = (Button) findViewById(R.id.signInButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Sname, Susername, Spassword;

                Sname = name.getText().toString();
                Susername = username.getText().toString();
                Spassword = password.getText().toString();

                if(Sname.equals("") || Susername.equals("") || Spassword.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
                }
                else if(!isValidEmail(Susername.trim())){
                    Toast.makeText(getApplicationContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
                }
                else if(!isValidPassword(Spassword.trim())){
                    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Include uppercase, lowercase, numbers and special characters with minimum " +
                                                                  "number of 8 characters!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Sign Up successful", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", Sname);
                    bundle.putString("user", Susername);
                    bundle.putString("password", Spassword);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public boolean isValidPassword(final String Password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#$%&^+_=()@!])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(Password);
        return (matcher.matches());
    }

    public boolean isValidEmail(final String Email){
        final String EMAIL_PATTERN = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(Email);
        return (matcher.matches());

    }
}