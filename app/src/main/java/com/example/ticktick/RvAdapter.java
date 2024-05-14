package com.example.ticktick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticktick.databinding.EachnoteBinding;

public class RvAdapter extends ListAdapter<Note,RvAdapter.ViewHolder> {

    public RvAdapter(){
        super(CALLBACK);
    }
    private static  final DiffUtil.ItemCallback<Note> CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())&& oldItem.getDisc().equals(newItem.getDisc());
        }
    };
    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eachnote,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Note note = getItem(position);
        holder.binding.titlenote.setText(note.getTitle());
        holder.binding.dispNote.setText(note.getDisc());
    }

    public Note getNote(int position){
        return  getItem(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        EachnoteBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = EachnoteBinding.bind(itemView);
        }
    }
}
