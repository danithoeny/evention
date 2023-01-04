package com.leoneer.evention;

import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.os.ProxyFileDescriptorCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FragmentNewEvent extends Fragment {

    private RadioGroup rgMode;
    private ImageView imgMode;
    private TextView txtEventDescription;
    private ImageButton btnPlayers;
    private EditText edtEventName;
    private Event newEvent;
    private Bundle bundle;
    private ArrayList<Event> eventList;
    private String eventMode;
    private Switch drawSwitch;
    private EditText edtWinPoints;
    private EditText edtDrawPoints;
    private boolean winPointsValid;
    private boolean drawPointsValid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_event, container, false);

        rgMode = view.findViewById(R.id.rgMode);
        imgMode = view.findViewById(R.id.imgMode);
        txtEventDescription = view.findViewById(R.id.txtEventDescription);
        btnPlayers = view.findViewById(R.id.btnPlayers);
        edtEventName = view.findViewById(R.id.edtEventName);
        drawSwitch = view.findViewById(R.id.switchDraw);
        edtWinPoints = view.findViewById(R.id.edtPointsWin);
        edtDrawPoints = view.findViewById(R.id.edtPointsDraw);
        winPointsValid = false;
        drawPointsValid = false;

        eventMode = "League";

        rgMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbLeague:
                        imgMode.setImageResource(R.drawable.league);
                        txtEventDescription.setText("top ranked team with the most points wins the event");
                        eventMode = "League";
                        edtWinPoints.setVisibility(View.VISIBLE);
                        drawSwitch.setVisibility(View.VISIBLE);
                        drawSwitch.setChecked(false);
                        edtDrawPoints.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.rbKO:
                        imgMode.setImageResource(R.drawable.kosystem);
                        txtEventDescription.setText("winning team proceeds to the next bracket \nlosing team gets eliminated");
                        eventMode = "K.O.-System";
                        edtWinPoints.setVisibility(View.INVISIBLE);
                        drawSwitch.setVisibility(View.INVISIBLE);
                        drawSwitch.setChecked(false);
                        edtDrawPoints.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rbTournament:
                        imgMode.setImageResource(R.drawable.tournament);
                        txtEventDescription.setText("group phase followed by finals");
                        eventMode = "Tournament";
                        edtWinPoints.setVisibility(View.VISIBLE);
                        drawSwitch.setVisibility(View.VISIBLE);
                        drawSwitch.setChecked(false);
                        edtDrawPoints.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

        bundle = this.getArguments();

        drawSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    edtDrawPoints.setVisibility(View.VISIBLE);
                } else {
                    edtDrawPoints.setVisibility(View.INVISIBLE);
                    edtDrawPoints.setText("");

                }
            }
        });

        btnPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Event> eventArrayList = (ArrayList<Event>)
                bundle.getSerializable("allEvents_ArrayList");

                ArrayList<Team> newTeams = new ArrayList<Team>();
                newEvent = new Event(edtEventName.getText().toString(),eventMode);
                newEvent.setEventTeams(newTeams);

                if (edtWinPoints.getVisibility() == View.VISIBLE && edtWinPoints.getText().length() > 0) {
                    String stringWinPoints = edtWinPoints.getText().toString();
                    int intWinPoints = new Integer(stringWinPoints).intValue();
                    newEvent.setPointsWin(intWinPoints);
                    winPointsValid = true;
                } else if (edtWinPoints.getVisibility() == View.INVISIBLE){
                    winPointsValid = true;
                    newEvent.setPointsWin(0);
                }

                if (edtDrawPoints.getVisibility() == View.VISIBLE && edtDrawPoints.getText().length() > 0) {
                    String stringDrawPoints = edtDrawPoints.getText().toString();
                    int intDrawPoints = new Integer(stringDrawPoints).intValue();
                    newEvent.setPointsDraw(intDrawPoints);
                    drawPointsValid = true;
                } else if (edtDrawPoints.getVisibility() == View.INVISIBLE){
                    drawPointsValid = true;
                    newEvent.setPointsDraw(0);
                }

                if (winPointsValid && drawPointsValid) {
                    eventArrayList.add(newEvent);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentPlayers fragment = new FragmentPlayers(eventArrayList);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            fragment).addToBackStack(null).commit();
                } else {
                    Toast.makeText(getActivity(), "Please set Points for Wins/Draws", Toast.LENGTH_SHORT).show();
                    winPointsValid = false;
                    drawPointsValid = false;
                }

            }
        });

        return view;
    }

}