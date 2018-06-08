package com.example.schen.camera;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraCaptureSession;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.schen.camera.Adapter.EasyAdapter;
import com.example.schen.camera.Adapter.MyData;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    EasyAdapter mAdapter;
    List<MyData> mDataList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        mListView = findViewById(R.id.listView1);

        try {
            generateData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".png",storageDir);
        //int numOfFiles = (int) storageDir.listFiles().length;

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
//        updateDataSet();
        return image;

    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent passpictureintent = new Intent(MainActivity.this,testactivity.class);
        startActivity(passpictureintent);

    }

    public void generateData() throws IOException {
        File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        int numOfFiles = storageDir1.listFiles().length - 1;
        File[] LoF = storageDir1.listFiles();
        testactivity.ReadFile rf = new testactivity.ReadFile();
        String filename ="/storage/emulated/0/Android/data/com.example.schen.camera/files/Pictures/titledescp.txt";
        String listtitlename[]=rf.readLines(filename);


            for (int i = 0;i<numOfFiles;i++){
                String name = LoF[i].getAbsolutePath();
                String titlename = listtitlename[i];
                mDataList.add(new MyData("",name,titlename));
            }

        mAdapter = new EasyAdapter(this, mDataList);
        //Set the adapter with MyData list to the listView;
        mListView.setAdapter(mAdapter);
    }

    private void updateDataSet() {
        //Create MyData objects.
        File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        int numOfFiles = storageDir1.listFiles().length;
        File[] LoF = storageDir1.listFiles();

//        String titlemessage = getIntent().getStringExtra("messagekey1");
//        String descpmessage = getIntent().getStringExtra("messagekey2");

        for (int i = numOfFiles - 1;i < numOfFiles;i++){
            String name = LoF[i].getAbsolutePath();

            testactivity.ReadFile rf = new testactivity.ReadFile();
            String filename ="/storage/emulated/0/Android/data/com.example.schen.camera/files/Pictures/titledescp.txt";

            /*try
            {
                List<String> lines = Arrays.asList(rf.readLines(filename));
                String titleofimage= lines.get(i);
                mDataList.add(new MyData("",name,""));
            }
            catch(IOException e)
            {
                // Print out the exception that occurred
                System.out.println("Unable to create "+filename+": "+e.getMessage());
            }*/



            mDataList.add(new MyData("",name,""));
        }

        mAdapter = new EasyAdapter(this, mDataList);
        //Set the adapter with MyData list to the listView;
        mListView.setAdapter(mAdapter);
    }
}
