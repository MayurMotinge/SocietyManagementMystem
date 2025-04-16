package com.mountreachsolution.societymanagementsystem.Admin.Notice;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mountreachsolution.societymanagementsystem.Admin.Notice.AllNoticeAdapter;
import com.mountreachsolution.societymanagementsystem.Admin.Notice.pojo_All_Notice;
import com.mountreachsolution.societymanagementsystem.R;

import java.util.List;

public class AllNoticeAdapter extends BaseAdapter {

    List<pojo_All_Notice> list;
    Activity activity;
    TextView tv_no_record;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public AllNoticeAdapter(List<pojo_All_Notice> list, Activity activity, TextView tv_no_record) {
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

        final AllNoticeAdapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (v == null) {
            holder = new AllNoticeAdapter.ViewHolder();
            v = inflater.inflate(R.layout.list_all_notice, null);

            holder.tv_date = v.findViewById(R.id.tv_all_notice_date);
            holder.tv_time = v.findViewById(R.id.tv_all_notice_time);
            holder.tv_title = v.findViewById(R.id.tv_all_notice_title);
            holder.tv_description = v.findViewById(R.id.tv_all_notice_description);

            v.setTag(holder);
        } else {
            holder = (AllNoticeAdapter.ViewHolder) v.getTag();
        }

        final pojo_All_Notice obj = list.get(position);
        holder.tv_date.setText(obj.getDate());
        holder.tv_time.setText(obj.getTime());
        holder.tv_title.setText(obj.getTitle());
        holder.tv_description.setText(obj.getDescription());


        return v;
    }


    class ViewHolder {
        TextView tv_date, tv_time, tv_title, tv_description;
    }
}