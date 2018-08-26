package codes.gen.com.serviinformacion.Utils;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class PhoneInformation extends BroadcastReceiver {
    public Context context;
    public Location location;
    public String informationPhone = "";
    public FusedLocationProviderClient locationProvider;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        getInformationPhone();
        Intent background = new Intent(context, BackgroundService.class);
        context.startService(background);

    }

    public void getInformationPhone() {
        try{
            locationProvider = LocationServices.getFusedLocationProviderClient(context);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context,"Por favor acepte los permisos de Locacion", Toast.LENGTH_LONG).show();

            }else{
                locationProvider.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isComplete() && task.getResult() != null){
                            location = task.getResult();
                            informationPhone += "Locacion: ("+ location.getLatitude()+")-("+location.getLongitude()+")";
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(context,"Por favor acepte los permisos de telefono", Toast.LENGTH_LONG).show();
                            }else{
                                TelephonyManager mngr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
                                informationPhone += " Emei: "+mngr.getDeviceId();
                            }

                            Toast.makeText(context,informationPhone, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }catch (Exception ex){
            Log.e("Error",ex.getMessage());
        }
    }
}
