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


public class BookFragment extends Fragment {

    private EditText titleEditText;
    private EditText yearEditText;
    private RatingBar bookRating;
    public BookFragment() {
        // Required empty public constructor
    }

    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        titleEditText = view.findViewById(R.id.book_title_edit_text);
        yearEditText = view.findViewById(R.id.book_year_edit_text);
        bookRating = view.findViewById(R.id.book_rating);

        Button bookSubmitBtn = view.findViewById(R.id.book_submit_button);
        bookSubmitBtn.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String year = yearEditText.getText().toString();
            float rating = bookRating.getRating();

            if(title.isEmpty() || year.isEmpty()) {
                Toast.makeText(getContext(), R.string.all_fields_required, Toast.LENGTH_SHORT).show();
            }
            else {
                String submission = getString(R.string.book_submission, title, year, rating);
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity!= null) {
                    activity.addEntry(submission);
                }
                titleEditText.setText("");
                yearEditText.setText("");
                bookRating.setRating(0.0f);
            }

        });
        return view;
    }
}