package com.leoneer.evention;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentEvent extends Fragment {

    private RecyclerView rvEventTeams;
    private RecyclerView rvGameDay;
    private ArrayList<Team> eventTeams;
    private ArrayList<Game> eventGames;
    private final int eventNum;
    private ArrayList<Event> eventList;
    private TextView eventName;
    private BottomNavigationView bottom_nav;

    public Integer gameNum;
    public Integer score1;
    public Integer score2;


    public FragmentEvent(int num) {
        eventNum = num;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        rvEventTeams = view.findViewById(R.id.rvEventTeams);
        rvGameDay = view.findViewById(R.id.rvGameDay);
        eventName = view.findViewById(R.id.txtComment);
        bottom_nav = view.findViewById(R.id.bottom_navigation);


        eventList = PrefConfig.readEventListFromPref(getContext());
        eventTeams = eventList.get(eventNum).getEventTeams();


        eventGames = eventList.get(eventNum).getEventGames();

        eventName.setText(eventList.get(eventNum).getEventName());

        showTeams();
        showGameDay();

        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.standings:
                        showTeams();
                        rvEventTeams.setVisibility(View.VISIBLE);
                        rvGameDay.setVisibility(View.INVISIBLE);
                        return true;
                    case R.id.gameday:
                        showGameDay();
                        rvEventTeams.setVisibility(View.INVISIBLE);
                        rvGameDay.setVisibility(View.VISIBLE);
                        return true;
                }
                return false;
            }
        });
        return view;
    }



    public void showTeams() {
        Collections.sort(eventTeams);
        if (eventTeams.get(eventTeams.size()-1).getTeamPoints() >= 0) {
            Collections.reverse(eventTeams);
        }

        EventDetailRecViewAdapter adapter = new EventDetailRecViewAdapter(eventList);
        adapter.setPlayerArrayList(eventTeams);
        rvEventTeams.setAdapter(adapter);
        rvEventTeams.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void showGameDay(){
        GameDayRecViewAdapter adapter = new GameDayRecViewAdapter(eventList, eventNum);
        adapter.setGamesArrayList(eventGames);
        rvGameDay.setAdapter(adapter);
        rvGameDay.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}

