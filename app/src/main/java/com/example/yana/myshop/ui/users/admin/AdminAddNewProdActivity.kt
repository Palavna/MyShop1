package com.example.yana.myshop.ui.users.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.yana.myshop.R
import com.example.yana.myshop.databinding.ActivityAdminAddNewProdBinding
import com.example.yana.myshop.databinding.ActivityMainBinding
import com.example.yana.myshop.databinding.ActivityRegisterBinding

class AdminAddNewProdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminAddNewProdBinding
    private var categoryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddNewProdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryName = intent.extras?.get("category").toString()

        Toast.makeText(this, "Выбрана категория$categoryName", Toast.LENGTH_SHORT).show()
    }
}