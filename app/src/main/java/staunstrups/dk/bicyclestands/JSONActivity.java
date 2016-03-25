package staunstrups.dk.bicyclestands;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;


public class JSONActivity extends Activity {
    private TextView contents;
    private Position[] allBStands;

    private class FetchJSONTask extends AsyncTask<String, Void, Position[]> {

        @Override
        protected Position[] doInBackground(String... params) {
             return new NetworkFetcher().fetchItems(params[0]);
        }
        @Override
        protected void onPostExecute(Position[] result) {
            allBStands= result;
            String roadname= findClosest(result, new Position (55.69235781300705, 12.586233843911234, "Her"));
            contents.setText(roadname);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        contents= (TextView) findViewById(R.id.content);
        new FetchJSONTask().execute(" http://wfs-kbhkort.kk.dk/k101/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=k101:cykelstativ&outputFormat=json&SRSNAME=EPSG:4326");

    }

    private String findClosest(Position[] b, Position target ) {
        double temp;
        if ((b != null ) && (b.length>0)) {
            int i= b.length -1;
            Position closest= b[i];
            double min= distance(closest, target);
            while (i>0) {
                i= i-1;
                temp= distance(b[i], target);
                if ( temp< min) { closest= b[i]; min= temp;}
            }
            return closest.getWhere();
        }
        return "No positions found";
    }

    private double distance (Position p1, Position p2) {
        return Math.abs(p1.getLa()-p2.getLa()) + Math.abs(p1.getLo() - p2.getLo());
    }

}

