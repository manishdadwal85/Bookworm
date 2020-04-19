package com.bookworm.bookworm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.HashMap;

public class Viewbook extends AppCompatActivity {


    private ViewPager imageviewpager;
    private ArrayList<desclist> arrayList=new ArrayList<>();
    private TextView bookname,bookdesc,bookprice, bookrent, booktenure, booklanguage, bookauthor, booktype, bookcategory, bookCond;
    private RelativeLayout progress;
    private ArrayList<String> imagearray=new ArrayList<>();
    private SpringDotsIndicator dotsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbook);
        Intent intes=getIntent();
        //String course=intes.getStringExtra("course");
        //String semester=intes.getStringExtra("semester");
        //String subject=intes.getStringExtra("subject");
        String id=intes.getStringExtra("id");
        imageviewpager=(ViewPager)findViewById(R.id.imageview);
        bookname=(TextView)findViewById(R.id.bookname);
        bookdesc=(TextView)findViewById(R.id.bookdescribe);
        bookprice=(TextView)findViewById(R.id.bookprice);
        bookprice=(TextView)findViewById(R.id.bookprice);
        bookrent=(TextView)findViewById(R.id.bookrent);
        booktenure=(TextView)findViewById(R.id.booktenure);
        booklanguage=(TextView)findViewById(R.id.booklanguage);
        bookauthor=(TextView)findViewById(R.id.bookauthor);
        booktype=(TextView)findViewById(R.id.booktype);
        bookcategory=(TextView)findViewById(R.id.bookcategory);
        bookCond=(TextView)findViewById(R.id.bookcondition);
        dotsIndicator = (SpringDotsIndicator) findViewById(R.id.dot_indicator);
        progress=(RelativeLayout)findViewById(R.id.progress);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        progress.setVisibility(View.VISIBLE);
        reference.child("Books").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if(dataSnapshot.exists())
                       {
                           Log.i("demo",""+dataSnapshot.child("name").getValue().toString());

                           HashMap<String,String> nmap=(HashMap<String, String>) dataSnapshot.getValue();

                           arrayList.add(new desclist(nmap.get("front_image"),nmap.get("back_image")
                           ,nmap.get("name"),nmap.get("description")
                           ,nmap.get("price"),nmap.get("author")
                                   ,nmap.get("category"),nmap.get("condition")
                                   ,nmap.get("format"),nmap.get("language")
                                    ,nmap.get("rent"),nmap.get("tenure")));
                       }
                       updateui();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void updateui()
    {
        Log.i("demo",""+arrayList.get(0).booknames);
        imagearray.add(arrayList.get(0).image1);
        imagearray.add(arrayList.get(0).image2);
        imageviewpager.setAdapter(new viewpagerdetails(Viewbook.this,imagearray));
        dotsIndicator.setViewPager(imageviewpager);
        bookname.setText(arrayList.get(0).booknames);
        bookdesc.setText("Description : "+arrayList.get(0).bookdesc);
        bookprice.setText("Price: "+arrayList.get(0).bookprice);
        bookrent.setText("Price: "+arrayList.get(0).rent);
        booktenure.setText("Price: "+arrayList.get(0).tenure);
        booklanguage.setText("Price: "+arrayList.get(0).language);
        bookauthor.setText("Price: "+arrayList.get(0).author);
        booktype.setText("Price: "+arrayList.get(0).format);
        bookcategory.setText("Price: "+arrayList.get(0).category);
        bookCond.setText("Price: "+arrayList.get(0).condition);
        progress.setVisibility(View.INVISIBLE);
    }
}
