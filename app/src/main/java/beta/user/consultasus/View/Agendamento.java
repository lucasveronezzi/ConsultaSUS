package beta.user.consultasus.View;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import beta.user.consultasus.R;

/**
 * Created by Lucas on 16/11/2017.
 */

public class Agendamento {
    private AppCompatActivity app;
    private String[] consultas;
    private int op_consulta;
    private TableLayout table;
    private Dialog dialog_confirmar;
    private TextView text_dialog;

    public Agendamento(AppCompatActivity app){
        this.app = app;
        RelativeLayout mainLayout = (RelativeLayout) app.findViewById(R.id.screen_conteudo);
        LayoutInflater inflater = (LayoutInflater)app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.activity_agendamento, null);
        mainLayout.removeAllViews();
        mainLayout.addView(layout);

        consultas = app.getResources().getStringArray(R.array.ArrayConsultas);
        table = (TableLayout) app.findViewById(R.id.table_agenda);

        FloatingActionButton fab = (FloatingActionButton) app.findViewById(R.id.fab);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogAdd();
            }
        });

        createDialogConfirmar((RelativeLayout)inflater.inflate(R.layout.dialog_confirmar, null));
    }

    public void addRow(String txt_consulta, String txt_data){
        TableRow row = new TableRow(app);
        row.setBackground(app.getResources().getDrawable(R.drawable.border_all));

        TextView viewConsulta = new TextView(app);
        viewConsulta.setText(txt_consulta);
        viewConsulta.setTextAppearance(app,R.style.TextAppearance_AppCompat_Caption);
        viewConsulta.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        viewConsulta.setPadding(15,10,10,10);
        viewConsulta.setTextColor(Color.parseColor("#000000"));

        TextView viewData = new TextView(app);
        viewData.setText(txt_data);
        viewData.setTextAppearance(app,R.style.TextAppearance_AppCompat_Caption);
        viewData.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        viewData.setPadding(15,10,10,10);
        viewData.setTextColor(Color.parseColor("#000000"));

        row.addView(viewData);
        row.addView(viewConsulta);

        table.addView(row);
    }

    public void createDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(app, R.style.Theme_AppCompat_DayNight_Dialog));
        builder.setTitle("Selecione uma Consulta Médica").setItems(R.array.ArrayConsultas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                op_consulta = which;
                showDialogConfirmar(consultas[which]);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.show();
    }

    public void createDialogConfirmar(RelativeLayout layout_dialog){
        dialog_confirmar = new Dialog(app); // Context, this, etc.
        text_dialog = (TextView) layout_dialog.getChildAt(0);

        Button btn_confirmar = (Button) ((LinearLayout)layout_dialog.getChildAt(1)).getChildAt(1);
        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow(consultas[op_consulta], "Em Espera");
                dialog_confirmar.hide();
            }
        });
        Button btn_cancelar = (Button) ((LinearLayout)layout_dialog.getChildAt(1)).getChildAt(0);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_confirmar.hide();
            }
        });

        dialog_confirmar.setContentView(layout_dialog);
        dialog_confirmar.setCancelable(false);
        dialog_confirmar.setTitle("Confirmar Agendamento");
    }

    public void showDialogConfirmar(String s){
        text_dialog.setText("Deseja agendar uma consulta médica de " + s + "?");
        dialog_confirmar.show();
    }
}
