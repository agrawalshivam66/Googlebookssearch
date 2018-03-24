package com.example.shivam_pc.googlebookssearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Shivam-PC on 19-01-2018.
 */

public class booksAdapter extends ArrayAdapter<books> {

    public booksAdapter(Context context, List<books> books_list){
        super(context,0,books_list);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate
                    (R.layout.list_item, parent, false);

        }

        books currentbook = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.name);
        String names1 = currentbook.getname();
        name.setText(names1);

        TextView author = (TextView) listItemView.findViewById(R.id.author);
        String authorname = currentbook.getauthor();
        author.setText(authorname);

        TextView lang = (TextView) listItemView.findViewById(R.id.language);
        String language = currentbook.getlanguage();
        lang.setText(language);

        TextView publ = (TextView) listItemView.findViewById(R.id.describe);
        String publisher = currentbook.getpublisher();
        publ.setText(publisher);

        ImageView img =(ImageView)listItemView.findViewById(R.id.image);

        Glide.with(getContext())
                .load(currentbook.getimage())
                .into(img);

        return listItemView;
    }

    }

