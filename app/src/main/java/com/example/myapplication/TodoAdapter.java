package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<HomeActivity.Task> todoList;

    public TodoAdapter(List<HomeActivity.Task> todoList) {
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        HomeActivity.Task task = todoList.get(position);
        holder.tvTodoTitle.setText(task.getTitle());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTodoTitle;  // Changed from tvTodoItem to tvTodoTitle

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTodoTitle = itemView.findViewById(R.id.tvTodoTitle);
        }
    }
}