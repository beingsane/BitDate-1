package com.nickpoxon.bitdate;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nickpoxon on 12/08/2017.
 */

public class CardAdapter extends ArrayAdapter<User> {
    CardAdapter(Context ctx, List<User> users){
        super(ctx, R.layout.card,R.id.name,users);
    }
    @Override
    public CardView getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        CardView v = (CardView)super.getView(position, convertView, parent);
        TextView nameView = (TextView)v.findViewById(R.id.name);
        nameView.setText(user.getDisplayName());
        ImageView imageView = (ImageView)v.findViewById(R.id.user_photo);
        Picasso.with(getContext()).load(user.getPictureURL()).into(imageView);
        return v;
    }
}
