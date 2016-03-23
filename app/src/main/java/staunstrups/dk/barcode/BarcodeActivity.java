package staunstrups.dk.barcode;
//code adopted from http://www.mysamplecode.com/2011/09/android-barcode-scanner-using-zxing.html

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BarcodeActivity extends Activity {
    private TextView contents;

    private class FetchOutpanTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
             return new NetworkFetcher().fetchItems(params[0]);
        }
        @Override
        protected void onPostExecute(String result) {   contents.setText(result);   }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        contents= (TextView) findViewById(R.id.content);
        new FetchOutpanTask().execute(" http://wfs-kbhkort.kk.dk/k101/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=k101:cykelstativ&outputFormat=json&SRSNAME=EPSG:4326");

    }

}

