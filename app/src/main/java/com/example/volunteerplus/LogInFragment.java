package com.example.volunteerplus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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


public class LogInFragment extends Fragment {
    View view;
    private FirebaseAuth mAuth;
    private EditText emailEt, passwordEt;

    public LogInFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_log_in, container, false);
        init();

        mAuth = FirebaseAuth.getInstance();

        Button buttonNewReg = view.findViewById(R.id.buttonNewReg);
        Button buttonLogIn = view.findViewById(R.id.buttonToLogIn);

        buttonLogIn.setOnClickListener( view -> {
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("LogInFragment", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_eventsListOrgFragment);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("LogInFragment", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(requireContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(this.getContext(), "Пожалуйста, заполните поля", Toast.LENGTH_LONG).show();
            }
        });

        buttonNewReg.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_registerFragment);
        });

        return view;
    }

    private void init() {
        emailEt = view.findViewById(R.id.editTextTextEmailAddress);
        passwordEt = view.findViewById(R.id.editTextTextPassword);
    }
}