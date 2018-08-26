package codes.gen.com.serviinformacion.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class MessageDialog {

    private Context context;
    private String title,message;

    public MessageDialog(Context context,String title,String message){
        this.context = context;
        this.title = title;
        this.message = message;
    }


    public void ShowAlertDialog()
    {
        String TextButton = "OK";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton(TextButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
