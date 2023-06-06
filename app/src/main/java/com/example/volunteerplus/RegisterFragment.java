package com.example.volunteerplus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    View view;
    private EditText EmailAddress, Password, FirstName, LastName, BirthDay, InviteCode;
    private DatabaseReference myDataBase;
    private final String UserKey = "User";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        init();
        Button buttonToReg = view.findViewById(R.id.buttonToReg);
        buttonToReg.setOnClickListener(view -> {
            String id = myDataBase.getKey();
            String InEmail = EmailAddress.getText().toString();
            String InPassword = Password.getText().toString();
            String InFirstName = FirstName.getText().toString();
            String InLastName = LastName.getText().toString();
            String InBirthday = BirthDay.getText().toString();
            String InInviteCode = InviteCode.getText().toString() ;
            User newUser = new User(id,InEmail,InPassword,InFirstName,InLastName,InBirthday,InInviteCode);
            if(!TextUtils.isEmpty(InEmail) && !TextUtils.isEmpty(InPassword) && !TextUtils.isEmpty(InFirstName)
                    && !TextUtils.isEmpty(InLastName) && !TextUtils.isEmpty(InBirthday)
                    && !TextUtils.isEmpty(InInviteCode)){
                myDataBase.push().setValue(newUser);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, LogInFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
                Toast.makeText(this.getContext(), "Регестрация успешна", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this.getContext(), "Заполнены не все поля", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    private void init() {
        EmailAddress = view.findViewById(R.id.editTextTextEmailAddress);
        Password = view.findViewById(R.id.editTextTextPassword);
        FirstName = view.findViewById(R.id.firstNameEt);
        LastName = view.findViewById(R.id.lastNameEt);
        BirthDay = view.findViewById(R.id.birthdayEt);
        InviteCode = view.findViewById(R.id.inviteCodeEt);
        myDataBase = FirebaseDatabase.getInstance().getReference(UserKey);
    }
}




