package beta.user.consultasus.Control;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import beta.user.consultasus.R;
import beta.user.consultasus.cadastro.PagerCadastro;
import beta.user.consultasus.utils.APIHTTP;

public class Registrar extends AppCompatActivity {
    private TabLayout tabLayout;
    private Button btn_back;
    private Button btn_next;
    private Button btn_finish;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        context = this;

        tabLayout = (TabLayout) findViewById(R.id.tab_register);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        btn_back = (Button) findViewById(R.id.back);
        btn_next = (Button) findViewById(R.id.next);
        btn_finish = (Button) findViewById(R.id.finish);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_register);
        final PagerCadastro adapter = new PagerCadastro
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    btn_back.setVisibility(View.GONE);
                    btn_finish.setVisibility(View.GONE);
                    btn_next.setVisibility(View.VISIBLE);
                }else if(tab.getPosition() == tabLayout.getTabCount() - 1){
                    btn_back.setVisibility(View.VISIBLE);
                    btn_finish.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.GONE);
                }else{
                    btn_back.setVisibility(View.VISIBLE);
                    btn_finish.setVisibility(View.GONE);
                    btn_next.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        disableClickTab();
    }

    public void click_back(View v){
        int index;
        if((index = tabLayout.getSelectedTabPosition()) > 0){
            index--;
            tabLayout.getTabAt(index).select();
            btn_finish.setVisibility(View.GONE);
            btn_next.setVisibility(View.VISIBLE);
            if(index == 0){
                btn_back.setVisibility(View.GONE);
            }
        }

    }
    public void click_next(View v){
        int index;
        if((index = tabLayout.getSelectedTabPosition()) < tabLayout.getTabCount() - 1) {
            index++;
            tabLayout.getTabAt(index).select();
            btn_back.setVisibility(View.VISIBLE);
            if (index == tabLayout.getTabCount() - 1) {
                btn_next.setVisibility(View.GONE);
                btn_finish.setVisibility(View.VISIBLE);
            }
        }
    }
    public void click_finish(View v){
        JSONObject cadastro = new JSONObject();
        try {
            cadastro.put("nome", ((TextView)findViewById(R.id.nome_completo)).getText().toString() );
            cadastro.put("cpf", ((TextView)findViewById(R.id.cpf)).getText().toString() );
            cadastro.put("num_sus", ((TextView)findViewById(R.id.num_sus)).getText().toString() );
            cadastro.put("end_rua", ((TextView)findViewById(R.id.endereco)).getText().toString() );
            cadastro.put("end_num", ((TextView)findViewById(R.id.numero)).getText().toString() );
            cadastro.put("end_compl", ((TextView)findViewById(R.id.complemento)).getText().toString() );
            cadastro.put("end_bairro", ((TextView)findViewById(R.id.bairro)).getText().toString() );
            cadastro.put("end_cidade", ((TextView)findViewById(R.id.cidade)).getText().toString() );
            cadastro.put("end_cep", ((TextView)findViewById(R.id.cep)).getText().toString() );
            cadastro.put("end_uf", ((TextView)findViewById(R.id.uf)).getText().toString() );
            cadastro.put("login", ((TextView)findViewById(R.id.email)).getText().toString() );
            cadastro.put("password", ((TextView)findViewById(R.id.senha)).getText().toString() );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RegistrarTask task = new RegistrarTask();
        task.execute(cadastro.toString());
    }

    private void disableClickTab(){
        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    public class RegistrarTask extends AsyncTask<String, Void, Boolean> {
        private String erro;
        private JSONObject dados = null;
        private ProgressDialog pDialog;

        RegistrarTask() {
        }
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(context);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Theme_AppCompat_DayNight_Dialog));
            if(success)
                builder.setTitle("Cadastro").setMessage("Registro realizado com sucesso");
            else
                builder.setTitle("Cadastro").setMessage("Registro não foi realziado");

            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }
}
