package beta.user.consultasus.utils;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

/**
 * Created by User on 05/12/2017.
 */

public class DialogError extends AlertDialog {
    private AlertDialog dialog;
    private boolean showing = false;
    public DialogError(@NonNull final FragmentActivity activity){
        super(activity);
    }
    public DialogError(@NonNull final FragmentActivity activity, final String titulo,String erro) {
        super(activity);
        Builder builder1 = new Builder(activity);
        builder1.setTitle(titulo);
        builder1.setMessage(erro);
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Fechar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(titulo == "Erro")
                            activity.onBackPressed();
                        showing = false;
                        dialog.cancel();
                    }
                });
        dialog = builder1.create();
    }
    public void show(){
        showing = true;
        dialog.show();
    }
    @Override
    public boolean isShowing(){
        return showing;
    }
}
