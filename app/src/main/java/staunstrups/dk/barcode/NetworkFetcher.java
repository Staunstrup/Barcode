package staunstrups.dk.barcode;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jst on 3/18/16.
 */
public class NetworkFetcher {
    private static final String TAG = "FileFetchr";
    private static final String APIKey="0d093355b598c1c60d855989a0818a31";


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
    public void fetchItems(String param) {
        try {
            String url= Uri.parse("https://api.outpan.com/v2/products/"+param)
                    .buildUpon()
                    .appendQueryParameter("apikey", APIKey)
                    .build().toString();
            String jsonString= getUrlString(url);
            Log.i(TAG, "Received JSON: "+jsonString);

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
    }

}
