package com.example.schen.camera;

import android.content.Intent;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class testactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testactivity);
        final EditText nametext;
        final EditText descptext;
        Button testbtn;
        ImageView iv1 =(ImageView)findViewById(R.id.annotateimage);
        testbtn = (Button)findViewById(R.id.savebutton);

            descptext = (EditText)findViewById(R.id.descriptext);
            nametext = (EditText)findViewById(R.id.titletext);

        final File storageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        int numOfFiles = storageDir1.listFiles().length;
        File[] LoF = storageDir1.listFiles();

        File dir = new File(String.valueOf(storageDir1));
        dir.mkdirs();

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(testactivity.this,MainActivity.class);
                String titlemessage = nametext.getText().toString();
                String descpmessage = descptext.getText().toString();
                File file = new File(storageDir1+"/titledescp.txt");
                String [] saveText = String.valueOf(titlemessage).split(System.getProperty("line.separator"));

                nametext.setText("");
                descptext.setText("");

                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();

                Save (file,saveText);
//                intent.putExtra("messagekey1",titlemessage);
//                intent.putExtra("messagekey2",descpmessage);
//                startActivity(intent);

            }

            public void load(View view){

                File file = new File (storageDir1 +"/titledescp.txt");
                //String [] loadtext=Load(file);
            }


            public void Save(File file, String[] data)
            {
                FileOutputStream fos = null;
                try
                {
                    fos = new FileOutputStream(file);
                }
                catch (FileNotFoundException e) {e.printStackTrace();}
                try
                {
                    try
                    {
                        for (int i = 0; i<data.length; i++)
                        {
                            fos.write(data[i].getBytes());
                            if (i < data.length-1)
                            {
                                fos.write("\n".getBytes());
                            }
                        }
                    }
                    catch (IOException e) {e.printStackTrace();}
                }
                finally
                {
                    try
                    {
                        fos.close();
                    }
                    catch (IOException e) {e.printStackTrace();}
                }
            }

            /*public String[] Load(File file)
            {
                FileInputStream fis = null;
                try
                {
                    fis = new FileInputStream(file);
                }
                catch (FileNotFoundException e) {e.printStackTrace();}
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);

                String test;
                int anzahl=0;
                try
                {
                    while ((test=br.readLine()) != null)
                    {
                        anzahl++;
                    }
                }
                catch (IOException e) {e.printStackTrace();}

                try
                {
                    fis.getChannel().position(0);
                }
                catch (IOException e) {e.printStackTrace();}

                String[] array = new String[anzahl];

                String line;
                int i = 0;
                try
                {
                    while((line=br.readLine())!=null)
                    {
                        array[i] = line;
                        i++;
                    }
                }
                catch (IOException e) {e.printStackTrace();}
                return array;
            }*/



        });


    }

}
