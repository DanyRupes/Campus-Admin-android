package admin.example.com.campus;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import admin.example.com.nav.R;
import admin.example.com.pyt.BasicOp;
import admin.example.com.pyt.BasicSyntax;
import admin.example.com.pyt.ClsAndObj;
import admin.example.com.pyt.DecMak;
import admin.example.com.pyt.Dictionary;
import admin.example.com.pyt.ExcpHand;
import admin.example.com.pyt.Func;
import admin.example.com.pyt.Lists;
import admin.example.com.pyt.Loops;
import admin.example.com.pyt.Numbers;
import admin.example.com.pyt.Stringss;
import admin.example.com.pyt.Tuples;
import admin.example.com.pyt.VarTypes;

public class MenuLP extends Fragment implements  View.OnClickListener{

    //final Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layoutyourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_menu_lp, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Learn Python ");

        Button button = view.findViewById(R.id.bas);
        button.setOnClickListener(this);
        Button var = view.findViewById(R.id.var);
        var.setOnClickListener(this);
        Button bas = view.findViewById(R.id.op);
        bas.setOnClickListener(this);
        Button dec = view.findViewById(R.id.dec);
        dec.setOnClickListener(this);
        Button loops = view.findViewById(R.id.loops);
        loops.setOnClickListener(this);
        Button num = view.findViewById(R.id.num);
        num.setOnClickListener(this);
        Button str = view.findViewById(R.id.str);
        str.setOnClickListener(this);
        Button lst = view.findViewById(R.id.lst);
        lst.setOnClickListener(this);
        Button tup = view.findViewById(R.id.tup);
        tup.setOnClickListener(this);
        Button dict = view.findViewById(R.id.dic);
        dict.setOnClickListener(this);
        Button func = view.findViewById(R.id.fun);
        func.setOnClickListener(this);
        Button excp = view.findViewById(R.id.Exp);
        excp.setOnClickListener(this);
        Button cao = view.findViewById(R.id.Cao);
        cao.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bas:
                Intent a = new Intent(getActivity(),BasicSyntax.class);
                startActivity(a);
                break;

            case R.id.var:
                Intent b = new Intent(getActivity(),VarTypes.class);
                startActivity(b);
                break;

            case R.id.op:
                Intent c = new Intent(getActivity(),BasicOp.class);
                startActivity(c);
                break;

            case R.id.dec:
                Intent d = new Intent(getActivity(),DecMak.class);
                startActivity(d);
                break;

            case R.id.loops:
                Intent e = new Intent(getActivity(),Loops.class);
                startActivity(e);
                break;

            case R.id.num:
                Intent f = new Intent(getActivity(),Numbers.class);
                startActivity(f);
                break;

            case R.id.str:
                Intent g = new Intent(getActivity(),Stringss.class);
                startActivity(g);
                break;

            case R.id.lst:
                Intent h = new Intent(getActivity(),Lists.class);
                startActivity(h);
                break;

            case R.id.tup:
                Intent i = new Intent(getActivity(),Tuples.class);
                startActivity(i);
                break;

            case R.id.dic:
                Intent j = new Intent(getActivity(),Dictionary.class);
                startActivity(j);
                break;

            case R.id.fun:
                Intent k = new Intent(getActivity(),Func.class);
                startActivity(k);
                break;

            case R.id.Exp:
                Intent l = new Intent(getActivity(),ExcpHand.class);
                startActivity(l);
                break;

            case R.id.Cao:
                Intent m = new Intent(getActivity(),ClsAndObj.class);
                startActivity(m);
                break;



        }
    }
}
