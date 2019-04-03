package pl.krakow.up.ii.opensource.mobilewu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    ListView listView;
    String[] listValue = new String[] {"Oceny","Plan studiów","Plan zajęć","Stypendia"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        listView = (ListView)findViewById(R.id.lvMenu);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_list_view_menu, R.id.textViewMenu, listValue);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(getApplicationContext(), "Wybrano Oceny", Toast.LENGTH_SHORT).show();
                        System.out.println("Wybrano 0");
                        Intent intent = new Intent(MenuActivity.this,OcenyActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Wybrano Plan studiów", Toast.LENGTH_SHORT).show();
                        System.out.println("Wybrano 1");
                        //Intent intent = new Intent(MenuActivity.this, [tu wpisujesz swoje activity.class]);
                        //startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Wybrano zajęć", Toast.LENGTH_SHORT).show();
                        System.out.println("Wybrano 2");
                        //Intent intent = new Intent(MenuActivity.this, [tu wpisujesz swoje activity.class]);
                        //startActivity(intent);
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Wybrano Stypendia", Toast.LENGTH_SHORT).show();
                        System.out.println("Wybrano 3");
                        //Intent intent = new Intent(MenuActivity.this, [tu wpisujesz swoje activity.class]);
                        //startActivity(intent);
                        break;
                }

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Wybrano Opcje", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.subitem1:
                Toast.makeText(this, "Wylogowano", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Wybrano Dane studenta", Toast.LENGTH_SHORT).show();
                //tutaj dodaj swoje activity tak jak u góry tylko bez finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}