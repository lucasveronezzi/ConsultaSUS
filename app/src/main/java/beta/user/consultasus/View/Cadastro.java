package beta.user.consultasus.View;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import beta.user.consultasus.R;
import beta.user.consultasus.cadastro.PagerCadastro;

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
}
