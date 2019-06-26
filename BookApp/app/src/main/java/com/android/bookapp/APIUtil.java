package com.android.bookapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class APIUtil
{
    private final static String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes";
    private final static String QUERY_PARAMETER_KEY ="q";
    private final static String API_KEY ="AIzaSyBukypy3QgdKX2pnSE82qVRODBRYPF3EU4";
    private final static String KEY ="key";

    public static URL buildUrl(String title)
    {

        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY , title)
                .appendQueryParameter(KEY,API_KEY)
                .build();
        try
        {
             url = new URL(uri.toString());
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }

        return url;
    }

    public static String getJson(URL url) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream stream = connection.getInputStream();
        Scanner scanner = new Scanner(stream);
        scanner.useDelimiter("\\A");

        try {
            Boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();
            } else {
                return null;
            }
        }
        catch (Exception e)
        {
            Log.d("Eroor" ,"Eror");
            return null;
        }

        finally {
            connection.disconnect();
        }


    }

    public static ArrayList<Book> getBooksFromJson(String json)
    {
        String ID ="id";
        String TITLE = "title";
        String SUBTITLE = "subtitle";
        String AUTHORS = "authors";
        String PUBLISHER = "publisher";
        String PUBLISHER_DATE = "publishedDate";
        String ITEMS = "items";
        String VOLUMEINFOR = "volumeInfo";
        ArrayList<Book> books = new ArrayList<>();
        try {

            JSONObject jsonBooks = new JSONObject(json);
            JSONArray jsonArray = jsonBooks.getJSONArray(ITEMS);
            int numberOfBooks = jsonArray.length();
            for(int i=0;i<numberOfBooks;i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject volumeObject = jsonObject.getJSONObject(VOLUMEINFOR);
                int authorNum = volumeObject.getJSONArray(AUTHORS).length();
                String[] authors = new String[authorNum];
                for(int j=0;j<authorNum;j++)
                {
                  authors[j] = volumeObject.getJSONArray(AUTHORS).get(j).toString();
                }
                Book book = new Book(jsonObject.getString(ID),
                        volumeObject.getString(TITLE),
                        (volumeObject.isNull(SUBTITLE)?"":volumeObject.getString(SUBTITLE)),
                        authors,
                        volumeObject.getString(PUBLISHER),
                        volumeObject.getString(PUBLISHER_DATE));

                books.add(book);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return books;
    }
}
