package in.co.maxxwarez.skynet.ui.devices;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
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

import in.co.maxxwarez.skynet.Home;
import in.co.maxxwarez.skynet.MainActivity;
import in.co.maxxwarez.skynet.NewDeviceSetUp;
import in.co.maxxwarez.skynet.R;

import static android.content.Context.WIFI_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class NewDeviceSetup extends Fragment implements View.OnClickListener {

    private static final String TAG = "SkyNet";
    protected WebView webView;
    private String mSSID;
    private String chipID;

    public NewDeviceSetup () {
    }

    public static NewDeviceSetup newInstance () {
        NewDeviceSetup fragment = new NewDeviceSetup();
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate");

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_device_setup, container, false);
        webView = v.findViewById(R.id.autoConfig);
        //webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        //WebSettings webSettings = webView.getSettings();
        //  webSettings.setJavaScriptEnabled(true);
        //webView.addJavascriptInterface(new NewDeviceSetUp.WebAppInterface(this.getContext()), "AndroidFunction");
        loadPage();
        getChip chip = new getChip();
        chip.execute();
        return v;
    }

    @Override
    public void onClick (View v) {

    }

    private void loadPage () {
        Log.i(TAG, "My Logger loadPage: ");


        webView.loadUrl("http://192.168.4.1/c");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String request) {
                view.loadUrl(request);
                return false;
            }
        });
    }

    class getChip extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground (String... strings) {
            java.net.HttpURLConnection urlConnection = null;
            String result = "";
            try {
                URL url = new URL("http://192.168.4.1/c");
                urlConnection = (HttpURLConnection) url.openConnection(
                );

                int code = urlConnection.getResponseCode();
                Log.i(TAG, "My Logger ChipID 4 " + code);

                if (code == 200) {
                    Log.i(TAG, "My Logger ChipID 5 " + code);
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
                Log.i(TAG, "My Logger ChipID 6 " + result);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
                //Log.d(TAG, "My Logger ChipID 2 " + result);
                //registerDevice("13304107");
            }
            return result;
        }

        @Override
        protected void onPostExecute (String result) {
            chipID = result;
            mSSID = result;
        }
    }

}