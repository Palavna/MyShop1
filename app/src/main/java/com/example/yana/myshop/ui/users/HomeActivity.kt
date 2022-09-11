package com.example.yana.myshop.ui.users

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yana.myshop.databinding.ActivityHomeBinding
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