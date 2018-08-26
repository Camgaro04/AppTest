package codes.gen.com.serviinformacion.Utils;
import android.support.v4.app.ActivityCompat;


public class Permissions {


    private android.app.Activity context;
    private final int REQUEST_PERMISSION = 200;

    public Permissions(android.app.Activity context){

        this.context=context;
    }

    public void SetPermission(){
        ActivityCompat.requestPermissions( this.context,
                new String[]{android.Manifest.permission.READ_PHONE_STATE
                        ,android.Manifest.permission.ACCESS_FINE_LOCATION
                        ,android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ,android.Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSION);
    }

}
