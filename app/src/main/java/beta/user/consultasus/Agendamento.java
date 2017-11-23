package beta.user.consultasus;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Lucas on 16/11/2017.
 */

public class Agendamento {
    private AppCompatActivity app;
    private RelativeLayout texto_dialog;
    private String[] consultas;
    public Agendamento(AppCompatActivity app){
        this.app = app;
        RelativeLayout mainLayout = (RelativeLayout) app.findViewById(R.id.screen_conteudo);
        LayoutInflater inflater = (LayoutInflater)app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.activity_agendamento, null);
        mainLayout.removeAllViews();
        mainLayout.addView(layout);

        texto_dialog = (RelativeLayout)inflater.inflate(R.layout.dialog_confirmar, null);
        consultas = app.getResources().getStringArray(R.array.ArrayConsultas);

        FloatingActionButton fab = (FloatingActionButton) app.findViewById(R.id.fab);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogAdd();
            }
        });
    }

    public void createDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(app, R.style.Theme_AppCompat_DayNight_Dialog));
        builder.setCancelable(false);
        builder.setTitle("Selecione uma Consulta Médica").setItems(R.array.ArrayConsultas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createDialogConfirmar(consultas[which]);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.show();
    }

    public void createDialogConfirmar(String s){
        TextView text = (TextView) texto_dialog.getChildAt(0);
        text.setText("Deseja agendar uma consulta médica de " + s + "?");
        final Dialog dialog = new Dialog(app); // Context, this, etc.
        dialog.setContentView(texto_dialog);
        dialog.setTitle("Confirmar Agendamento");
        dialog.show();
    }
}
