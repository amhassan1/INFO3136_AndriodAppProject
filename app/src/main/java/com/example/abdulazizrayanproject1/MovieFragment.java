package com.example.abdulazizrayanproject1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


public class MovieFragment extends Fragment {

    private EditText titleEditText;
    private EditText yearEditText;
    private RatingBar movieRating;
    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        titleEditText = view.findViewById(R.id.movie_title_edit_text);
        yearEditText = view.findViewById(R.id.movie_year_edit_text);
        movieRating = view.findViewById(R.id.movie_rating);

        Button bookSubmitBtn = view.findViewById(R.id.movie_submit_button);
        bookSubmitBtn.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String year = yearEditText.getText().toString();
            float rating = movieRating.getRating();

            if(title.isEmpty() || year.isEmpty()) {
                Toast.makeText(getContext(), R.string.all_fields_required, Toast.LENGTH_SHORT).show();
            }
            else {
                String submission = getString(R.string.movie_submission, title, year, rating);
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity!= null) {
                    activity.addEntry(submission);
                }
                titleEditText.setText("");
                yearEditText.setText("");
                movieRating.setRating(0.0f);
            }
        });
        return view;
    }
}