package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;


public class lockscreenpattern extends AppCompatActivity {

    private PatternView patternView;

    private List<Integer> firstPattern = new ArrayList<>();
    private List<Integer> secondPattern = new ArrayList<>();
    private boolean isSettingSecondPattern = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreenpattern);

        patternView = findViewById(R.id.pattern_lock_view);
        patternView.setOnPatternListener(new PatternView.OnPatternListener() {
            @Override
            public void onPatternStart() {

            }

            @Override
            public void onPatternCleared() {

            }

            @Override
            public void onPatternCellAdded(List<PatternView.Cell> pattern) {

            }

            @Override
            public void onPatternDetected(List<PatternView.Cell> pattern) {
                if (!isSettingSecondPattern) {
                    firstPattern.clear();
                    for (PatternView.Cell cell : pattern) {
                        // Store the pattern as a list of cell IDs
                        firstPattern.add(cell.getRow());
                    }
                    // Clear the pattern and prompt for the second entry
                    patternView.clearPattern();
                    isSettingSecondPattern = true;
                } else {
                    secondPattern.clear();
                    for (PatternView.Cell cell : pattern) {
                        secondPattern.add(cell.getRow());
                    }

                    if (firstPattern.equals(secondPattern)) {
                        // Patterns match, save the pattern and open another activity
                        savePattern(firstPattern);

                        startActivity(new Intent(lockscreenpattern.this, emicalculator.class));
                        finish();
                    } else {
                        // Patterns don't match, show an error message
                        Toast.makeText(lockscreenpattern.this, "Patterns do not match", Toast.LENGTH_SHORT).show();
                        // Clear the pattern and start over
                        patternView.clearPattern();
                        firstPattern.clear();
                        secondPattern.clear();
                        isSettingSecondPattern = false;
                    }
                }
            }
        });
    }

    private void savePattern(List<Integer> pattern) {
        // Save the pattern securely, e.g., in SharedPreferences or a database
    }

}