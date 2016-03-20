package staunstrups.dk.barcode;

/**
 * Created by jst on 3/20/16.
 */
public class SharedBoolean {

    private static SharedBoolean sString;
    private Boolean mBoolean= false;

    private void SharedBoolean() {}

    public static SharedBoolean get() {
        if (sString==null) sString= new SharedBoolean();
        return sString;
    }

    public void set(Boolean b) { mBoolean= b;}
    public Boolean getValue() {return mBoolean; }

}
