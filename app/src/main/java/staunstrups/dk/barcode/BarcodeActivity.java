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
    private TextView outpan;

    private class FetchOutpanTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            scannedItem sI= new NetworkFetcher().fetchItems(params[0]);
            return sI.getName();
        }
        @Override
        protected void onPostExecute(String result) {   outpan.setText(result);   }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        outpan= (TextView) findViewById(R.id.contentOutpan);

        try {
            Button scanner = (Button)findViewById(R.id.scan_outpan);
            scanner.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                    startActivityForResult(intent, 0);
                }

            });

        } catch (ActivityNotFoundException anfe) {
            Log.e("onCreate", "Scanner Not Found", anfe);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                new FetchOutpanTask().execute(contents);
                /* Handle successful scan
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();*/
            } else if (resultCode == RESULT_CANCELED) {
// Handle cancel
                Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            }
        }
    }

}

