package beta.user.consultasus.cadastro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import beta.user.consultasus.R;
import beta.user.consultasus.utils.MaskEditUtil;

public class TabFragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cadastro_form_tab1, container, false);
        EditText cpf = (EditText) view.findViewById(R.id.cpf);
        EditText sus = (EditText) view.findViewById(R.id.num_sus);
        cpf.addTextChangedListener(MaskEditUtil.mask(cpf, MaskEditUtil.FORMAT_CPF));
        sus.addTextChangedListener(MaskEditUtil.mask(sus, "###.####.####.####"));
        return view;
    }
}
