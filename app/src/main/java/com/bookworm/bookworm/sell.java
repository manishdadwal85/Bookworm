package com.bookworm.bookworm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class sell extends AppCompatActivity {

    private Button frontiamge,backimage,postad;
    private SharedPreferences sharedPreferences;
    private String categorySel,formatSel,tenureSel,languageSel,imageid1,imageid2;
    private CharSequence[] options={"Take Photo","Gallery","Close"};
    private Integer REQUEST_CAMERA = 101, SELECT_FILE = 102;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,

    };
    private boolean f,f2;
    private RelativeLayout  progresssell;


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        sharedPreferences = getSharedPreferences("bookworm.com", MODE_PRIVATE);
        frontiamge=(Button)findViewById(R.id.frontimage);
        backimage=(Button)findViewById(R.id.backimage);
        postad=(Button)findViewById(R.id.postad);
        final EditText book_name=(EditText)findViewById(R.id.book_name);
        final EditText book_author=(EditText)findViewById(R.id.book_author);
        final EditText prices=(EditText)findViewById(R.id.selling_price);
        final EditText rent=(EditText)findViewById(R.id.rent);
        final EditText tenureV=(EditText)findViewById(R.id.tenure);
        final EditText desc=(EditText)findViewById(R.id.book_desc);
        final RadioButton radioold=(RadioButton) findViewById(R.id.radioold);
        final RadioButton radionew=(RadioButton) findViewById(R.id.radionew);
        final Spinner spinnercategory=(Spinner)findViewById(R.id.spinnercategory);
        final Spinner spinnerformat=(Spinner)findViewById(R.id.spinnerformat);
        final Spinner spinnerlanguage=(Spinner)findViewById(R.id.spinnerlanguage);
        final Spinner spinnertenure=(Spinner)findViewById(R.id.spinnertenure);
        progresssell=(RelativeLayout)findViewById(R.id.progressell);

        ////////////bcom list
        final String[] category = new String[]{"Action & Anventure", "Arts, Film, Photography", "Biography", "Business & Economy", "Comics","Computing","Home","Thriller & Mystery", "Exam Perepration","Fantesy","Health","History","Kids", "Language", "Law", "Lifestyle", "Politics","Romance","Sports", "Textbook","Travel"};
        final ArrayAdapter<String> adapter_cat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, category);
        spinnercategory.setAdapter(adapter_cat);

        final String[] format = new String[]{"Audiobook","Board Book", "Hardcover","Loose Leaf","Paperback"};
        final ArrayAdapter<String> adapter_format = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, format);
        spinnerformat.setAdapter(adapter_format);

        final String[] language = new String[]{"English","Hindi", "Marathi","Tamil","Telugu","Others"};
        final ArrayAdapter<String> adapter_lang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, language);
        spinnerlanguage.setAdapter(adapter_lang);

        final String[] tenure = new String[]{"Days","Weeks","Months"};
        final ArrayAdapter<String> adapter_tenure = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tenure);
        spinnertenure.setAdapter(adapter_tenure);

        postad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!book_name.getText().toString().equals("") && !categorySel.equals("") && !formatSel.equals("") && !languageSel.equals(""))
                {
                    progresssell.setVisibility(View.VISIBLE);
//                    Log.i("demo subjectid",subjectid);
//                    Log.i("demo subject",subjects);
//                    Log.i("demo course",coursess);
//                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
//                            .child("Myad").child(subjectid).child("name").setValue(book_name.getText().toString());
//                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
//                            .child("Myad").child(subjectid).child("price").setValue(prices.getText().toString());
//                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
//                            .child("Myad").child(subjectid).child("course").setValue(coursess);
//                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
//                            .child("Myad").child(subjectid).child("semester").setValue(semesters);
//                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
//                            .child("Myad").child(subjectid).child("subjects").setValue(subjects);
                    String bookId = FirebaseDatabase.getInstance().getReference().child("Books").push().getKey();
                    FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("user_id").setValue(sharedPreferences.getString("handle",""));
                    FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("name").setValue(book_name.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("author").setValue(book_author.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("price").setValue(prices.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("rent").setValue(rent.getText().toString());
                    String tenureStr = tenureV.getText().toString() + "-" + tenureSel;
                    FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("tenure").setValue(tenureStr);
                    FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("description").setValue(desc.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("category").setValue(categorySel);
                    FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("format").setValue(formatSel);
                    FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("language").setValue(languageSel);
                    String freetext = book_name.getText().toString();
                    if(radionew.isSelected()){
                        FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("condition").setValue(getResources().getString(R.string.nnew));
                    }else{
                        FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("condition").setValue(getResources().getString(R.string.old));
                    }


                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
                            .child("Myad").child("bookid").setValue(bookId);

                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
                            .child("Myad").child("date").setValue(new Date().toString());

                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
                            .child("Myad").child("status").setValue(getResources().getString(R.string.active));

                    Toast.makeText(getApplicationContext(),"Ad Published",Toast.LENGTH_LONG).show();
                    progresssell.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getApplicationContext(),landingpage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid Feilds or Empty Feilds.",Toast.LENGTH_LONG).show();
                }

            }
        });

        spinnercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySel=parent.getItemAtPosition(position).toString();
//                Log.i("Course",coursess);
//                if(coursess.equals("Bachelor of Commerce") || coursess.equals("Bachelor of Science"))
//                {
//                    spinnersemester.setAdapter(adapterss);
//                }else
//                {
//                    spinnersemester.setAdapter(adapters);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categorySel="";
            }
        });

        spinnerformat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                formatSel=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                formatSel="";
            }
        });
        spinnerlanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                languageSel=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                languageSel="";
            }
        });

        spinnertenure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenureSel=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tenureSel="";
            }
        });

        frontiamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=true;
                Log.i("f status", ""+f);
                final AlertDialog.Builder builder = new AlertDialog.Builder(sell.this);
                builder.setTitle("Select Options").setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(options[i].equals("Take Photo"))
                        {
                            if (!hasPermissions(sell.this)) {
                                ActivityCompat.requestPermissions(sell.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                openCamera();
                            }
                        }
                        else if(options[i].equals("Gallery"))
                        {
                            if (!hasPermissions(sell.this)) {
                                ActivityCompat.requestPermissions(sell.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                gallerys();
                            }
                        }
                        else if(options[i].equals(""))
                        {
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f2=true;
                final AlertDialog.Builder builder = new AlertDialog.Builder(sell.this);
                builder.setTitle("Select Options").setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(options[i].equals("Take Photo"))
                        {
                            if (!hasPermissions(sell.this)) {
                                ActivityCompat.requestPermissions(sell.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                openCamera();
                            }
                        }
                        else if(options[i].equals("Gallery"))
                        {
                            if (!hasPermissions(sell.this)) {
                                ActivityCompat.requestPermissions(sell.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                gallerys();
                            }
                        }
                        else if(options[i].equals(""))
                        {
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                progresssell.setVisibility(View.VISIBLE);
                uploadfront(bitmap);
            }
        } else if (requestCode == SELECT_FILE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imagedata = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(sell.this.getContentResolver(), imagedata);
                    uploadfront(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void gallerys() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select image"), SELECT_FILE);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0 && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_CAMERA)
                openCamera();
            else if (requestCode == SELECT_FILE)
                gallerys();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(sell.this);
            builder.setMessage("Permission not granted").setTitle("Permission Error");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void uploadfront(Bitmap bitmap1) {
        String date=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference refrence = storage.getReferenceFromUrl("gs://bookworm-25e0d.appspot.com");
        final StorageReference storageReference = refrence.child(md5(date));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = storageReference.putBytes(data);
        Log.i("upload", "uploads started1");
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Log.i("image", task.getResult().toString()+ "status"+f);
                            imageid1=task.getResult().toString();
                            if(f)
                            {
                                FirebaseDatabase.getInstance().getReference().child("Books").child("image2").setValue(imageid1);
                                f=false;
                                progresssell.setVisibility(View.INVISIBLE);
                            }
                            if(f2)
                            {

                                FirebaseDatabase.getInstance().getReference().child("Books").child("image").setValue(imageid1);
                                Log.i("image logged", task.getResult().toString());
                                f2=false;
                                progresssell.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
            }
        });
    }
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
