package com.example.assiment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    public SQLiteHelper sqLiteHelper;

    public ProfileFragment() {
        // Required empty public constructor
    }

    TextView name , email , user;
    Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sqLiteHelper = new SQLiteHelper(getContext());
        name = view.findViewById(R.id.Pname);
        email = view.findViewById(R.id.Pemail);
        user = view.findViewById(R.id.user);
        logout = view.findViewById(R.id.logout);

        Shared shared = new Shared(getContext());
        String nameuser = shared.getUser();

        LoginActivity mail = new LoginActivity();
        if (nameuser.equals("ziad") || nameuser.equals("saad") || nameuser.equals("amen") ||nameuser.equals("umer") || nameuser.equals("admin")){
            name.setText(" "+getResources().getString(R.string.name) + "     " + nameuser);
            email.setText(" "+getResources().getString(R.string.email)+ "    "+shared.getEmail());
            user.setText("you are admin");

        }
        else {
            Cursor c = sqLiteHelper.getData("SELECT * FROM account");
            while (c.moveToNext()) {
                String n = c.getString(1);
                if (n.equals(nameuser)) {
                    String emailuser = c.getString(2);
                    name.setText(" "+getResources().getString(R.string.name) + "     " + nameuser);
                    email.setText(" "+getResources().getString(R.string.email) + "   " + emailuser);
                }
            }
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared shared = new Shared(getContext());
                shared.trigActivity();
                Intent intent = new Intent(getContext() , LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
