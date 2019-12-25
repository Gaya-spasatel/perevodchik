package com.example.perevodchik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText ed;
    TextView tv;
    String gtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        ed = findViewById(R.id.editText);
        tv = findViewById(R.id.result);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gtext = ed.getText().toString();
                gtext.replace(" ", "%20");
                myDownload myDownload = new myDownload();
                myDownload.execute();
            }
        });
    }
    private class myDownload extends AsyncTask<Void, Void, String> {
        HttpURLConnection httpURLConnection;
        private String adress = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20191225T104707Z.ab8ca46ca8ea327d.939e7cf8244c0606b2e4c0751dd1db5e53859d8f&lang=ru-en&text=";

        @Override
        protected String doInBackground(Void... voids) {
            try{

                URL url = new URL(adress+gtext);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                StringBuffer buffer = new StringBuffer();

                while (scanner.hasNextLine()){
                    buffer.append(scanner.nextLine());
                }
                return buffer.toString();
            } catch (java.io.IOException m){
                Log.e("RRRRRRRRRRR",m.toString());
            };

            return null;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            Gson g = new Gson();
            Log.e("RRRR",s);
            Translator t = g.fromJson(s, Translator.class);
            tv.setText(t.getText().get(0).toString());
        }

    }
}
