package com.example.yana.myshop

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RegisterActivity : AppCompatActivity(), ValueEventListener {

   private lateinit var phone: String
    private lateinit var userName: String
    private lateinit var password: String

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("message")

    private lateinit var registerBtn: Button
    private lateinit var userNameInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerBtn = findViewById(R.id.registerButton)
        userNameInput = findViewById(R.id.registerUserName)
        phoneInput = findViewById(R.id.registerPhoneInput)
        passwordInput = findViewById(R.id.registerPasswordInput)
        loadingBar = ProgressDialog(this)
        setupListeners()
    }

    private fun setupListeners() {
        registerBtn.setOnClickListener {
            val userName = userNameInput.text.toString()
            val phone = phoneInput.text.toString()
            val password = passwordInput.text.toString()

            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show()
            }
            else if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show()
            }
            else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
            }else{
                loadingBar.setTitle("Создание аккаунта")
                loadingBar.setMessage("Пожалуйста подождите")
                loadingBar.setCanceledOnTouchOutside(false)
                loadingBar.show()
                validatePhone(userName, phone, password)
            }
        }
    }
    fun validatePhone(userName: String, phone: String, password: String){
        myRef.addListenerForSingleValueEvent(this)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        if (!(snapshot.child("").child(phone).exists())){
           val userDataMap = HashMap<String, Any>()
            userDataMap.put("phone", phone)
            userDataMap.put("userName", userName)
            userDataMap.put("password", password)

            myRef.child("User").child(phone).updateChildren(userDataMap)
                .addOnCompleteListener{
                  if(it.isSuccessful){
                      loadingBar.dismiss()
                      Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                      val intent = Intent(this, LoginActivity::class.java)
                      startActivity(intent)
                  }else{
                      loadingBar.dismiss()
                      Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
                  }
                }

        }else{
            loadingBar.dismiss()
            Toast.makeText(this, "Номер" + phone + "Уже зарегистрирован", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCancelled(error: DatabaseError) {

    }

}