package in.co.maxxwarez.skynet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class NewDeviceSetUp extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SkyNet";
    protected TextView mSSID;
    protected WebView webView;
    protected View mView;
    protected Button btn_refresh;
    protected Button btn_connect;
    private Object HttpURLConnection;
    private FirebaseAuth mAuth;
    private String chipID;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_device_set_up);
        Intent intent = getIntent();
        //mSSID.setText(R.string.no_device_found);
        webView = findViewById(R.id.autoConfig);
        mSSID = findViewById(R.id.ssid);
        btn_refresh = findViewById(R.id.refresh);
        btn_connect = findViewById(R.id.connect);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "AndroidFunction");
        //mSSID.setText(getCurrentSSID());
        btn_refresh.setVisibility(View.GONE);
        btn_connect.setVisibility(View.GONE);


    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        //Log.i(TAG, "My Logger On Destroy .....");

    }

    @Override
    protected void onPause () {
        super.onPause();
        //Log.i(TAG, "My Logger On Pause .....");
    }


    @Override
    protected void onRestart () {
        super.onRestart();
        //Log.i(TAG, "My Logger On Restart .....");
    }


    @Override
    protected void onResume () {
        super.onResume();
        //Log.i(TAG, "My Logger On Resume .....");
    }


    @Override
    protected void onStop () {
        super.onStop();
        // Log.i(TAG, "My Logger On Stop .....");
    }

    @Override
    protected void onStart () {
        Log.i(TAG, "Inside onStart");
        super.onStart();
        String ssID = "";
        if (getConnState()) {
            ssID = getCurrentSSID();
            Log.i(TAG, "My Logger connectToWifi:getCurrentSSID " + getConnState());

            if (ssID.contains("SkyNet-AutoConfig")) {
                Log.i(TAG, "Inside connectToWifi  ID ssID");
                loadPage();
                showButton();
            } else {
                connectToWifi();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadPage();
                showButton();
            }
        } else {
            connectToWifi();
            getCurrentSSID();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loadPage();
            showButton();
        }
    }


    private void loadPage () {
        Log.i(TAG, "My Logger loadPage: ");
        chipID = getData();

        webView.loadUrl("http://192.168.4.1/");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String request) {
                view.loadUrl(request);
                return false;
            }
        });

    }

    public void connectToWifi () {
        Log.i(TAG, "Inside connectToWifi");
        try {
            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiConfiguration wc = new WifiConfiguration();
            wc.SSID = "\"SkyNet-AutoConfig\"";
            wc.preSharedKey = "\"PASSWORD\"";
            wc.status = WifiConfiguration.Status.ENABLED;
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiManager.setWifiEnabled(true);
            int netId = wifiManager.addNetwork(wc);
            if (netId == -1) {
                netId = getExistingNetworkId(wc.SSID);
            }
            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);
            wifiManager.reconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getExistingNetworkId (String SSID) {
        WifiManager wifiManager = (WifiManager) super.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return TODO;
        }
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            for (WifiConfiguration existingConfig : configuredNetworks) {
                if (existingConfig.SSID.equals(SSID)) {
                    return existingConfig.networkId;
                }
            }
        }
        return -1;
    }

    void showButton () {
        Log.i(TAG, "Inside showButton");
        String connID = getCurrentSSID();
        if (connID.contains("SkyNet-AutoConfig")) {
            btn_refresh.setVisibility(View.VISIBLE);
            btn_connect.setVisibility(View.GONE);
        } else {
            btn_refresh.setVisibility(View.GONE);
            btn_connect.setVisibility(View.VISIBLE);
        }

    }


    public boolean getConnState () {
        boolean state = false;

        WifiManager wifiManager = (WifiManager) super.getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE);
        ConnectivityManager connManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        //Log.i(TAG, "My Logger connectToWifi:getConnState " + networkInfo.getExtraInfo());
        if (networkInfo.isConnected()) {
            // final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            // Log.i(TAG, "My Logger connectToWifi:getCurrentSSID final" + connectionInfo);
            // if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.g)) {
            state = true;
            //}
        }

        return state;
    }


    public String getCurrentSSID () {
        String ssid = "Not Connected";
        WifiManager wifiManager = (WifiManager) super.getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE);
        ConnectivityManager connManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        //NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();

        if (activeNetwork.isConnected()) {
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = activeNetwork.getExtraInfo();
                mSSID.setText(ssid);
            }
        }
        mSSID.setText(ssid);
        //Log.i(TAG, "My Logger connectToWifi:getCurrentSSID " + ssid);
        return ssid;
    }


    @Override
    public void onClick (View v) {
        int i = v.getId();
        if (i == R.id.refresh) {
            //Log.d(TAG, "My Logger onClick: Refresh");
            loadPage();
            //registerDevice();
            //getData();
        }
        if (i == R.id.connect) {
            connectToWifi();
            //Log.d(TAG, "My Logger onClick: Connect");
            //egisterDevice("13304107");

        }

    }

    public String getData () {
        java.net.HttpURLConnection urlConnection = null;
        String result = "";
        try {
            URL url = new URL("http://192.168.4.1/c");
            urlConnection = (HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();
            //Log.d(TAG, "My Logger ChipID 4 " + code);

            if (code == 200) {
                //Log.d(TAG, "My Logger ChipID 5 " + code);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                if (in != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null)
                        result += line;
                }
                in.close();
                //registerDevice(result);
            }
            //Log.d(TAG, "My Logger ChipID 6 " + result);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
            //Log.d(TAG, "My Logger ChipID 2 " + result);
            //registerDevice("13304107");
        }
        return (result);


    }

    private void registerDevice (String chipID) {
        //Log.d(TAG, "My Logger registerDevice is" + chipID);

        mAuth = FirebaseAuth.getInstance();
        //Log.d(TAG, "My Logger: " + mAuth);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Log.d(TAG, "My Logger2: " + currentUser);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //Query query = ref.child("users").child(currentUser.getUid());
        ref.child("users").child(currentUser.getUid()).child("deviceID").child(chipID).child("ID").setValue(chipID);
        //  DatabaseReference pushUserDeviceMap = ref.child("userDeviceMap");

        ref.child("devicUsermap").child(chipID).setValue(currentUser.getUid());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("deviceID", chipID);

        startActivity(intent);
    }

    /*class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "My Logger AsyncTask  "  );
            java.net.HttpURLConnection urlConnection = null;
            String result = "";
            try {
                URL url = new URL("http://192.168.4.1/c");
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();

                //Log.d(TAG, "My Logger ChipID 1 " + code);
                if (code == 200) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    if (in != null) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                        String line = "";

                        while ((line = bufferedReader.readLine()) != null)
                            result += line;
                    }
                    in.close();
                    registerDevice(result);
                }
                Log.d(TAG, "My Logger ChipID 2 " + result);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
                Log.d(TAG, "My Logger ChipID 3 " + result);
            }
            //registerDevice(result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != "") {
                Log.d(TAG, "My Logger Inside Post Execute 0" + result);
                registerDevice(result);
            }

            registerDevice(chipID);
            Log.d(TAG, "My Logger Inside Post Execute 1" + result);

        }


    }*/

    public static class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        public WebAppInterface (Context c) {
            mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void showToast () {
            //new GetData().execute();
            //getData();
            //registerDevice(chipID);
            //Log.d(TAG, "My Logger WebAppInterface: ");
        }
    }


}
