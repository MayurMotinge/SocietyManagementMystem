package com.mountreachsolution.societymanagementsystem.Admin.Resident;

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

public class AllResidentAdapter extends BaseAdapter {

    List<pojo_All_Resident> list;
    Activity activity;
    TextView tv_no_record;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public AllResidentAdapter(List<pojo_All_Resident> list, Activity activity, TextView tv_no_record) {
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

        final AllResidentAdapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (v == null) {
            holder = new AllResidentAdapter.ViewHolder();
            v = inflater.inflate(R.layout.list_all_resident, null);

            holder.tv_name = v.findViewById(R.id.tv_all_resident_name);
            holder.tv_mobile_no = v.findViewById(R.id.tv_all_resident_mobile_no);
            holder.tv_email_id = v.findViewById(R.id.tv_all_resident_email_id);
            holder.tv_wing = v.findViewById(R.id.tv_all_resident_wing);
            holder.tv_flat_no = v.findViewById(R.id.tv_all_resident_flat_no);
            holder.tv_username = v.findViewById(R.id.tv_all_resident_username);

            v.setTag(holder);
        } else {
            holder = (AllResidentAdapter.ViewHolder) v.getTag();
        }

        final pojo_All_Resident obj = list.get(position);
        holder.tv_name.setText(obj.getName());
        holder.tv_mobile_no.setText(obj.getMobile_no());
        holder.tv_email_id.setText(obj.getEmail_is());
        holder.tv_wing.setText(obj.getWing());
        holder.tv_flat_no.setText(obj.getFlat_no());
        holder.tv_username.setText(obj.getUsername());

        return v;
    }


    class ViewHolder {
        TextView tv_name, tv_mobile_no, tv_email_id, tv_wing, tv_flat_no, tv_username;
    }
}