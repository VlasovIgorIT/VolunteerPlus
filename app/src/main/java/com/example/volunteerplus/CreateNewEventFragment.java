package com.example.volunteerplus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewEventFragment extends Fragment {
    View view;
    private EditText nameEvents, descriptionEvent, numberParticipants;
    private String eventKey = "Event";
    private DatabaseReference myDataBase;

    public CreateNewEventFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_new_event, container, false);
        init();
        Button buttonToSave;
        buttonToSave = view.findViewById(R.id.buttonToSave);
        buttonToSave.setOnClickListener( view -> {
            String id = myDataBase.getKey();
            String nameEvent  = nameEvents.getText().toString();
            String descriptionEvents  = descriptionEvent.getText().toString();
            String number = numberParticipants.getText().toString();
            Event newEvent = new Event(id, nameEvent, descriptionEvents, number);
            myDataBase.push().setValue(newEvent);
            Toast.makeText(this.getContext(), "Мероприятие создано", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.action_createNewEventFragment_to_eventsListOrgFragment);
        });
        return view;

    }

    private void init() {
        nameEvents = view.findViewById(R.id.edNameEvents);
        descriptionEvent = view.findViewById(R.id.edDescriptionEvent);
        numberParticipants = view.findViewById(R.id.edNumber);
        myDataBase = FirebaseDatabase.getInstance().getReference(eventKey);
    }
}
