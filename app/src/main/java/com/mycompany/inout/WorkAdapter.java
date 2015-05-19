package com.mycompany.inout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;


public class WorkAdapter extends ArrayAdapter<ParseObject> {
    protected Context mContext;
    protected List<ParseObject> mWDate;

    public WorkAdapter(Context context, List<ParseObject> WDate){
        super(context, R.layout.work_custom_layout, WDate);
        mContext = context;
        mWDate = WDate;
    }
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.work_custom_layout, null);
            holder = new ViewHolder();
            holder.userDateWork = (TextView) convertView
                    .findViewById(R.id.dateF);
            holder.userHoursWork = (TextView) convertView
                    .findViewById(R.id.hoursF);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject clientObject = mWDate.get(position);

        String date = String.valueOf(clientObject.get("updatedAt"));
        holder.userDateWork.setText(date);

        String hours = String.valueOf(clientObject.get("duration"));
        holder.userHoursWork.setText(hours);

        return convertView;
    }
    public static class ViewHolder{
        TextView userDateWork;
        TextView userHoursWork;
    }
}
