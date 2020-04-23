package com.nabih.wikaya;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends ArrayAdapter {

    List list = new ArrayList();
    SharedPreferences sharedpreferences;
    private Context context;
    InternalFunction internalFunction = new InternalFunction();




    String string_place_id, string_location_type, string_place_name, string_section, string_residential_type,
            string_property_number;


    static class PlacesHolder {
        TextView tv_place_section;
        TextView tv_resid_prop;

        PlacesHolder() {
        }
    }

    public PlacesAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }


    public void add(Object object) {
        super.add(object);
        this.list.add(object);
    }

    public int getCount() {
        return this.list.size();
    }

    @Nullable
    public Object getItem(int position) {
        return this.list.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PlacesHolder placesHolder;
        View row = convertView;
        if (row == null) {
            row = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.places_row_layout, parent, false);
            placesHolder = new PlacesHolder();
            placesHolder.tv_place_section = (TextView) row.findViewById(R.id.tv_place_section);
            placesHolder.tv_resid_prop = (TextView) row.findViewById(R.id.tv_resi_prop);
             row.setTag(placesHolder);
        } else {
            placesHolder = (PlacesHolder) row.getTag();
        }


        Places places = (Places) getItem(position);




        string_place_id = places.get_place_id();
        string_location_type = places.get_location_type();
        string_place_name = places.get_place_name();
        string_section = places.get_section();
        string_residential_type = places.get_residential_type();
        string_property_number = places.get_property_number();




        if(string_section.equals("")){
            placesHolder.tv_place_section.setText(string_place_name );

        }else{
            placesHolder.tv_place_section.setText(string_place_name + " Section: " + string_section);

        }

        if(string_location_type.equals("Others")){
            placesHolder.tv_resid_prop.setVisibility(View.GONE);

        }else{
            placesHolder.tv_resid_prop.setText(string_residential_type + " " + string_property_number);

        }






        return row;
    }

    public void clearData() {
        // clear the data
        list.clear();

    }
}