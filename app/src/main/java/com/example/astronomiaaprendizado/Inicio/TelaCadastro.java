package com.example.astronomiaaprendizado.Inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.astronomiaaprendizado.MainActivity;
import com.example.astronomiaaprendizado.R;
import com.example.astronomiaaprendizado.databinding.ActivityTelaCadastroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TelaCadastro extends AppCompatActivity {
    private ActivityTelaCadastroBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaCadastroBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        binding.btnConfirmar.setOnClickListener(v -> validaDados());
        binding.btnVoltar.setOnClickListener(view -> {
            startActivity(new Intent(this, TelaLogin.class));
            finish();
        });

    }
    private void validaDados(){
        String email = binding.cadastroLogin.getText().toString().trim();
        String senha = binding.cadastroSenha.getText().toString().trim();
        String confirmesenha = binding.confirmaSenha.getText().toString().trim();
        String senhaArmazenada = "null";

       if (!email.isEmpty()){
           if (!senha.isEmpty()){

           }else {
               Toast.makeText(this, "Campo de senha vazio", Toast.LENGTH_SHORT).show();
           }

       }else{
           Toast.makeText(this, "Campo de Email vazio", Toast.LENGTH_SHORT).show();
       }

        if (senha.equals(confirmesenha)){
            senhaArmazenada = senha;
            binding.progessbarCarregar.setVisibility(View.VISIBLE);
            contaFirebase(email,senhaArmazenada);
            finish();
        }else {
            Toast.makeText(this, "Senhas Diferentes", Toast.LENGTH_SHORT).show();
        }


    }

    private void contaFirebase(String email, String senhaArmazenada){
        mAuth.createUserWithEmailAndPassword(
                email, senhaArmazenada
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                finish();
                startActivity(new Intent(this, TelaLogin.class));
            } else {
                binding.progessbarCarregar.setVisibility(View.GONE);
                Toast.makeText(this, "Ocorreu um Erro", Toast.LENGTH_SHORT).show();
            }

        });

    }
}