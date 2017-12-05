package beta.user.consultasus.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
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

import org.json.JSONObject;

import beta.user.consultasus.R;
import beta.user.consultasus.utils.APIHTTP;

/**
 * Created by Lucas on 16/11/2017.
 */

public class Agendamento {
    private AppCompatActivity app;
    private String[] consultas;
    private int[] id_consultas;
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

        ConsultasTask task = new ConsultasTask();
        task.execute();
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
        builder.setTitle("Selecione uma Consulta Médica").setItems(consultas, new DialogInterface.OnClickListener() {
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
                AddAgendamento task = new AddAgendamento();
                task.execute();
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

    public class AddAgendamento extends AsyncTask<String, Void, Boolean> {
        private String erro;
        private JSONObject dados = null;
        private ProgressDialog pDialog;

        AddAgendamento() {
        }
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(app);
            pDialog.setMessage("Carregando");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                dados = APIHTTP.getObject("cadastrar","POST",params[0]);
                if(dados.getString("result") == "ok"){
                    addRow(consultas[op_consulta], "Em Espera");
                    return true;
                }
                return false;
            } catch (Exception e) {
                if(e.getMessage().startsWith("Unable to resolve host"))
                    erro = "Falha ao tentar se conectar com o servidor web.\nVerifique se seu celular possui sinal com a internet.";
                else
                    erro = e.getMessage();
            }

            return false;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            pDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(app, R.style.Theme_AppCompat_DayNight_Dialog));
            if(success)
                builder.setTitle("Agendamento").setMessage("Registro realizado com sucesso");
            else
                builder.setTitle("Agendamento").setMessage("Registro não foi realziado");

            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }

    public class ConsultasTask extends AsyncTask<String, Void, Boolean> {
        private String erro;
        private JSONObject dados = null;
        private ProgressDialog pDialog;

        ConsultasTask() {
        }
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(app);
            pDialog.setMessage("Carregando");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                dados = APIHTTP.getObject("consultas/all","GET","");
                if(dados.getString("result") == "ok"){
                    addRow(consultas[op_consulta], "Em Espera");
                    return true;
                }
                return false;
            } catch (Exception e) {
                if(e.getMessage().startsWith("Unable to resolve host"))
                    erro = "Falha ao tentar se conectar com o servidor web.\nVerifique se seu celular possui sinal com a internet.";
                else
                    erro = e.getMessage();
            }

            return false;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
           // consultas;
            pDialog.dismiss();
        }
    }
}
