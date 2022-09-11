package com.example.yana.myshop.ui.users

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.yana.myshop.R
import com.example.yana.myshop.databinding.ActivityLoginBinding
import com.example.yana.myshop.databinding.ActivityMainBinding
import com.example.yana.myshop.databinding.ActivityRegisterBinding
import com.example.yana.myshop.ui.users.admin.AdminAddNewProdActivity
import com.example.yana.myshop.model.Prevalent
import com.example.yana.myshop.model.Users
import com.example.yana.myshop.ui.users.admin.AdminCategoryActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.paperdb.Paper

class LoginActivity : AppCompatActivity(), ValueEventListener {

    private lateinit var binding: ActivityLoginBinding

    private var phone: String? = null
    private var password: String? = null

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("message")

    private var parentDbName: String? = "User"
    private lateinit var loadingBar: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingBar = ProgressDialog(this)
        Paper.init(this)
        setupListeners()
    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            phone = binding.loginPhoneInput.text.toString()
            password = binding.loginPasswordInput.text.toString()

            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
            } else {
                loadingBar.setTitle("Вход в приложение")
                loadingBar.setMessage("Пожалуйста подождите")
                loadingBar.setCanceledOnTouchOutside(false)
                loadingBar.show()
                validateUser(phone, password)
            }
        }
        binding.adminPanelLink.setOnClickListener {
            binding.adminPanelLink.isVisible = false
            binding.notAdminPanelLink.isVisible = true
            binding.loginButton.text = "Вход для админа"
            parentDbName = "Admin"
        }
        binding.notAdminPanelLink.setOnClickListener {
            binding.adminPanelLink.isVisible = true
            binding.notAdminPanelLink.isVisible = false
            binding.loginButton.text = "Войти"
            parentDbName = "User"
        }
    }

    fun validateUser(phone: String?, password: String?) {
        if (binding.loginChekBox.isChecked) {
            Paper.book().write(Prevalent::userPhoneKey.toString(), phone.toString())
            Paper.book().write(Prevalent::userPasswordKey.toString(), password.toString())
        }

        myRef.addListenerForSingleValueEvent(this)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.child(parentDbName.toString()).child(phone.toString()).exists()) {
            val usersData = snapshot.child(parentDbName.toString()).child(phone.toString())
                .getValue(Users::class.java)
            if (usersData?.phone.equals(phone)) {
                if (usersData?.password.equals(password)) {

                    if (parentDbName.equals("User")) {
                        loadingBar.dismiss()
                        Toast.makeText(this, "Успешный вход", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else if (parentDbName.equals("Admin")) {
                        loadingBar.dismiss()
                        Toast.makeText(this, "Успешный вход", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, AdminCategoryActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }


    override fun onCancelled(error: DatabaseError) {
        Log.d("sadsadasdsa", "sdasdasdasdsadasd")
    }
}