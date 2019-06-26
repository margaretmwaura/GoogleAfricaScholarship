package com.android.bookapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            URL bookUrl = APIUtil.buildUrl("cooking");
           new BooksQueryTask().execute(bookUrl);
        }
        catch (Exception e)
        {
            Log.d("Erro" , "Error");
        }
    }

    public class BooksQueryTask extends AsyncTask<URL , Void , String>
    {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;
            try
            {
                result = APIUtil.getJson(searchUrl);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = (TextView) findViewById(R.id.tv_response);

            textView.setText(s);

            ArrayList<Book> books_list = APIUtil.getBooksFromJson(s);
        }
    }
}
