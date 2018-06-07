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
import com.example.schen.camera.Adapter.EasyAdapter;
import com.example.schen.camera.Adapter.MyData;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataSet();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });

        mListView = findViewById(R.id.listView1);

        generateData();
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

    private void listViewIntent(){
        File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        int numOfFiles = storageDir1.listFiles().length;
        File[] LoF = storageDir1.listFiles();
        String name = LoF[0].getAbsolutePath();
        //Bitmap bMap = BitmapFactory.decodeFile(String.valueOf(LoF));1
        ListView lv = findViewById(R.id.listView1);
        List<Bitmap> demoarray = new ArrayList<>();
        //for (int i = 0; i< numOfFiles; i++)

    }

    private void generateData() {
        //Create MyData objects.
        File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        int numOfFiles = storageDir1.listFiles().length;
        File[] LoF = storageDir1.listFiles();

        for (int i = 0;i<numOfFiles;i++){
            String name = LoF[i].getAbsolutePath();
            mDataList.add(new MyData("chicken",name,null));

        }

//        MyData apple = new MyData("Apple","storage/emulated/0/Android/data/com.example.schen.camera/files/Pictures/PNG_20180606_2920396161264260915.png" ,"I Love Apple");
//        MyData microsoft = new MyData("Microsoft", "https://pbs.twimg.com/profile_images/875416480547917824/R6wl9gWl_400x400.jpg", "Microsoft soft");
//        MyData google = new MyData("Google", "https://yt3.ggpht.com/a-/ACSszfH4rgI-WIVE6ZZqYZK-8oCZyEY_L8-FhvJarA=s900-mo-c-c0xffffffff-rj-k-no", "Google evil");
//
//        mDataList.add(apple);
//        mDataList.add(microsoft);
//        mDataList.add(google);

        mAdapter = new EasyAdapter(this, mDataList);
        //Set the adapter with MyData list to the listView;
        mListView.setAdapter(mAdapter);
       // File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    private void updateDataSet() {
        //Create a new MyData object and add to the listView
        MyData amazon = new MyData("Amazon", "https://pmcvariety.files.wordpress.com/2018/01/amazon-logo.jpg?w=1000&h=562&crop=1", "Amazon hehe");
        mDataList.add(amazon);
        //Update listView with new data list.
        mAdapter.updateData(mDataList);


    }
}