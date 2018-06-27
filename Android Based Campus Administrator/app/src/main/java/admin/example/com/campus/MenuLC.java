package admin.example.com.campus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import admin.example.com.c.C_array;
import admin.example.com.c.C_bas_syn;
import admin.example.com.c.C_data_types;
import admin.example.com.c.C_dec_mak;
import admin.example.com.c.C_error_hand;
import admin.example.com.c.C_func;
import admin.example.com.c.C_loops;
import admin.example.com.c.C_op;
import admin.example.com.c.C_pointers;
import admin.example.com.c.C_strings;
import admin.example.com.c.C_struct_union;
import admin.example.com.nav.R;

public class MenuLC extends Fragment implements View.OnClickListener{

    //final Context context = this;
    private Button button;
    //final Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layoutyourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_menu_lc, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Learn C ");

        //button.setOnClickListener(new View.OnClickListener() {
        Button button = view.findViewById(R.id.cbas);
        button.setOnClickListener(this);
        Button var = view.findViewById(R.id.cvar);
        var.setOnClickListener(this);
        Button bas = view.findViewById(R.id.cop);
        bas.setOnClickListener(this);
        Button dec = view.findViewById(R.id.cdec);
        dec.setOnClickListener(this);
        Button loops = view.findViewById(R.id.cloops);
        loops.setOnClickListener(this);
        Button fun = view.findViewById(R.id.cfun);
        fun.setOnClickListener(this);
        Button carr = view.findViewById(R.id.carr);
        carr.setOnClickListener(this);
        Button poin = view.findViewById(R.id.cpoin);
        poin.setOnClickListener(this);
        Button str = view.findViewById(R.id.cstr);
        str.setOnClickListener(this);
        Button excp = view.findViewById(R.id.cExp);
        excp.setOnClickListener(this);
        Button struct = view.findViewById(R.id.csruct);
        struct.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.cbas:
                Intent a = new Intent(getActivity(),C_bas_syn.class);
                startActivity(a);
                break;

            case R.id.cvar:
                Intent b = new Intent(getActivity(),C_data_types.class);
                startActivity(b);
                break;

            case R.id.cop:
                Intent c = new Intent(getActivity(),C_op.class);
                startActivity(c);
                break;

            case R.id.cdec:
                Intent d = new Intent(getActivity(),C_dec_mak.class);
                startActivity(d);
                break;

            case R.id.cloops:
                Intent e = new Intent(getActivity(),C_loops.class);
                startActivity(e);
                break;

            case R.id.cfun:
                Intent f = new Intent(getActivity(),C_func.class);
                startActivity(f);
                break;

            case R.id.cpoin:
                Intent g = new Intent(getActivity(),C_pointers.class);
                startActivity(g);
                break;

            case R.id.cstr:
                Intent h = new Intent(getActivity(),C_strings.class);
                startActivity(h);
                break;

            case R.id.csruct:
                Intent i = new Intent(getActivity(),C_struct_union.class);
                startActivity(i);
                break;

            case R.id.cExp:
                Intent j = new Intent(getActivity(),C_error_hand.class);
                startActivity(j);
                break;

            case R.id.carr:
                Intent k = new Intent(getActivity(),C_array.class);
                startActivity(k);
                break;

        }
    }

}
