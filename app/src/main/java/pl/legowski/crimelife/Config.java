package pl.legowski.crimelife;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.net.URLConnection;

/**
 * Created by Pablo on 2015-11-27.
 */
public class Config {
    private static Config ourInstance = new Config();
    Vector<City> listCities;

    public static Config getInstance() {
        return ourInstance;
    }

    private Config() {
        listCities = new Vector<City>();
    }

    public void updateConfig(JSONObject jsonConfig) {

    }

    public void loadDefaultConfig() {

    }
}
