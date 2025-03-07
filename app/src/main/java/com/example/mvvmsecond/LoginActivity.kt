package com.example.mvvmsecond

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsecond.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        viewModel.user.observe(this) { user ->
                if (user !=null){
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java)).apply {
                        finish()
                    }
                }

        }

        binding.loginButton.setOnClickListener {
            val email=binding.emailEditText.text.toString()
            val password=binding.passwordEditText.text.toString()
            viewModel.login(email,password)
        }

        binding.resisterButtonPage.setOnClickListener {
            val intent = Intent(this,ResisterActivity::class.java)
            startActivity(intent)
        }



    }

}