package com.example.volunteerplus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {
    View view;
    private EditText EmailAddress, Password, FirstName, LastName, BirthDay, InviteCode;
    private DatabaseReference myDataBase;
    private final String UserKey = "User";
    private FirebaseAuth mAuth;

    public RegisterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        init();
        mAuth = FirebaseAuth.getInstance();
        Button buttonToReg = view.findViewById(R.id.buttonToReg);
        buttonToReg.setOnClickListener( view -> {
            String id = myDataBase.getKey();
            String email = EmailAddress.getText().toString();
            String password = Password.getText().toString();
            String firstName = FirstName.getText().toString();
            String lastName = LastName.getText().toString();
            String birthday = BirthDay.getText().toString();
            String inviteCode = InviteCode.getText().toString();

            User newUser = new User(id, email, password, firstName, lastName, birthday, inviteCode);

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(firstName)
                    && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(birthday)
                    && !TextUtils.isEmpty(inviteCode)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("RegisterFragment", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_logInFragment);
                                    Toast.makeText(requireContext(), "Регестрация успешна", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("RegisterFragment", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(requireContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                myDataBase.push().setValue(newUser);

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




