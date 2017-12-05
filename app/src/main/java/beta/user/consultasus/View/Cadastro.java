package beta.user.consultasus.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import beta.user.consultasus.R;
import beta.user.consultasus.cadastro.PagerCadastro;
import beta.user.consultasus.utils.APIHTTP;

/**
 * Created by Lucas on 16/11/2017.
 */

public class Cadastro {
    private AppCompatActivity app;
    private View view_layout;

    public Cadastro(AppCompatActivity app){
        this.app = app;
        RelativeLayout mainLayout = (RelativeLayout) app.findViewById(R.id. screen_conteudo);
        LayoutInflater inflater = (LayoutInflater)app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view_layout = inflater.inflate(R.layout.activity_cadastro, null);
        mainLayout.removeAllViews();
        mainLayout.addView(view_layout);

        instancia_Pager();

        FloatingActionButton fab = (FloatingActionButton) app.findViewById(R.id.fab_save);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void instancia_Pager(){
        RelativeLayout layout  = (RelativeLayout) view_layout;
        TabLayout tabLayout = (TabLayout) layout.getChildAt(0);
        final ViewPager viewPager = (ViewPager) layout.getChildAt(1);

        final PagerCadastro adapter = new PagerCadastro
                (app.getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public class AlterarDadosTask extends AsyncTask<String, Void, Boolean> {
        private String erro;
        private JSONObject dados = null;
        private ProgressDialog pDialog;

        AlterarDadosTask() {
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
                dados = APIHTTP.getObject("consultas/all","PUT","");
                if(dados.getString("result") == "ok"){

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

    public class GetDadosTask extends AsyncTask<String, Void, Boolean> {
        private String erro;
        private JSONObject dados = null;
        private ProgressDialog pDialog;

        GetDadosTask() {
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
