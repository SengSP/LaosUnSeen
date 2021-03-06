package masterung.androidthai.in.th.laosunseen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import masterung.androidthai.in.th.laosunseen.R;
import masterung.androidthai.in.th.laosunseen.ServiceActivity;
import masterung.androidthai.in.th.laosunseen.utility.MyAlert;

public class MainFragment extends Fragment{

    private String emailString, passwordString;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Check Status
        checkStatus();


        // register Controller
        registerController();

//        Login Controller
        loginController();

    }// Method Main

    private void loginController(){
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailEditText = getView().findViewById(R.id.edtEmail);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);
                emailString = emailEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();
                if (emailString.isEmpty() || passwordString.isEmpty()) {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.normalDialog("Have Space", "Please Fill All Blank");

                } else {
                    checkAuthen();
                }
            }
        });
    }

    private void checkAuthen() {

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                    moveToService();
                    //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMainFragment, new ServiceFragment()).commit();
                    Log.d("9AugV1", "Login Status");
                } else {
                    Log.d("9AugV1", "No Login");
                    MyAlert myAlert =  new MyAlert(getActivity());
                    myAlert.normalDialog("Authen False", "Because ==>" + task.getException().toString());

                }
            }
        });

    }

    private void checkStatus() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            moveToService();
            Log.d("9AugV1", "Login");
        } else {
            Toast.makeText(getActivity(), "Please Login", Toast.LENGTH_SHORT).show();
            Log.d("9AugV1", "No Login");
        }
    }

    private void moveToService() {
        startActivity(new Intent(getActivity(), ServiceActivity.class));
        getActivity().finish();
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Replace Fracment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMainFragment, new RegisterFragment()).addToBackStack(null).commit();
            }
        });
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}
