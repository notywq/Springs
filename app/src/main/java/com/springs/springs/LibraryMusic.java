package com.springs.springs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.hoang8f.android.segmented.SegmentedGroup;


/**
 * A simple {@link Fragment} subclass.

 */
public class LibraryMusic extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    View v;
    ArrayList<DetailedImage> music = new ArrayList<>();
    LayoutInflater v2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.library_music_fragment, container, false);
        // Inflate the layout for this fragment
        v2 = inflater;
        populateMusic();

        TextView temp = (TextView)v.findViewById(R.id.txtalbumtitle);
        temp.setText(music.get(1).getAuthor());
        temp = (TextView)v.findViewById(R.id.txtalbumartist);
        temp.setText(music.get(1).getAuthor());

        populateListView();
        registerClickCallback();


        ((SegmentedGroup) v.findViewById(R.id.segmented3)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction ft;
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment f;
                switch (checkedId) {
                    case R.id.seg1:
                        Toast.makeText(getActivity(), "BOOKS", Toast.LENGTH_SHORT).show();
                        f = new Library();
                        ft.replace(R.id.tabcontent, f);
                        break;
                    case R.id.seg2:
                        Toast.makeText(getActivity(), "MUSIC", Toast.LENGTH_SHORT).show();
                        f = new LibraryMusic();
                        ft.replace(R.id.tabcontent,f);
                        break;

                }
                ft.commit();
            }
        });
        return v;

    }
    public void populateMusic()
    {

            music.add(new DetailedImage("Death by the Cowards", "Insurgency", "This is song number "+(music.size()+1)+"..", R.drawable.albumcover));
            music.add(new DetailedImage("Derpy Derpy Derpy", "Insurgency", "This is song number "+(music.size()+1)+"..", R.drawable.albumcover));
            music.add(new DetailedImage("What is Your Name", "Insurgency", "This is song number "+(music.size()+1)+"..", R.drawable.albumcover));
            music.add(new DetailedImage("Death by the Cowards", "Insurgency", "This is song number "+(music.size()+1)+"..", R.drawable.albumcover));
            music.add(new DetailedImage("Derpy Derpy Derpy", "Insurgency", "This is song number "+(music.size()+1)+"..", R.drawable.albumcover));
            music.add(new DetailedImage("What is Your Name", "Insurgency", "This is song number "+(music.size()+1)+"..", R.drawable.albumcover));
            music.add(new DetailedImage("Death by the Cowards", "Insurgency", "This is song number "+(music.size()+1)+"..", R.drawable.albumcover));
            music.add(new DetailedImage("Derpy Derpy Derpy", "Insurgency", "This is song number "+(music.size()+1)+"..", R.drawable.albumcover));
            music.add(new DetailedImage("What is Your Name", "Insurgency", "This is song number "+(music.size()+1)+"..", R.drawable.albumcover));

    }


    private void populateListView() {
        ArrayAdapter<DetailedImage> adapter = new MyListAdapter();
        ListView list = (ListView)v.findViewById(R.id.musiclistview);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView)v.findViewById(R.id.musiclistview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                DetailedImage currDetailedImage = music.get(position);
                String message = "Song " + (position+1)
                        + " selected, title: " + currDetailedImage.getTitle();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<DetailedImage> {
        public MyListAdapter() {
            super(getActivity(), R.layout.song_item_view, music);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = v2.inflate(R.layout.song_item_view, parent, false);
            }


            DetailedImage currentA = music.get(position);

            // Fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.albumart);
            imageView.setImageResource(currentA.getIcon());


            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtsong);
            makeText.setText(currentA.getTitle());


            TextView yearText = (TextView) itemView.findViewById(R.id.item_txtartist);
            yearText.setText(currentA.getAuthor());


            return itemView;
        }
    }


}


