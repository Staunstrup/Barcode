package staunstrups.dk.bicyclestands;
//code adopted from http://www.mysamplecode.com/2011/09/android-barcode-scanner-using-zxing.html

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;


public class JSONActivity extends Activity {
    private TextView contents;

    private class FetchJSONTask extends AsyncTask<String, Void, Position[]> {

        @Override
        protected Position[] doInBackground(String... params) {
             return new NetworkFetcher().fetchItems(params[0]);
        }
        @Override
        protected void onPostExecute(Position[] result) {    }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        contents= (TextView) findViewById(R.id.content);
        new FetchJSONTask().execute(" http://wfs-kbhkort.kk.dk/k101/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=k101:cykelstativ&outputFormat=json&SRSNAME=EPSG:4326");

    }

}

