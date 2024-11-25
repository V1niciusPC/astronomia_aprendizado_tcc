package com.example.astronomiaaprendizado.Inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.astronomiaaprendizado.MainActivity;
import com.example.astronomiaaprendizado.R;
import com.example.astronomiaaprendizado.databinding.ActivityMainBinding;
import com.example.astronomiaaprendizado.databinding.ActivityTelaLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class TelaLogin extends AppCompatActivity {
    private ActivityTelaLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaLoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        binding.textCriarConta.setOnClickListener(view -> {
            startActivity(new Intent(this, TelaCadastro.class));
            finish();
        });
        binding.btnLogin.setOnClickListener( v -> validalogin());
    }
    private void validalogin(){
        String email = binding.editLogin.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if (!email.isEmpty()){
            if (!senha.isEmpty()){
                binding.carregando.setVisibility(View.VISIBLE);
                loginFirebase(email,senha);

            }else {
                Toast.makeText(this, "Campo de senha vazio", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Campo de Email vazio", Toast.LENGTH_SHORT).show();
        }


    }

    private void loginFirebase(String email, String senha){
        mAuth.signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                binding.carregando.setVisibility(View.GONE);
                Toast.makeText(this, "Ocorreu um Erro", Toast.LENGTH_SHORT).show();
            }

        });

    }
}