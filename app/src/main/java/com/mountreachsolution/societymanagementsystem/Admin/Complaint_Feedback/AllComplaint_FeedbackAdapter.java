package com.mountreachsolution.societymanagementsystem.Admin.Complaint_Feedback;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mountreachsolution.societymanagementsystem.Admin.Complaint_Feedback.AllComplaint_FeedbackAdapter;
import com.mountreachsolution.societymanagementsystem.Admin.Complaint_Feedback.pojo_All_Complaint_Feedback;
import com.mountreachsolution.societymanagementsystem.R;

import java.util.List;

public class AllComplaint_FeedbackAdapter extends BaseAdapter {

    List<pojo_All_Complaint_Feedback> list;
    Activity activity;
    TextView tv_no_record;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public AllComplaint_FeedbackAdapter(List<pojo_All_Complaint_Feedback> list, Activity activity, TextView tv_no_record) {
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

        final AllComplaint_FeedbackAdapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (v == null) {
            holder = new AllComplaint_FeedbackAdapter.ViewHolder();
            v = inflater.inflate(R.layout.list_all_complaint_feedback, null);

            holder.tv_name = v.findViewById(R.id.tv_all_complaint_feedback_name);
            holder.tv_wing = v.findViewById(R.id.tv_all_complaint_feedback_wing);
            holder.tv_flat_no = v.findViewById(R.id.tv_all_complaint_feedback_flat_no);
            holder.tv_type = v.findViewById(R.id.tv_all_complaint_feedback_type);
            holder.tv_complaint_feedback_description = v.findViewById(R.id.tv_all_complaint_feedback_description);

            v.setTag(holder);
        } else {
            holder = (AllComplaint_FeedbackAdapter.ViewHolder) v.getTag();
        }

        final pojo_All_Complaint_Feedback obj = list.get(position);
        holder.tv_name.setText(obj.getName());
        holder.tv_wing.setText(obj.getWing());
        holder.tv_flat_no.setText(obj.getFlat_no());
        holder.tv_type.setText(obj.getType());
        holder.tv_complaint_feedback_description.setText(obj.getComplaint_feedback());

        return v;
    }


    class ViewHolder {
        TextView tv_name, tv_wing, tv_flat_no, tv_type,tv_complaint_feedback_description;
    }
}