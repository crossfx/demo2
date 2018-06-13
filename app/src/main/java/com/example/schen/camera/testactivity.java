package com.example.schen.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class testactivity extends AppCompatActivity {
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference mDatabase;


    int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testactivity);
        final EditText nametext;
        final EditText descptext;
        Button testbtn;
        Button readbtn;

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Photo details");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ImageView iv1 = findViewById(R.id.annotateimage);
        testbtn = findViewById(R.id.savebutton);


        descptext = findViewById(R.id.descriptiontextfinal);

        final File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        final File storageDir2 = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        final int numOfFiles = storageDir1.listFiles().length;
        final File[] LoF = storageDir1.listFiles();
        final File fileloc = LoF[numOfFiles-1];

        File dir = new File(String.valueOf(storageDir1));
        dir.mkdirs();

        iv1.setImageURI(Uri.fromFile(fileloc));



        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(testactivity.this,MainActivity.class);
                String titlemessage = descptext.getText().toString();
                HashMap<String, String> dataMap = new HashMap<String,String>();


                File file = new File(storageDir2+"/titledescp.txt");
                String [] saveText = String.valueOf(titlemessage).split(System.getProperty("line.separator"));

                descptext.setText("");

                    Uri filepath = Uri.fromFile(fileloc);
                    StorageReference uploadlocation = storageReference.child("images/"+filepath.getLastPathSegment());
                    String userID = FirebaseAuth.getInstance().getUid();
                    UploadTask uploadTask = uploadlocation.putFile(filepath);



                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(),"Not working boss",Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
                        }
                    });

                Save (file,saveText);

                dataMap.put("Description", (titlemessage));
                dataMap.put("Image URL", uploadlocation.toString());
                dataMap.put("UID",userID.toString());
                mDatabase.push().setValue(dataMap);

                startActivity(intent);

            }
                       public void Save(File file, String[] data)
            {
                OutputStream fos= null;
                try {
                    fos = new FileOutputStream(file , true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                OutputStreamWriter out = new OutputStreamWriter(fos);
                try {

                    out.write(data[0]);
                    out.write("\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        }

    public static class ReadFile
    {
        public String[] readLines(String filename) throws IOException
        {
            FileReader fileReader = new FileReader(filename);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> lines = new ArrayList<String>();
            String line = null;

            while ((line = bufferedReader.readLine()) != null)
            {
                lines.add(line);
            }

            bufferedReader.close();

            return lines.toArray(new String[lines.size()]);
        }
    }

    }


