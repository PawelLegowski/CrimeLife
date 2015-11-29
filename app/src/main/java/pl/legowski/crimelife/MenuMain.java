package pl.legowski.crimelife;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MenuMain extends AppCompatActivity {

    private final static String CONFIG_ADRESS   = "http://pawellegowski.cba.pl/config.txt/";
    private final static String CONFIG_FILE     = "/CrimeLife/Config.txt/";

    public static final int MENU_MAP        = 0;
    public static final int MENU_STORE      = 1;
    public static final int MENU_CREDITS    = 2;
    public static final int MENU_OPTIONS    = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        new configUpdater().execute();
    }


/*
  C)ccc   O)oooo  N)n   nn F)ffffff I)iiii   G)gggg
 C)   cc O)    oo N)nn  nn F)         I)    G)
C)       O)    oo N) nn nn F)fffff    I)   G)  ggg
C)       O)    oo N)  nnnn F)         I)   G)    gg
 C)   cc O)    oo N)   nnn F)         I)    G)   gg
  C)ccc   O)oooo  N)    nn F)       I)iiii   G)ggg
*/

    /* Async task for downloading new config */
    class configUpdater extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) { return getConfig(); }

        @Override
        protected void onPostExecute(String inputData) {
            if(inputData != null) {
                try {
                    Config.getInstance().updateConfig(new JSONObject(inputData));
                    saveConfig(inputData);
                }
                catch (JSONException e) {
                    Log.w("CONFIG", "MenuMain.configUpdater.onPostExecute() Failed to parseJSON!");
                    Log.i("CONFIG", "Loading saved config!");
                    loadConfig();
                    e.printStackTrace();
                }
            }
            super.onPostExecute(inputData);
        }

        /* Function downloading config from FTP server */
        private String getConfig(){
            URL url = null;
            try
            {
                url = new URL(CONFIG_ADRESS);
            }
            catch (MalformedURLException e)
            {
                Log.w("CONFIG", "MenuMain.getConfigRaw() failed to fromURL!");
                e.printStackTrace();
                return null;
            }
            HttpURLConnection connection = null;
            try
            {
                connection = (HttpURLConnection) url.openConnection();
            }
            catch (IOException e)
            {
                Log.w("CONFIG", "MenuMain.getConfigRaw() failed to openConnection!");
                e.printStackTrace();
                return null;
            }
            InputStream inputStream = null;
            try
            {
                inputStream = connection.getInputStream();
            }
            catch (IOException e)
            {
                Log.w("CONFIG", "MenuMain.getConfigRaw() failed to getInputStream!");
                e.printStackTrace();
            }
            Reader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String stringReadLine = null;
            try
            {
                while ((stringReadLine = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(stringReadLine);
                }
            }
            catch (IOException e)
            {
                Log.w("CONFIG", "MenuMain.getConfigRaw() failed to readLine!");
                e.printStackTrace();
            }
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                Log.w("CONFIG", "MenuMain.getConfigRaw() failed to closeInputStream!");
                e.printStackTrace();
            }
            connection.disconnect();
            return stringBuilder.toString();
        }

        /* Function saving config to internal storage */
        private void saveConfig(String rawData) {
            FileOutputStream outputStream = null;
            try {
                outputStream = openFileOutput(CONFIG_FILE, Context.MODE_PRIVATE);
            }
            catch (FileNotFoundException e) {
                Log.w("CONFIG", "MenuMain.saveConfig() failed to openOutputStream!");
                e.printStackTrace();
                return;
            }

            try {
                outputStream.write(rawData.getBytes());
            }
            catch (IOException e)
            {
                Log.w("CONFIG", "MenuMain.saveConfig() failed to writeData!");
                e.printStackTrace();
            }

            try {
                outputStream.close();
            }
            catch (IOException e) {
                Log.w("CONFIG", "MenuMain.saveConfig() failed to closeOutputStream");
                e.printStackTrace();
            }
        }

        /* Function loading config from internal storage */
        private void loadConfig() {
            FileInputStream inputStream = null;
            try {
                inputStream = openFileInput(CONFIG_FILE);
            }
            catch (FileNotFoundException e) {
                Log.w("CONFIG", "MenuMain.loadConfig() failed to openInputStream!");
                Log.w("CONFIG", "Loading default config!");
                Config.getInstance().loadDefaultConfig();
                e.printStackTrace();
                return;
            }

            Reader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String stringReadLine = null;
            try
            {
                while ((stringReadLine = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(stringReadLine);
                }
            }
            catch (IOException e) {
                Log.w("CONFIG", "MenuMain.loadConfig() failed to readLine!");
                e.printStackTrace();
            }

            try {
                Config.getInstance().updateConfig(new JSONObject(stringBuilder.toString()));
            }
            catch (JSONException e) {
                Log.w("CONFIG", "MenuMain.loadConfig() failed to parseJSON!");
                e.printStackTrace();
            }

            try {
                inputStream.close();
            }
            catch (IOException e) {
                Log.w("CONFIG", "MenuMain.loadConfig() failed to closeInputStream!");
                e.printStackTrace();
            }

            try
            {
                inputStreamReader.close();
            }
            catch (IOException e) {
                Log.w("CONFIG", "MenuMain.loadConfig() failed to closeInputStreamReader!");
                e.printStackTrace();
            }
        }
    }

    /* Function opening Map */
    private  void openMap() {
        Intent intent = new Intent(this,MenuMap.class);
        startActivityForResult(intent, MENU_MAP);
    }

    /* Function called after closing Map */
    private void onClosedMap(Bundle dataBundle) {

    }

    /* Function opening Store */
    private  void openStore() {
        Intent intent = new Intent(this,MenuStore.class);
        startActivityForResult(intent, MENU_STORE);
    }

    /* Function called after closing Store */
    private void onClosedStore(Bundle dataBundle) {

    }

    /* Function opening Credits */
    private  void openCredits() {
        Intent intent = new Intent(this,MenuCredits.class);
        startActivityForResult(intent, MENU_CREDITS);
    }

    /* Function called after closing Credits */
    private void onClosedCredits(Bundle dataBundle) {

    }

    /* Function opening Options */
    private  void openOptions() {
        Intent intent = new Intent(this,MenuOptions.class);
        startActivityForResult(intent, MENU_OPTIONS);
    }

    /* Function called after closing Options */
    private void onClosedOptions(Bundle dataBundle) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MENU_MAP:
                    onClosedMap(data.getBundleExtra("dataBundle"));
                    break;
                case MENU_STORE:
                    onClosedStore(data.getBundleExtra("dataBundle"));
                    break;
                case MENU_CREDITS:
                    onClosedCredits(data.getBundleExtra("dataBundle"));
                    break;
                case MENU_OPTIONS:
                    onClosedOptions(data.getBundleExtra("dataBundle"));
                    break;
                default:
                    Log.e("MENU","MenuMain.onActivityResult() WRONG CODE");
                    break;
            }
        }
        else {
            Log.e("MENU","MenuMain.onActivityResult() RESULT FAILED WITH CODE:"+Integer.toString(requestCode));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
