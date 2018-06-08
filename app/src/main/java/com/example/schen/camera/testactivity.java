package com.example.schen.camera;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class testactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testactivity);
        final EditText nametext;
        final EditText descptext;
        Button testbtn;
        Button readbtn;
        readbtn = findViewById(R.id.readbutton);
        ImageView iv1 = findViewById(R.id.annotateimage);
        testbtn = findViewById(R.id.savebutton);

            descptext = findViewById(R.id.descriptext);
            nametext = findViewById(R.id.titletext);

        final File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        int numOfFiles = storageDir1.listFiles().length;
        File[] LoF = storageDir1.listFiles();

        File dir = new File(String.valueOf(storageDir1));
        dir.mkdirs();

        readbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadFile rf = new ReadFile();
                String filename ="/storage/emulated/0/Android/data/com.example.schen.camera/files/Pictures/titledescp.txt";

                try
                {
                    String[] lines = rf.readLines(filename);

                    for (String line : lines)
                    {
                        System.out.println(line);
                    }
                }
                catch(IOException e)
                {
                    // Print out the exception that occurred
                    System.out.println("Unable to create "+filename+": "+e.getMessage());
                }

            }
        });

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(testactivity.this,MainActivity.class);
                String titlemessage = nametext.getText().toString();
                String descpmessage = descptext.getText().toString();
                File file = new File(storageDir1+"/titledescp.txt");
                String [] saveText = String.valueOf(titlemessage).split(System.getProperty("line.separator"));

                nametext.setText("");
                descptext.setText("");

                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();

                Save (file,saveText);

                intent.putExtra("messagekey1",titlemessage);
                intent.putExtra("messagekey2",descpmessage);

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


