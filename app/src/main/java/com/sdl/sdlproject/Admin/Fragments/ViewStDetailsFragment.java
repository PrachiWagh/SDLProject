package com.sdl.sdlproject.Admin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdl.sdlproject.Admin.Adapter.IssueBookAdapter;
import com.sdl.sdlproject.Admin.Adapter.ReservedBookAdapter;
import com.sdl.sdlproject.Model.Books;
import com.sdl.sdlproject.Model.MyBooksItem;
import com.sdl.sdlproject.R;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewStDetailsFragment extends Fragment {

    public RecyclerView issuerecycler, reserverecycler;
    public ArrayList<MyBooksItem> issuedItems = new ArrayList<>();
    public ArrayList<Books> reservedItems = new ArrayList<>();
    private TextInputLayout inputText;
    private MaterialButton searchbtn;
    private String userid = "";
    private FirebaseFirestore db;
    DocumentSnapshot curr_doc;
    public static ArrayList<String> resBooksId;
    IssueBookAdapter issueAdapter;
    ReservedBookAdapter reservedAdapter;
    int i = 0, j = 0;
    //    boolean issue = false, reserve = false;
    private LinearLayout layout;
    TextView latefeeTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_stdetails, container, false);
        db = FirebaseFirestore.getInstance();
        issuerecycler = view.findViewById(R.id.issued_books_recycler);
        reserverecycler = view.findViewById(R.id.reserved_books_recycler);
        inputText = view.findViewById(R.id.search_student);
        searchbtn = view.findViewById(R.id.search_student_btn);
        latefeeTV = view.findViewById(R.id.lateFeeTV);
        layout = view.findViewById(R.id.lateFeeLayout);


        reservedAdapter = new ReservedBookAdapter(reservedItems, getContext());
        issueAdapter = new IssueBookAdapter(issuedItems, getContext());
        issuerecycler.setHasFixedSize(true);
        reserverecycler.setHasFixedSize(true);
        issuerecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        reserverecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        reservedAdapter = new ReservedBookAdapter(reservedItems, getContext());
        issueAdapter = new IssueBookAdapter(issuedItems, getContext());
        issuerecycler.setAdapter(issueAdapter);
        reserverecycler.setAdapter(reservedAdapter);


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid = inputText.getEditText().getText().toString();

                getBooks(userid);
                Log.d("search", userid);

            }
        });


        return view;
    }

    private void getBooks(String userId) {
        reservedItems.removeAll(reservedItems);
        issuedItems.removeAll(issuedItems);

        DocumentReference ref = db.collection("Students").document(userId);

        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                DocumentSnapshot doc = documentSnapshot;
                curr_doc = doc;
                ArrayList<Map<String, String>> map1 = (ArrayList<Map<String, String>>) curr_doc.get("issuedBooks");
                layout.setVisibility(View.VISIBLE);
                db.document("Students/" + inputText.getEditText().getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String latefee = documentSnapshot.getDouble("lateFee").toString();
                        latefeeTV.setText(latefee);

                    }
                });

                db.collection("Books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            for (i = 0; i < map1.size(); i++) {
                                Map<String, String> map = map1.get(i);
                                String id = map.get("bookId").trim();


                                if (document.getId().equals(id)) {
                                    String title = (String) document.get("title");
                                    issuedItems.add(new MyBooksItem(title, map.get("issueDate"), map.get("returnDate")));
                                }

                            }
                        }


                    }
                });

                Log.d("search", map1.toString());

                resBooksId = (ArrayList<String>) curr_doc.get("reservedBooks");


                db.collection("Books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {

                            for (j = 0; j < resBooksId.size(); j++) {
                                String bookId = resBooksId.get(j);

                                if (doc.getId().equals(bookId)) {
                                    Log.d("searchreserved", "ID received");
                                    reservedItems.add(new Books((String) doc.get("title"), (String) doc.get("author"), (String) doc.get("category"), (String) doc.get("publication"), (String) doc.get("shelf"), ((Long) doc.get("totalCopy")).intValue(), ((Long) doc.get("availableCopy")).intValue()));
                                } else {
                                    Log.d("searchreservedID", doc.getId());

                                }

                                if (j == reservedItems.size() - 1) {
                                    issueAdapter.notifyDataSetChanged();
                                    reservedAdapter.notifyDataSetChanged();
                                    issuerecycler.setAdapter(issueAdapter);
                                    reserverecycler.setAdapter(reservedAdapter);

                                } else {
                                    Log.d("search", String.valueOf(j));
                                }

                            }

                        }

                    }
                });


            }
        });


    }


}
