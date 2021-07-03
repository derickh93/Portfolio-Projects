package edu.qc.seclass.glm;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ReminderAdapter extends ArrayAdapter<Reminder>{

    Context context;
    ArrayList<Reminder> list = new ArrayList<>();


    public ReminderAdapter(@NonNull Context context, int resource, ArrayList<Reminder> listItem) {
        super(context, resource, listItem);
        this.context = context;
        this.list = list;
        list.addAll(listItem);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        VersionHolder holder = new VersionHolder();

        if (convertView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_row,null);


            holder.checkBox = convertView.findViewById(R.id.check_box);
            holder.textView = convertView.findViewById(R.id.tv_name);

            holder.checkBox.setOnCheckedChangeListener((EditListActivity)context);

            convertView.setTag(holder);

        }else{
            holder = (VersionHolder) convertView.getTag();
        }

        Reminder versions  = list.get(position);
        holder.textView.setText(versions.getName());

        holder.checkBox.setTag(list);
        holder.checkBox.setChecked(list.get(position).getIsSelected());

        return convertView;
    }

    public static class VersionHolder{
        public CheckBox checkBox;
        public TextView textView;
    }
}