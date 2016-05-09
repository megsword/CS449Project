package edu.umkc.mes6ybmail.thedish;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Megan on 4/24/2016.
 */
public class MealPlanActivityTest extends ActivityInstrumentationTestCase2<MealPlanActivity> {

    private MealPlanActivity activity;

    public MealPlanActivityTest() {
        super(MealPlanActivity.class);
    }

    // If you want to send key events via your test, you have to turn off
    // the touch mode in the emulator via setActivityInitialTouchMode(false)
    // in your setup() method of the test.
    public void setUp(  )  {
        // setActivityInitialTouchMode(false) must be called before the
        //   activity is created.
        setActivityInitialTouchMode(false);
        activity = (MealPlanActivity) getActivity();
    }

    public void MealUpdate() throws Exception {

        Button mealButton;
        mealButton = (Button) activity.findViewById(R.id.addExtra);

        TextView tv;
        tv = (TextView) activity.findViewById(R.id.editText1);

        final ListView inputField;
        inputField = (ListView) activity.findViewById(R.id.listOf);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                inputField.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("meals");
        getInstrumentation().waitForIdleSync();

        // TouchUtils handles the sync with the main thread internally
        TouchUtils.clickView(this, mealButton);
        // Note, the following line was missing in an earlier version
        //  of this program. It is definitely needed. If you don't wait
        //  for the clickView() update to finish, the assert below
        //  will fail.
        getInstrumentation().waitForIdleSync();

        // displayed value should e 1 now.
        assertEquals("list not updated properly", "meals", tv.getText().toString());
    }
}

