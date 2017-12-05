package beta.user.consultasus.Control;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import beta.user.consultasus.R;
import beta.user.consultasus.utils.APIHTTP;

public class Login extends AppCompatActivity {
    private TextView usuario;
    private EditText senha;
    private Context context;
    private View include_form;
    private View include_loading;

    public static String nome;
    public static String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        usuario = (TextView) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.senha);

        include_form = findViewById(R.id.include_form);
        include_loading = findViewById(R.id.include_loading);
        include_form.setVisibility(View.VISIBLE);
        include_loading.setVisibility(View.GONE);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    public void click_login(View v){
        if(validate()){
            UserLoginTask task = new UserLoginTask(usuario.getText().toString(), senha.getText().toString());
            task.execute();
        }
    }

    public void click_registrar(View v){
        Intent intent = new Intent(this, Registrar.class);
        startActivity(intent);
    }

    public boolean validate(){
        if(usuario.getText().length() < 3){
            Toast.makeText(this,"Usuário inválido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(senha.getText().length() < 4){
            Toast.makeText(this,"Senha inválida", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private String s_usuario, s_senha;
        private String erro;
        private JSONObject dados = null;
        UserLoginTask(String email, String password) {
            s_usuario = email;
            s_senha = password;
        }
        @Override
        protected void onPreExecute() {
            include_form.setVisibility(View.GONE);
            include_loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                dados = APIHTTP.getObject("login/"+s_usuario+"/"+s_senha,"GET","");
                if(dados.getString("acesso") == "ok"){
                    nome = dados.getString("nome");
                    codigo = dados.getString("codigo");
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
            if(success){
                Intent intent = new Intent(context, Main.class );
                startActivity(intent);
                finish();
            }else{
                include_form.setVisibility(View.VISIBLE);
                include_loading.setVisibility(View.GONE);
                Toast.makeText(context,"Login Inválido",Toast.LENGTH_LONG).show();
            }
        }
    }
}
