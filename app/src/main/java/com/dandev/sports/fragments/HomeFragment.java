package com.dandev.sports.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dandev.sports.R;
import com.dandev.sports.activities.AthlethicsActivity;
import com.dandev.sports.activities.BasketballActivity;
import com.dandev.sports.activities.FootballActivity;
import com.dandev.sports.activities.RecreationalActivity;
import com.dandev.sports.activities.RugbyActivity;
import com.dandev.sports.activities.VolleyballActivity;


public class HomeFragment extends Fragment implements View.OnClickListener {

    CardView football, basketball, volleyball, rugby, recreation, athletics;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        football = view.findViewById(R.id.football);
        basketball = view.findViewById(R.id.basketball);
        volleyball = view.findViewById(R.id.volleyball);
        rugby = view.findViewById(R.id.rugby);
        athletics = view.findViewById(R.id.athletics);
        recreation = view.findViewById(R.id.recreation);

        football.setOnClickListener(this);
        basketball.setOnClickListener(this);
        volleyball.setOnClickListener(this);
        rugby.setOnClickListener(this);
        athletics.setOnClickListener(this);
        recreation.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId())
        {
            case R.id.football:
                intent = new Intent(getActivity(),FootballActivity.class);
                startActivity(intent);
                break;

            case R.id.basketball:
                intent = new Intent(getActivity(), BasketballActivity.class);
                startActivity(intent);
                break;

            case R.id.volleyball:
                intent = new Intent(getActivity(), VolleyballActivity.class);
                startActivity(intent);
                break;

            case R.id.rugby:
                intent = new Intent(getActivity(), RugbyActivity.class);
                startActivity(intent);
                break;

            case R.id.athletics:
                intent = new Intent(getActivity(), AthlethicsActivity.class);
                startActivity(intent);
                break;

            case R.id.recreation:
                intent = new Intent(getActivity(), RecreationalActivity.class);
                startActivity(intent);
                break;


            default:break;

        }

    }
}