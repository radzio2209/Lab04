package pollub.ism.lab04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    
    char[][] plansza = new char[3][3]; 
    String[] aktualnyStan = new String[9];
    char znak_gracza1 = 'X', znak_gracza2 = 'O';
    boolean[] zapisanyStan = {true,true,true,true,true,true,true,true,true};
    boolean czy_gracz1 = true;
    public Button button1, button2, button3, button4, button5,button6, button7, button8, button9;
    int ruch=0;
    char znak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("plansza",aktualnyStan);
        outState.putBooleanArray("isenable",zapisanyStan);
        outState.putBoolean("whichplayer", czy_gracz1);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        aktualnyStan =savedInstanceState.getStringArray("plansza");
        zapisanyStan =savedInstanceState.getBooleanArray("isenable");
        czy_gracz1 = savedInstanceState.getBoolean("whichplayer");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                button = (Button) findViewById(getResources().getIdentifier("button_" + i + "_"+j, "id", this.getPackageName()));
                button.setText(aktualnyStan[i*3+j]);
                button.setEnabled(zapisanyStan[i*3+j]);
                if (aktualnyStan[i*3+j]!=null){
                    plansza[i][j]=aktualnyStan[i*3+j].charAt(0);
                }
            }
        }
    }


    public static boolean sprawdzWiersz(char[][] plansza, char symbol) {
        for (int i = 0; i <= 2; i++) {
            boolean win = true;
            for (int j = 0; j <= 2; j++) {
                if (plansza[i][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }
        return false;
    }

    public static boolean sprawdzP1(char[][] plansza, char symbol) {
        for (int i = 0; i <= 2; i++) {
            if (plansza[i][i] != symbol) {
                return false;
            }
        }
        return true;
    }

    public static boolean sprawdzP2(char[][] plansza, char symbol) {
        if (plansza[2][0] != symbol || plansza[1][1] != symbol || plansza[0][2] != symbol) {
            return false;
        }
        return true;
    }

    public static boolean sprawdzKol(char[][] plansza, char symbol) {
        for (int i = 0; i <= 2; i++) {
            boolean win = true;
            for (int j = 0; j <= 2; j++) {
                if (plansza[j][i] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }
        return false;
    }

    public void setenabled(){
        button1 = (Button) findViewById(R.id.button_0_0);
        button2 = (Button) findViewById(R.id.button_0_1);
        button3 = (Button) findViewById(R.id.button_0_2);
        button4 = (Button) findViewById(R.id.button_1_0);
        button5 = (Button) findViewById(R.id.button_1_1);
        button6 = (Button) findViewById(R.id.button_1_2);
        button7 = (Button) findViewById(R.id.button_2_0);
        button8 = (Button) findViewById(R.id.button_2_1);
        button9 = (Button) findViewById(R.id.button_2_2);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
    }

    public void kliknieciePrzycisku(View view) {
        String nazwaprzycisku = view.getResources().getResourceEntryName(view.getId()); // wyciągnięcie nazwy przycisku po kliknięciu
        String[] idSplit = nazwaprzycisku.split("_");
        int wiersz = Integer.parseInt(idSplit[1]);
        int kolumna = Integer.parseInt(idSplit[2]);
        ruch++;

        if (czy_gracz1) {
            plansza[wiersz][kolumna] = znak_gracza1;
            czy_gracz1 = false;
            znak=znak_gracza1;
            aktualnyStan[(wiersz)*3+(kolumna)] = Character.toString(znak_gracza1);
        }
        else {
            plansza[wiersz][kolumna] = znak_gracza2;
            czy_gracz1 = true;
            znak=znak_gracza2;
            aktualnyStan[(wiersz)*3+(kolumna)] = Character.toString(znak_gracza2);
        }
        
        Button button = (Button) findViewById(view.getId());
        button.setText(Character.toString(plansza[wiersz][kolumna]));
        button.setEnabled(false);

        if (sprawdzWiersz(plansza,znak)==true || sprawdzKol(plansza,znak)==true ||sprawdzP1(plansza,znak)==true || sprawdzP2(plansza,znak)==true){
            setenabled();
            Toast.makeText(this,"Wygrały " + znak, Toast.LENGTH_LONG).show();
        }
        else if(ruch==9) {
            setenabled();
            Toast.makeText(this,"Remis", Toast.LENGTH_LONG).show();
        }
        zapisanyStan[wiersz*3+kolumna]=false;
    }
}