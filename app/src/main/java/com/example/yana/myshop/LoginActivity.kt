package com.example.yana.myshop

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginPhoneInp: EditText
    private lateinit var loginPasswordInp: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPhoneInp = findViewById(R.id.loginPhoneInput)
        loginPasswordInp = findViewById(R.id.loginPasswordInput)
        loginBtn = findViewById(R.id.loginButton)
    }
}