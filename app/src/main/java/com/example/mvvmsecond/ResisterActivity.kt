package com.example.mvvmsecond

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsecond.databinding.ActivityResisterBinding

class ResisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityResisterBinding
    lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.user.observe(this, Observer {
            if (it != null){
                Toast.makeText(this, "Resister Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LoginActivity::class.java)).apply {
                    finish()
                }
            }
            Toast.makeText(this, "${it.name}", Toast.LENGTH_SHORT).show()
        })

        binding.resisterButton.setOnClickListener {
            val name = binding.resisterNameEditText.text.toString()
            val email = binding.resisterEmailEditText.text.toString()
            val password = binding.resisterPasswordEditText.text.toString()
            viewModel.resister(name, email, password)


        }
        binding.loginButtonPage.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


    }
}