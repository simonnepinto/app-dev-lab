package com.simonne.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        TextView text = (TextView)findViewById(R.id.loginSuccessful);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            text.setText("Login Successful " + bundle.getString("name") + "!");
        }
    }

    public void logout(View view) {
        finish();
    }
}