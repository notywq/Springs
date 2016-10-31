package com.springs.springs.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.springs.springs.model.DetailedImage;
import com.springs.springs.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class NewsLetter extends Fragment {

    View v;
    ArrayList<DetailedImage> news = new ArrayList<>();
    LayoutInflater v2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.newsletter_fragment, container, false);
        // Inflate the layout for this fragment
        v2 = inflater;
        populateNews();
        populateListView();
        registerClickCallback();
        return v;

    }
    public void populateNews()
    {
        for(int i = 0; i < 10; i++) {
            news.add(new DetailedImage("Article "+ (i+1), "Baron Dimaranan", "This is article number "+(i+1)+"..", R.drawable.newsicon));

        }

    }


    private void populateListView() {
        ArrayAdapter<DetailedImage> adapter = new MyListAdapter();
        ListView list = (ListView)v.findViewById(R.id.newsView);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView)v.findViewById(R.id.newsView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                DetailedImage currDetailedImage = news.get(position);
                String message = "Article " + (position+1)
                        + " selected, Desc: " + currDetailedImage.getDescription();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<DetailedImage> {
        MyListAdapter() {
            super(getActivity(), R.layout.news_item_view, news);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = v2.inflate(R.layout.news_item_view, parent, false);
            }


            DetailedImage currentA = news.get(position);

            // Fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentA.getIcon());


            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtnewstitle);
            makeText.setText(currentA.getTitle());


            TextView yearText = (TextView) itemView.findViewById(R.id.item_txtauthor);
            yearText.setText(currentA.getAuthor());


            TextView condionText = (TextView) itemView.findViewById(R.id.item_txtdesc);
            condionText.setText(currentA.getDescription());

            return itemView;
        }
    }


}
