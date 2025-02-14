package com.example.gpacalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText[] creditHours, subjectName;
    Spinner[] gradeSpinners;
    TextView gpaResult;

    // Grade to GPA mapping
    private final HashMap<String, Double> gradeToGPA = new HashMap<String, Double>() {{
        put("A+", 4.0);
        put("A", 4.0);
        put("A-", 3.7);
        put("B+", 3.3);
        put("B", 3.0);
        put("B-", 2.7);
        put("C+", 2.3);
        put("C", 2.0);
        put("C-", 1.7);
        put("D+", 1.3);
        put("D", 1.0);
        put("F", 0.0);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       init();

        // Populate grade spinners
        setupGradeSpinners();


    }

    private void setupGradeSpinners() {
        String[] grades = {"Grade", "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, grades);

        for (Spinner spinner : gradeSpinners) {
            spinner.setAdapter(adapter);
        }
    }

    private void calculateGPA() {
        double totalPoints = 0;
        int totalCreditHours = 0;

        for (int i = 0; i < creditHours.length; i++) {
            String creditText = creditHours[i].getText().toString();
            String selectedGrade = gradeSpinners[i].getSelectedItem().toString();

            if (!creditText.isEmpty() && gradeToGPA.containsKey(selectedGrade)) {
                int credit = Integer.parseInt(creditText);
                double gpaValue = gradeToGPA.get(selectedGrade);

                totalPoints += credit * gpaValue;
                totalCreditHours += credit;
            }
        }

        if (totalCreditHours > 0) {
            double gpa = totalPoints / totalCreditHours;
            gpaResult.setText("Your GPA: " + String.format("%.2f", gpa));
        } else {
            gpaResult.setText("Enter valid credit hours and grades!");
        }
    }

    private void clearFields() {
        for (EditText editText : subjectName) {
            editText.setText("");
        }
        for (EditText editText : creditHours) {
            editText.setText("");
        }
        for (Spinner spinner : gradeSpinners) {
            spinner.setSelection(0);
        }
        gpaResult.setText("");
    }

    void init(){
        // Initializing views
        subjectName = new EditText[]{
                findViewById(R.id.subjectName1),
                findViewById(R.id.subjectName2),
                findViewById(R.id.subjectName3),
                findViewById(R.id.subjectName4),
                findViewById(R.id.subjectName5)
        };

        creditHours = new EditText[]{
                findViewById(R.id.creditHours1),
                findViewById(R.id.creditHours2),
                findViewById(R.id.creditHours3),
                findViewById(R.id.creditHours4),
                findViewById(R.id.creditHours5)
        };

        gradeSpinners = new Spinner[]{
                findViewById(R.id.gradeSpinner1),
                findViewById(R.id.gradeSpinner2),
                findViewById(R.id.gradeSpinner3),
                findViewById(R.id.gradeSpinner4),
                findViewById(R.id.gradeSpinner5)
        };

        gpaResult = findViewById(R.id.gpaResult);

        Button submitButton = findViewById(R.id.submitButton);
        Button clearButton = findViewById(R.id.clearButton);
        // Submit Button Click: Calculate GPA
        submitButton.setOnClickListener(v -> calculateGPA());

        // Clear Button Click: Reset all fields
        clearButton.setOnClickListener(v -> clearFields());
    }
}
