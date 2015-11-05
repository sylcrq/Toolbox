package com.syl.toolbox.views.activities;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.desmond.ripple.RippleCompat;
import com.syl.toolbox.MessageEvent;
import com.syl.toolbox.R;
import com.syl.toolbox.views.PieChart;

import de.greenrobot.event.EventBus;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        Button button = (Button) findViewById(R.id.hello_world);

        RippleCompat.init(this);
        RippleCompat.apply(button);

        Resources res = getResources();

        final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
        pie.addItem("Agamemnon", 2, res.getColor(R.color.seafoam));
        pie.addItem("Bocephus", 3.5f, res.getColor(R.color.chartreuse));
        pie.addItem("Calliope", 2.5f, res.getColor(R.color.emerald));
        pie.addItem("Daedalus", 3, res.getColor(R.color.bluegrass));
        pie.addItem("Euripides", 1, res.getColor(R.color.turquoise));
        pie.addItem("Ganymede", 3, res.getColor(R.color.slate));

        ((Button) findViewById(R.id.hello_world)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pie.setCurrentItem(0);

                EventBus.getDefault().post(new MessageEvent("Hello EventBus !"));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    public void onEvent(MessageEvent event) {
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
    }
}
