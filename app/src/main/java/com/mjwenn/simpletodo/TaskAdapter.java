package com.mjwenn.simpletodo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mjwenn.simpletodo.model.Todo;

import java.util.List;
import java.util.Locale;

/**
 * Created by Ermano
 * on 1/26/2018.
 */

public class TaskAdapter extends ArrayAdapter<Todo> {

    Context context;
    List<Todo> todos;
    LayoutInflater inflater;

    public TaskAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Todo> objects) {
        super(context, resource, objects);

        this.context = context;
        todos = objects;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_task, parent, false);

            holder.tvTask = (TextView) view.findViewById(R.id.tvtask_adapter);
            holder.tvDueDate = (TextView) view.findViewById(R.id.tvduedate_adapter);
            holder.tvPriority = (TextView) view.findViewById(R.id.tvpriority_adapter);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Todo todo = todos.get(position);

        holder.tvTask.setText(todo.getTask());
        holder.tvDueDate.setText(String.format(Locale.US, "Due date : %s", todo.getDueDate()));
        try {
            holder.tvPriority.setText(context.getResources().getStringArray(R.array.priority_level)[todo.getPrority()]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    private static class ViewHolder {
        public TextView tvTask;
        public TextView tvDueDate;
        public TextView tvPriority;
    }
}
