package beta.user.consultasus.cadastro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import beta.user.consultasus.R;
import beta.user.consultasus.utils.MaskEditUtil;

public class TabFragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cadastro_form_tab2, container, false);
        EditText cep = (EditText) view.findViewById(R.id.cep);
        cep.addTextChangedListener(MaskEditUtil.mask(cep, MaskEditUtil.FORMAT_CEP));
        return view;
    }
}
