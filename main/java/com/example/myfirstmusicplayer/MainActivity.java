package com.example.myfirstmusicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //create the verifier code to permissions
    private static final int READ_EXTERNAL_STORAGE = 1;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //call check permission method which has created
        checkPermission();

        //

        //now set the recycler view as follows after we create the array list which load data of music
        ArrayList<AudioData> list = loadAudio(this);

        //now create the recycler view

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //the object in the adapter is the class which we first created
        recyclerView.setAdapter(new MusicListAdapter(list));
        //now add the play functionality

        button=findViewById(R.id.button);

    }
    // permission methods to read external storage
    //check permission
    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    //create the method to check permission at every start
    //override on result method


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE){
            //checking whether user granted the permission  or not
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }

    }
    //

    //load audio method
    private ArrayList<AudioData> loadAudio(Context context){

        ArrayList<AudioData> tmpList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection={
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATA
        };

        Cursor cursor=context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor!=null){
            while (cursor.moveToNext()){
                tmpList.add(new AudioData(
                   cursor.getString(0),
                   cursor.getString(1)
                ));
            }
            cursor.close();
        }
        return tmpList;
    }


    //implement the dependencies
    //create a class inside this class for the recyclerview adapter
    private class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MyViewHolder>{

        //create the arraylist which will hold the music data received

        //create a class where ew can store the music details from storage

        //provide that audiodata class inside Arraylist
        //implement the methods below
        //create the method to load all the audio files in the device storage

        private ArrayList<AudioData> list;

        public MusicListAdapter(ArrayList<AudioData> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.music_list_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.textView.setText(list.get(position).getName());
            holder.textView.setOnClickListener(e->{
                MediaPlayer player = MediaPlayer.create(e.getContext(),
                        Uri.parse(list.get(position).getPath()));
                //call start method to run the music
                player.start();
            });
            //back to the music list class
            button.setOnClickListener(e->{
                MediaPlayer player = MediaPlayer.create(e.getContext(),
                        Uri.parse(list.get(position).getPath()));
                //call start method to run the music
                player.start();
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        //create a class named view holder to hold the view

        protected class MyViewHolder extends RecyclerView.ViewHolder{
            //create button reference
                TextView textView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
            textView = itemView.findViewById(R.id.textView);
            }
        }
    }
}