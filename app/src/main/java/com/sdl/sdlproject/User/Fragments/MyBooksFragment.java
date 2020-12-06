package com.sdl.sdlproject.User.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sdl.sdlproject.Model.MyBooksItem;
import com.sdl.sdlproject.R;
import com.sdl.sdlproject.User.Adapter.MyBookAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static com.sdl.sdlproject.LoginActivity.curr_doc;
import static com.sdl.sdlproject.LoginActivity.myBooksItems;

public class MyBooksFragment extends Fragment {
    RecyclerView recyclerView1;
    TextView lateFee;
    public static MyBookAdapter myBooksAdapter;
    public static double late_fee;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Calendar cobj=Calendar.getInstance(TimeZone.getTimeZone("IST"));
        String date_today=cobj.get(Calendar.YEAR)+"-"+(cobj.get(Calendar.MONTH)+1)+"-"+cobj.get(Calendar.DATE);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_books, container, false);
        recyclerView1 = view.findViewById(R.id.recyclerView_myBooks);
        lateFee=view.findViewById(R.id.lateFee);
        //  recyclerView.setHasFixedSize(true);
        Log.d("today",date_today);
        if (!(myBooksItems.size() < 1)) {
            late_fee=0;
            for(MyBooksItem mybook:myBooksItems){
                try {
                    Date today= df.parse(date_today);
                    Log.d("today",today.toString());

                    Date ret=df.parse(mybook.getReturnDate());
                    Log.d("ret",ret.toString());
                    if(ret.before(today)){
                        Log.d("yes","yes");
                        long difference_In_Time
                                = today.getTime() - ret.getTime();
                        long difference_In_Days
                                = TimeUnit.MILLISECONDS.toDays(difference_In_Time);
                        Log.d("diff",String.valueOf(difference_In_Days));
                        late_fee=late_fee+ ((double) difference_In_Days)*10;

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Log.d("iddddddddd",curr_doc.getId());
            FirebaseFirestore db=FirebaseFirestore.getInstance();
            DocumentReference ref = db.document("Students/"+curr_doc.getId());

            Map<String, Object> map = new HashMap<>();
            map.put("lateFee",late_fee);

            ref.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });


          /*  db.collection("Students").document(curr_doc.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();

                    }
                }
            });*/

            lateFee.setText("Late Fee: ₹"+late_fee);
            myBooksAdapter= new MyBookAdapter(myBooksItems,getContext());
            recyclerView1.setAdapter(myBooksAdapter);
            recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        else {
            Toast.makeText(getContext(),"No Issued Books",Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}
