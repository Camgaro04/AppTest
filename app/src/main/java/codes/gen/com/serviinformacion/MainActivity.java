package codes.gen.com.serviinformacion;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import codes.gen.com.serviinformacion.Comunication.ServiceConnectionServer;
import codes.gen.com.serviinformacion.Controllers.ActivityMap;
import codes.gen.com.serviinformacion.Utils.MessageDialog;
import codes.gen.com.serviinformacion.Utils.Permissions;
import codes.gen.com.serviinformacion.Utils.PhoneInformation;

public class MainActivity extends AppCompatActivity {

    private Context context;
    public Button btnSendRequest,btnLaunchMap;
    public EditText edtUrl;
    public Spinner spMethods;
    private String selectedMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUrl = findViewById(R.id.etUrl);
        btnSendRequest = findViewById(R.id.btSendRequest);
        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendRequest();
            }
        });
        btnLaunchMap=findViewById(R.id.btLaunchMap);
        btnLaunchMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityMap.class);
                startActivity(intent);
            }
        });
        spMethods = findViewById(R.id.spMethod);
        spMethods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMethod = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Permissions permissions = new Permissions(MainActivity.this);
        permissions.SetPermission();

        this.context = this;
        Intent alarm = new Intent(this.context, PhoneInformation.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this.context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);

        if (!alarmRunning) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 180000, pendingIntent);
        }
    }

    private void SendRequest (){
        try{
            String urlParam = edtUrl.getText().toString();
            int selectedMethod = SelectedMethod();
            if(urlParam.isEmpty() || urlParam.equals("")){
                Toast.makeText(context,"Este Campo no puede estar vacio",Toast.LENGTH_LONG).show();
                edtUrl.requestFocus();
                return;
            }

            if(selectedMethod == -1){
                Toast.makeText(context,"Metodo seleccionado invalido",Toast.LENGTH_LONG).show();
                return;
            }
            ServiceConnectionServer serviceConnection = new ServiceConnectionServer();
            serviceConnection.CallWithoutParams(context,selectedMethod,urlParam);

        }catch (Exception ex){
            Log.e("Error",ex.getMessage());
        }
    }

    private int SelectedMethod(){
        switch (selectedMethod){
            case "GET":
                return 0;
            case "POST":
                return 1;
            case "PUT":
                return 2;
            case "DELETE":
                return 3;
        }

        return -1;
    }


}
