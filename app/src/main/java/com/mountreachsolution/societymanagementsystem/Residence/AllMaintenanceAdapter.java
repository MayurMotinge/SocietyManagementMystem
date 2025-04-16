package com.mountreachsolution.societymanagementsystem.Residence;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mountreachsolution.societymanagementsystem.R;

import java.util.List;

public class AllMaintenanceAdapter extends BaseAdapter {

    List<pojo_All_Maintenance> list;
    Activity activity;
    TextView tv_no_record;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public AllMaintenanceAdapter(List<pojo_All_Maintenance> list, Activity activity, TextView tv_no_record) {
        this.list = list;
        this.activity = activity;
        this.tv_no_record = tv_no_record;

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        final AllMaintenanceAdapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (v == null) {
            holder = new AllMaintenanceAdapter.ViewHolder();
            v = inflater.inflate(R.layout.list_all_maintenance, null);
            holder.tv_date = v.findViewById(R.id.tv_all_maintenance_date);
            holder.tv_title = v.findViewById(R.id.tv_all_maintenance_title);
            holder.tv_cost = v.findViewById(R.id.tv_all_maintenance_cost);
            holder.tv_description = v.findViewById(R.id.tv_all_maintenance_description);

            v.setTag(holder);
        } else {
            holder = (AllMaintenanceAdapter.ViewHolder) v.getTag();
        }

        final pojo_All_Maintenance obj = list.get(position);
        holder.tv_date.setText(obj.getDate());
        holder.tv_title.setText(obj.getTitle());
        holder.tv_cost.setText(obj.getCost());
        holder.tv_description.setText(obj.getDescription());

        return v;
    }


    class ViewHolder {
        TextView tv_date, tv_title, tv_cost, tv_wing, tv_description;
    }
}