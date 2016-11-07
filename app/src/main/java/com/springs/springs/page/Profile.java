package com.springs.springs.page;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.springs.springs.R;


public class Profile extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        //TextView name = (TextView)v.findViewById(R.id.pname);
        //name.setText("Jason Borne");

        Button b = (Button) v.findViewById (R.id.bplogout);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Log Out Pressed!", Toast.LENGTH_SHORT).show();


            }
        });

        return v;
    }

}
