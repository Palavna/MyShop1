package com.example.yana.myshop.ui.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.yana.myshop.R
import com.example.yana.myshop.databinding.ActivityHomeBinding
import com.example.yana.myshop.databinding.ActivityMainBinding
import com.example.yana.myshop.databinding.ActivityRegisterBinding
import io.paperdb.Paper

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.button.setOnClickListener {
            Paper.book().destroy()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}