package com.example.assiment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListAdapter extends ArrayAdapter {

    String[] name ;
    String[] address ,email;
    int[] image ;
    private Activity context;

    public ListAdapter(Activity context, String[] name , String[] address , int[] image,String[] email) {
        super(context, R.layout.activity_hotel_list, name);

        this.context = context;
        this.address = address;
        this.name = name;
        this.image = image;
        this.email = email;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        Holder holder = null;

        if (r == null){
            LayoutInflater inflater = context.getLayoutInflater();
            r = inflater.inflate(R.layout.iteam, null,true);
            holder = new Holder(r);
            r.setTag(holder);
        }else {
            holder = (Holder) r.getTag();
        }
         holder.imageViewh.setImageResource(image[position]);
        holder.nameh.setText(name[position]);
        holder.addressh.setText(address[position]);
        holder.emailh.setText(email[position]);

        return r;
    }

    class Holder{
        TextView nameh;
        TextView addressh;
        ImageView imageViewh;
        TextView emailh;

        Holder(View v){
            emailh = v.findViewById(R.id.email);
            nameh = v.findViewById(R.id.name);
            addressh = v.findViewById(R.id.address);
            imageViewh = v.findViewById(R.id.image1);
        }
    }
}
