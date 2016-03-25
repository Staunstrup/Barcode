package staunstrups.dk.bicyclestands;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jst on 3/18/16.
 */
public class NetworkFetcher {
    private static final String TAG = "NetworkFetchr";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
    public Position[] fetchItems(String param) {

        //warning may return null
        try {
            String url = Uri.parse(param)
                    .buildUpon()
                    //.appendQueryParameter("apikey", APIKey)
                    .build().toString();
            Log.i(TAG, url);
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON");
            JSONObject jsonBody = new JSONObject(jsonString);
            return parseItems(jsonBody);

        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
            return null;
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
            return null;
        }
    }

    private Position[] parseItems(JSONObject jsonBody) throws IOException, JSONException {
        JSONArray featureArray= jsonBody.getJSONArray("features");
        if (featureArray.length()>0) {
            Position[] tempArr= new Position[featureArray.length()];
            for (int i=0; (i< featureArray.length()); i++)  {
                JSONArray coOrd= featureArray.getJSONObject(i)
                        .getJSONObject("geometry")
                        .getJSONArray("coordinates");
                tempArr[i]= new Position(coOrd.getDouble(0), coOrd.getDouble(1));
                Log.i(TAG, tempArr[i].toString());
            }
            return tempArr;
        } else return null;
    }
}
