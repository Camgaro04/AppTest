package codes.gen.com.serviinformacion.Comunication;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import codes.gen.com.serviinformacion.Utils.MessageDialog;

public class ServiceConnectionServer {

    public String result = "";
    public ServiceConnectionServer(){


    }

    public void CallWithoutParams(final Context context, int selectedItem, String url){
            RequestQueue requestQueue =  Volley.newRequestQueue(context);
            StringRequest arrReq = new StringRequest(SelectMethod(selectedItem), url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            if(response != null){
                                MessageDialog messageDialog = new MessageDialog(context,"Respuesta del llamado",response.toString());
                                messageDialog.ShowAlertDialog();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            MessageDialog messageDialog = new MessageDialog(context,"Error",error.getMessage());
                            messageDialog.ShowAlertDialog();
                        }
                    }

            );
            requestQueue.add(arrReq);
    }

    private int SelectMethod(int methodNumber){

        switch (methodNumber){
            case 0:
                return  Request.Method.GET;
            case 1:
                return Request.Method.POST;
            case 2:
                return Request.Method.PUT;
            case 3:
                return Request.Method.DELETE;
        }

        return 200;
    }

}
