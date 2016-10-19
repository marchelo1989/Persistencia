package cl.duoc.android.persistencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MantenedorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenedor);

        SharedPreferences sp = getSharedPreferences( getString(R.string.archivoPreferencias), Context.MODE_PRIVATE );
        String usuario = sp.getString("usuario", "");
        Log.v("Usuario", "usuario: "+usuario);

        TextView tvConectadoComo = (TextView) findViewById(R.id.tvConectacoComo);
        if(!usuario.isEmpty()) {
            tvConectadoComo.setText( String.format("Conectado como %s", usuario) );
        } else {
            tvConectadoComo.setText( "Bienvenido usuario" );
        }

    }
}
