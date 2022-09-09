package com.example.yana.myshop.ui.users.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yana.myshop.R
import com.example.yana.myshop.databinding.ActivityAdminCategoryBinding
import com.example.yana.myshop.databinding.ActivityMainBinding
import com.example.yana.myshop.databinding.ActivityRegisterBinding
import com.example.yana.myshop.ui.users.RegisterActivity

class AdminCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.car.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "car")
            startActivity(intent)
        }
        binding.moto.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "moto")
            startActivity(intent)
        }
        binding.boat.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "boat")
            startActivity(intent)
        }
        binding.service.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "service")
            startActivity(intent)
        }
        binding.dress.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "dress")
            startActivity(intent)
        }
        binding.shoes.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "shoes")
            startActivity(intent)
        }
        binding.coat.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "coat")
            startActivity(intent)
        }
        binding.hats.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "hats")
            startActivity(intent)
        }
        binding.photo.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "photo")
            startActivity(intent)
        }
        binding.pc.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "pc")
            startActivity(intent)
        }
        binding.camera.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "camera")
            startActivity(intent)
        }
        binding.fridge.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "fridge")
            startActivity(intent)
        }
        binding.cycling.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "cycling")
            startActivity(intent)
        }
        binding.book.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "book")
            startActivity(intent)
        }
        binding.stamp.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "stamp")
            startActivity(intent)
        }
        binding.guitar.setOnClickListener {
            val intent = Intent(this, AdminAddNewProdActivity::class.java)
            intent.putExtra("category", "guitar")
            startActivity(intent)
        }
    }
}