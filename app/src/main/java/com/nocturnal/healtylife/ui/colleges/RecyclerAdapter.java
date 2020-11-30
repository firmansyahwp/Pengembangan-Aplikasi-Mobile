package com.nocturnal.healtylife.ui.colleges;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nocturnal.healtylife.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<College> dataset;
    private FragmentActivity fragmentActivity;

    public RecyclerAdapter(List<College> dataset, FragmentActivity fragmentActivity) {
        this.dataset = dataset;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.colleges_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(dataset.get(position).getName());
        Glide.with(holder.logo.getContext()).load(dataset.get(position).getLogo()).into(holder.logo);

        holder.name.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataset.get(position).getWeb()));
            fragmentActivity.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView name;

        ViewHolder(View view) {
            super(view);
            logo = view.findViewById(R.id.colleges_logo);
            name = view.findViewById(R.id.colleges_name);
        }
    }
}
