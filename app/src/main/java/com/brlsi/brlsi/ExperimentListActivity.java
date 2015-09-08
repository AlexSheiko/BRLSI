package com.brlsi.brlsi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExperimentListActivity extends AppCompatActivity {

    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_list);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.experiment_list_item, new ArrayList<String>());
        listView.setAdapter(adapter);

        populateList(adapter);
    }

    private void populateList(final ArrayAdapter<String> adapter) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Experiment");
        query.whereEqualTo("author", ParseUser.getCurrentUser());
        query.orderByDescending("updatedAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> experiments, ParseException e) {
                if (e == null) {
                    for (ParseObject exp : experiments) {
                        adapter.add(exp.getString("title"));
                    }
                } else {
                    Toast.makeText(ExperimentListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
