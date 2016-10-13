package com.springs.springs;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BooksAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<DetailedImage> books;
    private final int mode;

    public int getMode() {
        return mode;
    }

    // 1
    public BooksAdapter(Context context, ArrayList<DetailedImage> books, int mode) {
        this.mContext = context;
        this.books = books;
        this.mode = mode;
    }

    // 2
    @Override
    public int getCount() {
        return books.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final DetailedImage book = books.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            if(mode == 1)
                convertView = layoutInflater.inflate(R.layout.book_grid_view, null);
            else if(mode == 2)
                convertView = layoutInflater.inflate(R.layout.book_solo, null);

        }

        // 3
        if(mode ==1) {
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview_cover_art);
            final TextView nameTextView = (TextView) convertView.findViewById(R.id.textview_book_name);
            final TextView authorTextView = (TextView) convertView.findViewById(R.id.textview_book_author);
            imageView.setImageResource(book.getIcon());
            nameTextView.setText(book.getTitle());
            authorTextView.setText(book.getAuthor());
        }
        else if(mode == 2)
        {
            final ImageView imageView = (ImageView)convertView.findViewById(R.id.ibook_solo);
            imageView.setImageResource(book.getIcon());
        }

        // 4


        return convertView;
    }

}