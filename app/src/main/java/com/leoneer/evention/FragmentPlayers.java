package com.leoneer.evention;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class FragmentPlayers extends Fragment {

    private RadioGroup rgTeamMode;
    private ImageButton btnSinglePlayer;
    private ImageButton btnEditTeam;
    private ImageButton btnRndTeam;
    private ImageButton btnAdd;
    private ArrayList<Game> eventGames;
    private ArrayList<ArrayList<Game>> eventGameDays;
    private ArrayList<Player> playerList;
    private ArrayList<Team> eventTeams;
    private RecyclerView rvPlayerList;
    private EditText edtPlayerName;
    private final ArrayList<Event> eventList;
    private EditText edtMaxTeamSize;
    private int numGameDays;
    private int numTeams;
    public Random RndNumber = new Random();

    public FragmentPlayers(ArrayList<Event> arrayList) {
        eventList = arrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_players, container, false);

        rgTeamMode = view.findViewById(R.id.rgTeamMode);
        btnSinglePlayer = view.findViewById(R.id.btnSinglePlayer);
        btnEditTeam = view.findViewById(R.id.btnEdtTeam);
        btnRndTeam = view.findViewById(R.id.btnRndTeam);
        rvPlayerList = view.findViewById(R.id.rvPlayerList);
        edtPlayerName = view.findViewById(R.id.edtPlayerName);
        btnAdd = view.findViewById(R.id.btnAdd);
        edtMaxTeamSize = view.findViewById(R.id.edtNumTeams);

        eventTeams = new ArrayList<>();

        playerList = PrefConfig.readPlayerListFromPref(getContext());
        if (playerList == null) {
            playerList = new ArrayList<>();
        }

        showPlayers();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtPlayerName.getText().toString();
                Player player = new Player(name, true, false);
                playerList.add(player);

                showPlayers();

                edtPlayerName.setText("");

                PrefConfig.writePlayerListInPref(getActivity().getApplicationContext(), playerList);
            }
        });

        btnSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Player> singlePlayerList = new ArrayList<>();

                for (int i = 0; i < playerList.size(); i++) {
                    if (playerList.get(i).isPlayerActive() == true) {
                        Team playerTeam = new Team(playerList.get(i).getPlayerName(), singlePlayerList, true);
                        eventTeams.add(playerTeam);
                    }
                }
                eventList.get(eventList.size() - 1).setEventTeams(eventTeams);

                createGameDays(eventTeams);
                eventList.get(eventList.size() - 1).setEventGames(eventGames);

                //Toast.makeText( getActivity(),eventList.get(eventList.size()-1).getEventGameDays().get(0).get(0).getTeam1() + " vs. " + eventList.get(eventList.size()-1).getEventGameDays().get(0).get(0).getTeam2(),Toast.LENGTH_SHORT).show();

                PrefConfig.writeEventListInPref(getActivity().getApplicationContext(), eventList);
                //Toast.makeText(getActivity(), eventList.get(eventList.size() - 1).getEventName() + " was saved!", Toast.LENGTH_SHORT).show();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentEventView fragment = new FragmentEventView();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        fragment).commit();
            }
        });

        btnEditTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Player> activePlayerList = new ArrayList<>();

                for (int i = 0; i < playerList.size(); i++) {
                    if (playerList.get(i).isPlayerActive() == true) {
                        activePlayerList.add(playerList.get(i));
                    }
                }
                Toast.makeText(getActivity(), activePlayerList.size() + " players are compeeting in this event!", Toast.LENGTH_SHORT).show();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentSetTeam fragment = new FragmentSetTeam(eventList, eventTeams, activePlayerList, 1);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        fragment).addToBackStack(null).commit();
            }
        });

        btnRndTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Player> activePlayerList = new ArrayList<>();

                for (int i = 0; i < playerList.size(); i++) {
                    if (playerList.get(i).isPlayerActive() == true) {
                        activePlayerList.add(playerList.get(i));
                    }
                }
                String stringMaxTeams = edtMaxTeamSize.getText().toString();

                if (TextUtils.isEmpty(stringMaxTeams)) {
                    Toast.makeText(getActivity(), "Please enter a valid number of teams!", Toast.LENGTH_SHORT).show();
                } else {
                    int maxTeamSi = new Integer(stringMaxTeams).intValue();
                    if (activePlayerList.size() % maxTeamSi == 0) {
                        numTeams = activePlayerList.size() / maxTeamSi;
                    } else {
                        numTeams = activePlayerList.size() / maxTeamSi + 1;
                    }

                    for (int i = 0; i < numTeams; i++) {
                        int number = i + 1;
                        ArrayList<Player> teamPlayer = new ArrayList<>();
                        Team newTeam = new Team("Team " + number, teamPlayer, true);

                        eventTeams.add(newTeam);
                    }


                    for (int j = 0; j < activePlayerList.size(); j++) {
                        int rnd = RndNumber.nextInt(numTeams);
                        while (eventTeams.get(rnd).getTeamPlayers().size() >= maxTeamSi) {
                            rnd = RndNumber.nextInt(numTeams);
                        }
                        eventTeams.get(rnd).getTeamPlayers().add(activePlayerList.get(j));
                    }
                    eventList.get(eventList.size() - 1).setEventTeams(eventTeams);

                    createGameDays(eventTeams);
                    eventList.get(eventList.size() - 1).setEventGames(eventGames);

                    PrefConfig.writeEventListInPref(getActivity().getApplicationContext(), eventList);

                    //Toast.makeText(getActivity(), eventList.get(eventList.size() - 1).getEventName() + " was saved! ", Toast.LENGTH_SHORT).show();

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentEventView fragment = new FragmentEventView();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            fragment).commit();

                }
            }
        });

        rgTeamMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbSinglePlayers:
                        btnSinglePlayer.setVisibility(View.VISIBLE);
                        edtMaxTeamSize.setVisibility(View.INVISIBLE);
                        btnEditTeam.setVisibility(View.INVISIBLE);
                        btnRndTeam.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rbEditTeam:
                        btnSinglePlayer.setVisibility(View.INVISIBLE);
                        edtMaxTeamSize.setVisibility(View.INVISIBLE);
                        btnEditTeam.setVisibility(View.VISIBLE);
                        btnRndTeam.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rbRndTeams:
                        btnSinglePlayer.setVisibility(View.INVISIBLE);
                        edtMaxTeamSize.setVisibility(View.VISIBLE);
                        btnEditTeam.setVisibility(View.INVISIBLE);
                        btnRndTeam.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        return view;
    }

    public void showPlayers() {
        PlayersRecViewAdapter adapter = new PlayersRecViewAdapter();
        adapter.setPlayerArrayList(playerList);
        rvPlayerList.setAdapter(adapter);
        rvPlayerList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void createGameDays(ArrayList<Team> teams) {
        eventGames = new ArrayList<>();

        for (int i = 0; i < teams.size(); i++) {
            Team team1 = teams.get(i);
            for (int j = i + 1; j < teams.size(); j++) {
                Team team2 = teams.get(j);
                eventGames.add(new Game(team1.getTeamName(), team2.getTeamName()));
            }
        }

    }
}

