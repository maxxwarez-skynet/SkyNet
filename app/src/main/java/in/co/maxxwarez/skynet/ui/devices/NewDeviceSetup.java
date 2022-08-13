package in.co.maxxwarez.skynet.ui.devices;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebViewClient;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.ui.commons.InstructionsDetail;
import in.co.maxxwarez.skynet.ui.commons.NoSetUp;
import in.co.maxxwarez.skynet.ui.commons.SetUpButton;


public class NewDeviceSetup extends Fragment implements View.OnClickListener {

    private static final String TAG = "SkyNet";
    protected WebView webView;
    private String mSSID;
    private String chipID;
    private FirebaseAuth mAuth;

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
        loadPage();
        getChip chip = new getChip();
        chip.execute();
        return v;
    }

    @Override
    public void onClick (View v) {

    }

    private void loadPage () {
        webView.loadUrl("http://192.168.4.1/");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String request) {
                view.loadUrl(request);
                return false;
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError (WebView view, int errorCode, String description, String failingUrl) {
                Log.i(TAG, "WebView  error" + errorCode);
            }

            @Override
            public void onPageFinished (WebView view, String url) {
                super.onPageFinished(view, url);

                if (view.getUrl().contains("wifisave?")) {
                    Log.i(TAG, "WebView Page1 " + view.getUrl());
                    updateHeader();
                    updateButton("Done", "five");
                    updateInstructions("Your device has been activated. When the light starts blinking, click Done");
                }

                if (view.getUrl().contains("wifi?")) {

                }


            }

        });

    }

    private void updateInstructions (String s) {
        InstructionsDetail instructionsDetail = InstructionsDetail.newInstance();
        Bundle args = new Bundle();
        args.putString("text", s);
        instructionsDetail.setArguments(args);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.device_details_list, instructionsDetail).commit();
    }

    private void updateButton (String text, String flag) {
        FragmentManager fragmentManager = getParentFragmentManager();
        SetUpButton setUpButton = (SetUpButton) fragmentManager.findFragmentById(R.id.device_settings_list);
        setUpButton.mButton.setText(text);
        setUpButton.flag = flag;
        setUpButton.mChipID = chipID;
    }

    private void updateHeader () {
        FragmentManager fragmentManager = getParentFragmentManager();
        NoSetUp noSetUp = (NoSetUp) fragmentManager.findFragmentById(R.id.deviceList);
        noSetUp.mTextView.setText("Your DeviceID: " + chipID);
    }

    private void loadDevicePages () {
        DeviceList deviceList = DeviceList.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.deviceList, deviceList).commit();
        //settingsList();
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
                }
                Log.i(TAG, "My Logger ChipID 6 " + result);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result;
        }

        @Override
        protected void onPostExecute (String result) {
            chipID = result;

        }
    }


}