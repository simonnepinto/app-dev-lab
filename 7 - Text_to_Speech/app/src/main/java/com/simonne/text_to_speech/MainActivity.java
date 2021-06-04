package com.simonne.text_to_speech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText)findViewById(R.id.input);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
    }

    public void textToSpeech(View view) {
        String text = input.getText().toString();

        if(!text.matches("")){
            Toast toast = Toast.makeText(getApplicationContext(), Html.fromHtml("<font color = '#472605'><strong>"
                    + text + "</strong></font>"), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 100, 100);
            View toastView = toast.getView();
            toastView.setBackgroundResource(R.drawable.toast_bg);
            toast.show();
        }
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void refresh(View view) {
        input.setText("");
    }
}