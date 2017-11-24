package beta.user.consultasus.Control;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import beta.user.consultasus.R;
import beta.user.consultasus.cadastro.PagerCadastro;

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
}
