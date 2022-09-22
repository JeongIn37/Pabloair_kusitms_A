package com.example.pabloair_kusitms_a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SearchAdapter extends ArrayAdapter<SearchItem> {

    public SearchAdapter(Context context, int resource, List<SearchItem> SearchList) {
        super(context, resource, SearchList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SearchItem searchItem = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_item,parent,false);
        }

        TextView textView = convertView.findViewById(R.id.search_productName);
        ImageView imageView = convertView.findViewById(R.id.search_productPhoto);

        textView.setText(searchItem.getproductName());
        imageView.setImageResource(searchItem.getImage());

        return convertView;
    }
}
