package admin.example.com.campus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import admin.example.com.java.J_array;
import admin.example.com.java.J_bas_syn;
import admin.example.com.java.J_cao;
import admin.example.com.java.J_dec_mak;
import admin.example.com.java.J_exp;
import admin.example.com.java.J_loops;
import admin.example.com.java.J_methods;
import admin.example.com.java.J_numbers;
import admin.example.com.java.J_op;
import admin.example.com.java.J_string;
import admin.example.com.java.J_var;
import admin.example.com.nav.R;

public class MenuLJ extends Fragment implements View.OnClickListener{

    //final Context context = this;
    private Button button;
    //final Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layoutyourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_menu_lj, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Learn Java");

        //button.setOnClickListener(new View.OnClickListener() {
        Button button = view.findViewById(R.id.jbas);
        button.setOnClickListener(this);
        Button var = view.findViewById(R.id.jvar);
        var.setOnClickListener(this);
        Button bas = view.findViewById(R.id.jop);
        bas.setOnClickListener(this);
        Button dec = view.findViewById(R.id.jdec);
        dec.setOnClickListener(this);
        Button loops = view.findViewById(R.id.jloops);
        loops.setOnClickListener(this);
        Button num = view.findViewById(R.id.jnum);
        num.setOnClickListener(this);
        Button carr = view.findViewById(R.id.jarr);
        carr.setOnClickListener(this);
        Button met = view.findViewById(R.id.jmet);
        met.setOnClickListener(this);
        Button str = view.findViewById(R.id.jstr);
        str.setOnClickListener(this);
        Button excp = view.findViewById(R.id.jExp);
        excp.setOnClickListener(this);
        Button cao = view.findViewById(R.id.jCao);
        cao.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.jbas:
                Intent a = new Intent(getActivity(),J_bas_syn.class);
                startActivity(a);
                break;

            case R.id.jvar:
                Intent b = new Intent(getActivity(),J_var.class);
                startActivity(b);
                break;

            case R.id.jop:
                Intent c = new Intent(getActivity(),J_op.class);
                startActivity(c);
                break;

            case R.id.jdec:
                Intent d = new Intent(getActivity(),J_dec_mak.class);
                startActivity(d);
                break;

            case R.id.jloops:
                Intent e = new Intent(getActivity(),J_loops.class);
                startActivity(e);
                break;

            case R.id.jnum:
                Intent f = new Intent(getActivity(),J_numbers.class);
                startActivity(f);
                break;

            case R.id.jmet:
                Intent g = new Intent(getActivity(),J_methods.class);
                startActivity(g);
                break;

            case R.id.jstr:
                Intent h = new Intent(getActivity(),J_string.class);
                startActivity(h);
                break;

            case R.id.jarr:
                Intent i = new Intent(getActivity(),J_array.class);
                startActivity(i);
                break;

            case R.id.jExp:
                Intent j = new Intent(getActivity(),J_exp.class);
                startActivity(j);
                break;

            case R.id.jCao:
                Intent k = new Intent(getActivity(),J_cao.class);
                startActivity(k);
                break;

        }
    }

}
