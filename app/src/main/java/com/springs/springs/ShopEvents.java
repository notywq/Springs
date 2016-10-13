package com.springs.springs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import info.hoang8f.android.segmented.SegmentedGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopEvents extends Fragment {

    View v;
    public ShopEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.shop_events_fragment, container, false);

        GridView gridView = (GridView)v.findViewById(R.id.bookgridview);

        final ArrayList<DetailedImage> books = new ArrayList<>();
        for(int i = 0; i < 10; i+=2) {
            books.add(new DetailedImage("Book "+ (i+1), "Justin Pleyto", "This is book number "+(i+1)+"..", R.drawable.img1sample));
            books.add(new DetailedImage("Book "+ (i+2), "Baron Dim", "This is book number "+(i+2)+"..", R.drawable.img2sample));

        }


        final BooksAdapter booksAdapter = new BooksAdapter(getContext(), books,1);
        gridView.setAdapter(booksAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                DetailedImage book = books.get(position);


                // This tells the GridView to redraw itself
                // in turn calling your BooksAdapter's getView method again for each cell
                String message = "Book " + (position+1)
                        + " selected, title: " + book.getTitle();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                booksAdapter.notifyDataSetChanged();
            }
        });



        ((SegmentedGroup) v.findViewById(R.id.segmented2)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction ft;
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment f;
                switch (checkedId) {
                    case R.id.button21:
                        Toast.makeText(getActivity(), "BOOKS", Toast.LENGTH_SHORT).show();
                        f = new ShoppingCart();
                        ft.replace(R.id.tabcontent, f);
                        break;
                    case R.id.button22:
                        Toast.makeText(getActivity(), "MUSIC", Toast.LENGTH_SHORT).show();
                        f = new ShopMusic();
                        ft.replace(R.id.tabcontent,f);
                        break;
                    case R.id.button23:
                        Toast.makeText(getActivity(), "EVENTS", Toast.LENGTH_SHORT).show();
                        f = new ShopEvents();
                        ft.replace(R.id.tabcontent, f);
                        break;
                }
                ft.commit();
            }
        });
        return v;
    }

}
