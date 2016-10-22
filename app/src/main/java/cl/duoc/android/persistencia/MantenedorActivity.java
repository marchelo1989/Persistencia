package cl.duoc.android.persistencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MantenedorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenedor);

        SharedPreferences sp = getSharedPreferences(getString(R.string.archivoPreferencias), Context.MODE_PRIVATE);
        String usuario = sp.getString("usuario", "");
        Log.v("Usuario", "usuario: " + usuario);

        TextView tvConectadoComo = (TextView) findViewById(R.id.tvConectacoComo);
        if (!usuario.isEmpty()) {
            tvConectadoComo.setText(String.format("Conectado como %s", usuario));
        } else {
            tvConectadoComo.setText("Bienvenido usuario");
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private File crearDirectorioYArchivo(File file) {
        if (!file.getParentFile().mkdirs()) {
            Log.e("File", "Directory not created");
        }
        return file;
    }

    private File guardarCsvInternal(String filename) {
        File file = new File(getFilesDir(), filename);
        crearDirectorioYArchivo(file);
        return file;
    }

    private File guardarCsvExternal(String filename) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
        crearDirectorioYArchivo(file);
        return file;
    }

    public void descargarCSV(View v) {
        Toast.makeText(this, "Guardando BD a archivo CSV...", Toast.LENGTH_LONG).show();
        // la alternativa a getFilesDir() es getCacheDir()
        String filename = "TODOs.csv";

        // Usuar cualquier alternativa - Internal o External
        File file = guardarCsvExternal(filename);
        //File file = guardarCsvInternal(filename);

        String texto = "Hola mundo";
        FileOutputStream fos;
        try {
            // para escribir en archivo interno
            //fos = openFileOutput(filename, Context.MODE_APPEND);

            // new FileOutputStream funciona con external
            fos = new FileOutputStream(file, true);

            fos.write(texto.getBytes());
            fos.close();
            Toast.makeText(this, "Archivo guardado en "+file.getPath(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Log.e("Error", "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error", "IOException");
            e.printStackTrace();
        }
    }
}
