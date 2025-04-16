package com.mountreachsolution.societymanagementsystem.Admin.Maintenance;

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

public class ViewMaintenanceOfficerAdapter extends BaseAdapter {

    List<pojo_Maintenance_Officer> list;
    Activity activity;
    TextView tv_no_record;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public ViewMaintenanceOfficerAdapter(List<pojo_Maintenance_Officer> list, Activity activity, TextView tv_no_record) {
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

        final ViewMaintenanceOfficerAdapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (v == null) {
            holder = new ViewMaintenanceOfficerAdapter.ViewHolder();
            v = inflater.inflate(R.layout.list_all_maintenence_officer, null);

            holder.tv_name = v.findViewById(R.id.tv_all_maintenence_officer_name);
            holder.tv_mobile_no = v.findViewById(R.id.tv_all_maintenence_officer_mobile_no);
            holder.tv_email_id = v.findViewById(R.id.tv_all_maintenence_officer_email_id);
            holder.tv_username = v.findViewById(R.id.tv_all_maintenence_officer_username);
            holder.tv_password = v.findViewById(R.id.tv_all_maintenence_officer_password);

            v.setTag(holder);
        } else {
            holder = (ViewMaintenanceOfficerAdapter.ViewHolder) v.getTag();
        }

        final pojo_Maintenance_Officer obj = list.get(position);
        holder.tv_name.setText(obj.getName());
        holder.tv_mobile_no.setText(obj.getMobileno());
        holder.tv_email_id.setText(obj.getEmail_id());
        holder.tv_username.setText(obj.getUsername());
        holder.tv_password.setText(obj.getPassword());

        return v;
    }


    class ViewHolder {
        TextView tv_name, tv_mobile_no, tv_email_id, tv_username,tv_password;
    }
}