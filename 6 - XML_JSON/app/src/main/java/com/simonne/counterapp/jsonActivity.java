package com.simonne.counterapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class jsonActivity extends AppCompatActivity {

    TextView textView2;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        textView2 = (TextView)findViewById(R.id.textView2);
        parseJSON();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void parseJSON(){
        StringBuilder stringBuilder = new StringBuilder();
        String json;

        try{
            InputStream is2 = getAssets().open("Recipe.json");
            int size = is2.available(); //returns no of bytes occupied by file
            byte buffer[] = new byte[size];
            is2.read(buffer);
            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                stringBuilder.append("\nDish Name: ").append(jsonObject.getString("name")).append("\n");
                stringBuilder.append("Quantity: ").append(jsonObject.getString("quantity")).append("\n");
                stringBuilder.append("Serves: ").append(jsonObject.getString("serves")).append("\n");
                stringBuilder.append("Type: ").append(jsonObject.getString("type")).append("\n");
                stringBuilder.append("Cooking Time: ").append(jsonObject.getString("cooking time")).append("\n");
                stringBuilder.append("_________");
            }
            textView2.setText(stringBuilder.toString());
            is2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back(View view) {
        finish();
    }
}