package com.example.gpacalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    private EditText[] creditHours;
    private Spinner[] gradeSpinners;
    private TextView gpaResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateGPA();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculateGPA() {
        double totalPoints = 0;
        double totalCreditHours = 0;

        for (int i = 0; i < creditHours.length; i++) {
            String creditStr = creditHours[i].getText().toString();
            if (!creditStr.isEmpty()) {
                int credits = Integer.parseInt(creditStr);
                double gradeValue = getGradeValue(gradeSpinners[i].getSelectedItem().toString());

                totalPoints += credits * gradeValue;
                totalCreditHours += credits;
            }
        }

        if (totalCreditHours > 0) {
            double gpa = totalPoints / totalCreditHours;
            gpaResult.setText("Your Calculated GPA is: " + String.format("%.2f", gpa));
        } else {
            gpaResult.setText("Please enter valid data.");
        }
    }

    private double getGradeValue(String grade) {
        switch (grade) {
            case "A+": return 4.0;
            case "A": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "C+": return 2.7;
            case "C": return 2.3;
            case "D": return 2.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }

    @SuppressLint("SetTextI18n")
    private void clearFields() {
        for (EditText creditHour : creditHours) {
            creditHour.setText("");
        }
        gpaResult.setText("Your Calculated GPA is:");
    }
}
