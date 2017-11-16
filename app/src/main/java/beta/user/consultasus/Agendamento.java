package beta.user.consultasus;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Lucas on 16/11/2017.
 */

public class Agendamento {

    public Agendamento(AppCompatActivity app){
        RelativeLayout mainLayout = (RelativeLayout) app.findViewById(R.id. screen_conteudo);
        LayoutInflater inflater = (LayoutInflater)app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.activity_agendamento, null);
        mainLayout.removeAllViews();
        mainLayout.addView(layout);

        FloatingActionButton fab = (FloatingActionButton) app.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
