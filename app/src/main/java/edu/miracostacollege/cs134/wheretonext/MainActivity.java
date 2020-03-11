/***
 * Programmer: Sabas Sanchez
 * Date: 3/6/2020
 * Project: IC11 WhereToNext
 * File: MainActivity.java
 */

package edu.miracostacollege.cs134.wheretonext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import edu.miracostacollege.cs134.wheretonext.model.College;
import edu.miracostacollege.cs134.wheretonext.model.JSONLoader;

public class MainActivity extends AppCompatActivity {

    private List<College> collegesList;
    private edu.miracostacollege.cs134.wheretonext.CollegeListAdapter collegesListAdapter;
    private ListView collegesListView;
    private EditText nameEditText;
    private EditText populationEditText;
    private EditText tuitionEditText;
    private RatingBar collegeRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up references
        collegesListView = findViewById(R.id.collegeListView);
        nameEditText = findViewById(R.id.nameEditText);
        populationEditText = findViewById(R.id.populationEditText);
        tuitionEditText = findViewById(R.id.tuitionEditText);
        collegeRatingBar = findViewById(R.id.collegeRatingBar);


        //this.deleteDatabase(DBHelper.DATABASE_NAME);
        //db = new DBHelper(this);

        // TODO: Comment this section out once the colleges below have been added to the database,
        // TODO: so they are not added multiple times (prevent duplicate entries)
        //db.addCollege(new College("UC Berkeley", 42082, 14068, 4.7, "ucb.png"));
        //db.addCollege(new College("UC Irvine", 31551, 15026.47, 4.3, "uci.png"));
        //db.addCollege(new College("UC Los Angeles", 43301, 25308, 4.5, "ucla.png"));
        //db.addCollege(new College("UC San Diego", 33735, 20212, 4.4, "ucsd.png"));
        //db.addCollege(new College("CSU Fullerton", 38948, 6437, 4.5, "csuf.png"));
        //db.addCollege(new College("CSU Long Beach", 37430, 6452, 4.4, "csulb.png"));

        // DONE:  Fill the collegesList with all Colleges from the database
        try {
            collegesList = JSONLoader.loadJSONFromAsset(this);
        } catch (IOException e) {
            Log.e("WhereToNext", "Error loading from", e);
        }

        //nDisplay the college names
        for (int i = 0; i < collegesList.size(); i++) {
            Log.i("college name" , collegesList.get(i).toString());
            
        }

        // TODO:  Connect the list adapter with the list
        collegesListAdapter = new CollegeListAdapter(this, R.layout.college_list_item, collegesList);

        collegesListView.setAdapter(collegesListAdapter);
        // TODO:  Set the list view to use the list adapter
    }

    /***
     * Displays the selected college details
     * @param view the view that was selected when this method was invoked
     */
    public void viewCollegeDetails(View view) {

        // TODO: Implement the view college details using an Intent
        College clickedCollege = (College)view.getTag();

        Intent intent = new Intent(this, CollegeDetailsActivity.class);

        String name = clickedCollege.getName();
        int population = clickedCollege.getPopulation();
        double tuition = clickedCollege.getTuition();
        double rating = clickedCollege.getRating();
        String imageName = clickedCollege.getImageName();

        intent.putExtra("Name", clickedCollege.getName());
        intent.putExtra("Population", clickedCollege.getPopulation());
        intent.putExtra("Tuition", clickedCollege.getTuition());
        intent.putExtra("Rating", clickedCollege.getRating());
        intent.putExtra("ImageName", clickedCollege.getImageName());

        startActivity(intent);
    }

    /***
     * Adds a college to view list based on input text field data
     * @param view the view that was selected when this method was invoked
     */
    public void addCollege(View view) {

        // Done: Implement the code for when the user clicks on the addCollegeButton

        String collegeName = nameEditText.getText().toString();
        String population =  populationEditText.getText().toString();
        String tuition = tuitionEditText.getText().toString();
        float rating = collegeRatingBar.getRating();

        if(collegeName.isEmpty())
        {
            Toast.makeText(this, "Empty field is not allowed, enter a college Name",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            int pop = (population.isEmpty()) ? 0 : Integer.parseInt(population);
            double tuit = (tuition.isEmpty()) ? 0 : Double.parseDouble(tuition);
            College college = new College(collegeName, pop, tuit, rating);
            collegesList.add(college);
            collegesListAdapter.notifyDataSetChanged();
        }
    }

}
