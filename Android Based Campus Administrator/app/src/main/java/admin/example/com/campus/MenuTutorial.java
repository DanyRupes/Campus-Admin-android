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


public class MenuTutorial extends Fragment implements View.OnClickListener {
    Fragment fragment = null;

    //Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file

        final View view = inflater.inflate(R.layout.fragment_menu_tutorial, container, false);
        //context = view.getContext();

        Button buttonsp = view.findViewById(R.id.button);
        buttonsp.setOnClickListener(this);
        Button buttonc = view.findViewById(R.id.buttonC);
        buttonc.setOnClickListener(this);
        Button buttoncp = view.findViewById(R.id.buttonCP);
        buttoncp.setOnClickListener(this);
        Button buttonp = view.findViewById(R.id.buttonP);
        buttonp.setOnClickListener(this);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Tutorial");




    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.button:
                //Toast.makeText(getActivity(),"Button Clicked", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getActivity(),SampleContent.class);
                i.putExtra("Some", "Some thing");
                startActivity(i);
             /*
                MenuSample ms= new MenuSample();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, ms,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
                 */

                break;
            case R.id.buttonC:
                MenuLC lc= new MenuLC();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, lc,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.buttonCP:
                MenuLJ lcp= new MenuLJ();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, lcp,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.buttonP:
                MenuLP lp= new MenuLP();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, lp,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;


        }
    }
}
