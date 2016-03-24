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

import org.json.JSONException;
import org.json.JSONObject;


public class JsonExampleActivity extends Activity {
    private TextView contents;
    private final static String TAG= "JSON";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        contents= (TextView) findViewById(R.id.content);
        /*String JsonExStr= " {" +
                "\"t1\": \"v1\" " +
                "} "; */
        String JsonExStr= " {\n" +
                "  \"t1\": \"v1\",\n" +
                "  \"t2\": \"v2\",\n" +
                "  \"o1\": {\n" +
                "    \"o1t1\": \"o1v1\",\n" +
                "    \"o1t2\": \"o1v2\",\n" +
                "    \"o1t3\": \"o1v3\"\n" +
                "  },\n" +

                "  \"a1\": [\n" +
                "    {\n" +
                "      \"type\":  \"a1t0\",\n" +
                "      \"value\": \"a1v0\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\":  \"a1v1\",\n" +
                "      \"value\": \"a1v2\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            Log.i(TAG, JsonExStr);
            JSONObject JsonExObj = new JSONObject(JsonExStr);

            Log.i(TAG, JsonExObj.getString("t1") );
            Log.i(TAG, JsonExObj.getJSONObject("o1").getString("o1t1") );
            Log.i(TAG, JsonExObj.getJSONArray("a1").getJSONObject(0).getString("type") );
            Log.i(TAG, JsonExObj.getJSONArray("a1").getJSONObject(1).getString("value") );
        } catch (JSONException je) { Log.e(TAG,"Failed to parse JSON");
        }

    }

}

