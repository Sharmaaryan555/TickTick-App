package com.example.ticktick;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.Toast;

import com.example.ticktick.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DataInsertActivity.class);
                intent.putExtra("type","add mode");
                startActivityForResult(intent,1);
            }
        });

        binding.Rv.setLayoutManager(new LinearLayoutManager(this));

        binding.Rv.setHasFixedSize(true);
        RvAdapter adapter = new RvAdapter();
        binding.Rv.setAdapter(adapter);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT) {
                    noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Note Deleted !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this,DataInsertActivity.class);
                    intent.putExtra("type","update");
                    intent.putExtra("title",adapter.getNote(viewHolder.getAdapterPosition()).getTitle());
                    intent.putExtra("disc",adapter.getNote(viewHolder.getAdapterPosition()).getDisc());
                    intent.putExtra("id",adapter.getNote(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent,2);
                    }
            }
        }).attachToRecyclerView(binding.Rv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==1){
            String title = data.getStringExtra("title");
            String disc = data.getStringExtra("disc");
            Note note = new Note(title,disc);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == 2 ){
            String title = data.getStringExtra("title");
            String disc = data.getStringExtra("disc");
            Note note = new Note(title,disc);
            note.setId(data.getIntExtra("id",0));
            noteViewModel.update(note);
            Toast.makeText(MainActivity.this, "Updating the Notes", Toast.LENGTH_SHORT).show();


        }
    }
}