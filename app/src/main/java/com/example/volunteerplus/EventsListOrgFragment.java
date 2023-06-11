package com.example.volunteerplus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventsListOrgFragment extends Fragment {
    View view;
    private ListView listView;
    private Button buttonToCreate;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private String eventKey = "Event";
    private DatabaseReference myDataBase;

    public EventsListOrgFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events_list_org, container, false);
        buttonToCreate = view.findViewById(R.id.buttonToCreate);
        buttonToCreate.setOnClickListener( view -> {
            Navigation.findNavController(view).navigate(R.id.action_eventsListOrgFragment_to_createNewEventFragment);
        });
        init();
        getDataFromDB();
        return view;
    }

    private void init() {
        listView = view.findViewById(R.id.eventsListView);
        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        myDataBase = FirebaseDatabase.getInstance().getReference(eventKey);
    }

    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Event event = ds.getValue(Event.class);
                    assert event != null;
                    listData.add(event.nameEvent);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myDataBase.addValueEventListener(vListener);
    }
}