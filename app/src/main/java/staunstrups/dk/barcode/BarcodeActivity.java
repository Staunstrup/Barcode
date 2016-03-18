package staunstrups.dk.barcode;
//code adopted from http://www.mysamplecode.com/2011/09/android-barcode-scanner-using-zxing.html

import android.app.Activity;
import android.content.ActivityNotFoundException;import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class BarcodeActivity extends Activity {
    private TextView url, content;

    private class FetchTask extends AsyncTask<String, Void, String> {
        private static final String pattern= "<tr><td>Description</td><td></td><td>";
        @Override
        protected String doInBackground(String... params) {
            String result= null; int b=0; int e=0;
            try {
                result = new NetworkFetcher().getUrlString("https://www.upcdatabase.com/item/"+params[0]);
                b= result.indexOf(pattern)+pattern.length();
                e= result.indexOf("</", b);
                Log.i("Barcode", result.subSequence(b, e).toString());
            } catch (IOException ioe) {
                Log.e("HttpUrl", "Failed to fetch URL:", ioe);
            }
            return result.subSequence(b, e).toString();
        }
        @Override
        protected void onPostExecute(String result) {
            content.setText(result);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        content= (TextView) findViewById(R.id.content);

        try {
            Button scanner = (Button)findViewById(R.id.scanner);
            scanner.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                }

            });

            Button scanner2 = (Button)findViewById(R.id.scanner2);
            scanner2.setOnClickListener(new View.OnClickListener() {

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
                content.setText("       ");
                new FetchTask().execute(contents);
                // Handle successful scan
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else if (resultCode == RESULT_CANCELED) {
// Handle cancel
                Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            }
        }
    }

}

