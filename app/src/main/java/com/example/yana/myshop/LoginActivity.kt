package com.example.yana.myshop

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yana.myshop.model.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity(), ValueEventListener {

    private var phone: String? = null
    private var password: String? = null

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("message")

    private var parentDbName: String? = "users"

    private lateinit var loginPhoneInp: EditText
    private lateinit var loginPasswordInp: EditText
    private lateinit var loginBtn: Button
    private lateinit var loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPhoneInp = findViewById(R.id.loginPhoneInput)
        loginPasswordInp = findViewById(R.id.loginPasswordInput)
        loginBtn = findViewById(R.id.loginButton)
        loadingBar = ProgressDialog(this)
        setupListeners()
    }

    private fun setupListeners() {
        loginBtn.setOnClickListener {

//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)

            phone = loginPhoneInp.text.toString()
            password = loginPasswordInp.text.toString()

            if(TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show()
            }
            else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
        }else{
            loadingBar.setTitle("Вход в приложение")
            loadingBar.setMessage("Пожалуйста подождите")
            loadingBar.setCanceledOnTouchOutside(false)
            loadingBar.show()
            validateUser(phone, password)
       }
        }
    }

    fun validateUser( phone: String?, password: String?){
        myRef.addListenerForSingleValueEvent(this)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.child(parentDbName.toString()).child(phone.toString()).exists()){
             val usersData = snapshot.child(parentDbName.toString()).child(phone.toString()).value
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
            Log.d("sadsadasdsa", "sdasdasdasdsadasd")
        }else{
//            loadingBar.dismiss()
//            Toast.makeText(this, "Аккаунт с номером" + phone + "не существует", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
            Log.d("sadsadasdsa", "sdasdasdasdsadasd")
        }
    }


    override fun onCancelled(error: DatabaseError) {
//        Log.d("sadsadasdsa", "sdasdasdasdsadasd")
    }
}