package pl.krakow.up.ii.opensource.mobilewu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // https://developer.android.com/reference/android/widget/Button
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // https://developer.android.com/reference/android/view/View.html#findViewById(int)
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // https://developer.android.com/reference/android/widget/Toast

                // Toast z tekstem zdefiniowanym w res/values/strings.xml
                // CharSequence txt_main_toast = getResources().getText(R.string.txt_main_toast);
                // Toast.makeText(MainActivity.this, txt_main_toast, Toast.LENGTH_SHORT)
                //          .show();

                Toast.makeText(MainActivity.this, "Toast!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
