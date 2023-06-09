package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examen.entities.Users;
import com.example.examen.services.UsersService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarActivity extends AppCompatActivity {
    double x,y;
    private Users user = new Users();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        TextView etNombre = findViewById(R.id.etNombre);
        //TextView etEmail = findViewById(R.id.etEmail);
        //TextView etUsername = findViewById(R.id.etUsername);
        ImageView etFoto = findViewById(R.id.imFotoPokemonActualizar);

        Button btnAtras= findViewById(R.id.btnAtrasActualizar);
        Button btnActualizar = findViewById(R.id.btnActualizar);
        Button btnMapita = findViewById(R.id.btnMapita);

        Intent intent = getIntent();
        int temp = intent.getIntExtra("identificador", 0);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://64781c33362560649a2d370d.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersService servicio = retrofit.create(UsersService.class);
        Call<Users> llamado = servicio.EncontrarContacto(temp);
        llamado.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                user = response.body();
                x= user.latitud;
                y= user.longitud;
                etNombre.setText(user.nombre);
                //etEmail.setText(user.email);
                //etUsername.setText(user.username);
                Picasso.get().load(user.camaraFoto).into(etFoto);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
            }
        });

        btnMapita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MAIN_APP", "latitud: "+x+" longitud: "+y);
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                intent.putExtra("latitud", x);
                intent.putExtra("longitud", y);
                v.getContext().startActivity(intent);
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PrincipalActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActualizarActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        /*btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.nombre = etNombre.getText().toString();
                user.email = etEmail.getText().toString();
                user.username = etUsername.getText().toString();
                user.foto = etFoto.getText().toString();

                Call<Users> actualizar = servicio.EditarContactos(temp, user);
                actualizar.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        Intent intent = new Intent(v.getContext(), RetrofitActivity.class);
                        v.getContext().startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                    }
                });
            }
        });*/
    }
}