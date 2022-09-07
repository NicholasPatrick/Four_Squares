package com.example.foursquares;

import static java.lang.System.nanoTime;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.PrimitiveIterator;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

//    private String mParam1;
//    private String mParam2;
    private PrimitiveIterator.OfLong rng;
    private long problem;
    private StringBuffer solution;
    private Activity activity;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GameFragment.
     */
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
    public static GameFragment newInstance(/*String param1, String param2*/) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    private View view;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rng = new Random(nanoTime())
                .longs(1000, 10000)
                .iterator();
        view = inflater.inflate(R.layout.fragment_game, container, false);
        activity = getActivity();
        skip();
        Button zeroDigitButton = view.findViewById(R.id.zeroDigitButton);
        zeroDigitButton.setOnClickListener(this);
        Button oneDigitButton = view.findViewById(R.id.oneDigitButton);
        oneDigitButton.setOnClickListener(this);
        Button twoDigitButton = view.findViewById(R.id.twoDigitButton);
        twoDigitButton.setOnClickListener(this);
        Button threeDigitButton = view.findViewById(R.id.threeDigitButton);
        threeDigitButton.setOnClickListener(this);
        Button fourDigitButton = view.findViewById(R.id.fourDigitButton);
        fourDigitButton.setOnClickListener(this);
        Button fiveDigitButton = view.findViewById(R.id.fiveDigitButton);
        fiveDigitButton.setOnClickListener(this);
        Button sixDigitButton = view.findViewById(R.id.sixDigitButton);
        sixDigitButton.setOnClickListener(this);
        Button sevenDigitButton = view.findViewById(R.id.sevenDigitButton);
        sevenDigitButton.setOnClickListener(this);
        Button eightDigitButton = view.findViewById(R.id.eightDigitButton);
        eightDigitButton.setOnClickListener(this);
        Button nineDigitButton = view.findViewById(R.id.nineDigitButton);
        nineDigitButton.setOnClickListener(this);
        Button skipButton = view.findViewById(R.id.resetButton);
        skipButton.setOnClickListener(this);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zeroDigitButton:
                addDigit('0');
                break;
            case R.id.oneDigitButton:
                addDigit('1');
                break;
            case R.id.twoDigitButton:
                addDigit('2');
                break;
            case R.id.threeDigitButton:
                addDigit('3');
                break;
            case R.id.fourDigitButton:
                addDigit('4');
                break;
            case R.id.fiveDigitButton:
                addDigit('5');
                break;
            case R.id.sixDigitButton:
                addDigit('6');
                break;
            case R.id.sevenDigitButton:
                addDigit('7');
                break;
            case R.id.eightDigitButton:
                addDigit('8');
                break;
            case R.id.nineDigitButton:
                addDigit('9');
                break;
            case R.id.resetButton:
                skip();
                break;
            case R.id.deleteButton:
                deleteDigit();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void generateProblem() {
        problem = rng.nextLong();
        updateProblemText();
    }

    private void clearSolution() {
        solution = new StringBuffer();
        updateSolutionText();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void solved() {
        if (activity instanceof TimeTrialActivity) {
            ((TimeTrialActivity) activity).solved();
        } else if (activity instanceof ClearThirtyActivity) {
            ((ClearThirtyActivity) activity).solved();
        }
        generateProblem();
        clearSolution();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkCorrectness() {
        int[] digitCounts = {0, 0, 0, 0};
        while (true) {
            int pointer = solution.length();
            for (int i = 0; i < 4; ++i) {
                pointer -= digitCounts[i];
            }
            if (pointer < 0 || solution.length() - pointer > 12) {
                break;
            }
            long sum = 0;
            for (int i = 0; i < 4; ++i) {
                long a = 0;
                for (int j = 0; j < digitCounts[i]; ++j) {
                    a = a * 10 + solution.charAt(pointer) - '0';
                    pointer++;
                }
                sum += a * a;
            }
            if (sum == problem) {
                solved();
                return;
            }
            if (digitCounts[0] + digitCounts[1] + digitCounts[2] == 0) {
                digitCounts[0] = digitCounts[3] + 1;
                digitCounts[3] = 0;
            } else {
                for (int i = 0; i < 4; ++i) {
                    if (digitCounts[i] >= 1) {
                        digitCounts[i + 1] += 1;
                        digitCounts[0] = digitCounts[i] - 1;
                        if (i > 0) {
                            digitCounts[i] = 0;
                        }
                        break;
                    }
                }
            }
        }
    }

    private void updateSolutionText() {
        TextView textView = view.findViewById(R.id.solutionText);
        textView.setText(solution);
    }

    private void updateProblemText() {
        TextView textView = view.findViewById(R.id.problemText);
        textView.setText(String.format(Locale.US, "%d", problem));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addDigit(char c) {
        solution.append(c);
        while (solution.length() > 16) {
            solution.deleteCharAt(0);
        }
        checkCorrectness();
        updateSolutionText();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void skip() {
        generateProblem();
        clearSolution();
        if (activity instanceof TimeTrialActivity) {
            ((TimeTrialActivity) activity).reset();
        } else if (activity instanceof ClearThirtyActivity) {
            ((ClearThirtyActivity) activity).reset();
        }
    }

    public void deleteDigit() {
        if (solution.length() > 0) {
            solution.setLength(solution.length() - 1);
            updateSolutionText();
        }
    }
}