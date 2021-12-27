package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var tv_noti: TextView
    lateinit var view_01: View
    lateinit var view_02: View
    lateinit var view_03: View
    lateinit var view_04: View
    lateinit var btn_01: Button
    lateinit var btn_02: Button
    lateinit var btn_03: Button
    lateinit var btn_04: Button
    lateinit var btn_05: Button
    lateinit var btn_06: Button
    lateinit var btn_07: Button
    lateinit var btn_08: Button
    lateinit var btn_09: Button
    lateinit var btn_00: Button
    lateinit var btn_clear: Button

    var numbers_list: ArrayList<String> = ArrayList()
    var passCode: String = ""
    lateinit var num_01: String
    lateinit var num_02: String
    lateinit var num_03: String
    lateinit var num_04: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        if (getPassCodeFromSharedPreferences().length==0){
            tv_noti.setText("Vì vấn đề bảo mật hãy tạo mật khẩu!")
            tv_noti.setTextColor(Color.RED)
        } else{
            tv_noti.setText("Hãy nhập mật khẩu")
            tv_noti.setTextColor(Color.BLACK)
        }
    }

    private fun initView() {
        tv_noti = findViewById(R.id.tv_noti)
        view_01 = findViewById(R.id.view_01)
        view_02 = findViewById(R.id.view_02)
        view_03 = findViewById(R.id.view_03)
        view_04 = findViewById(R.id.view_04)

        btn_01 = findViewById(R.id.btn_01)
        btn_02 = findViewById(R.id.btn_02)
        btn_03 = findViewById(R.id.btn_03)
        btn_04 = findViewById(R.id.btn_04)
        btn_05 = findViewById(R.id.btn_05)
        btn_06 = findViewById(R.id.btn_06)
        btn_07 = findViewById(R.id.btn_07)
        btn_08 = findViewById(R.id.btn_08)
        btn_09 = findViewById(R.id.btn_09)
        btn_00 = findViewById(R.id.btn_00)
        btn_clear = findViewById(R.id.btn_clear)

        btn_01.setOnClickListener(this)
        btn_02.setOnClickListener(this)
        btn_03.setOnClickListener(this)
        btn_04.setOnClickListener(this)
        btn_05.setOnClickListener(this)
        btn_06.setOnClickListener(this)
        btn_07.setOnClickListener(this)
        btn_08.setOnClickListener(this)
        btn_09.setOnClickListener(this)
        btn_00.setOnClickListener(this)
        btn_clear.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_01->{
                numbers_list.add("1")
                passNumber(numbers_list)
            }
            R.id.btn_02->{
                numbers_list.add("2")
                passNumber(numbers_list)
            }
            R.id.btn_03->{
                numbers_list.add("3")
                passNumber(numbers_list)
            }
            R.id.btn_04->{
                numbers_list.add("4")
                passNumber(numbers_list)
            }
            R.id.btn_05->{
                numbers_list.add("5")
                passNumber(numbers_list)
            }
            R.id.btn_06->{
                numbers_list.add("6")
                passNumber(numbers_list)
            }
            R.id.btn_07->{
                numbers_list.add("7")
                passNumber(numbers_list)
            }
            R.id.btn_08->{
                numbers_list.add("8")
                passNumber(numbers_list)
            }
            R.id.btn_09->{
                numbers_list.add("9")
                passNumber(numbers_list)
            }
            R.id.btn_00->{
                numbers_list.add("0")
                passNumber(numbers_list)
            }
            R.id.btn_clear->{
                numbers_list.clear()
                passNumber(numbers_list)
            }
        }
    }

    private fun passNumber(numbersList: ArrayList<String>) {
        if(numbersList.size == 0){
            view_01.setBackgroundResource(R.drawable.bg_view_grey_oval)
            view_02.setBackgroundResource(R.drawable.bg_view_grey_oval)
            view_03.setBackgroundResource(R.drawable.bg_view_grey_oval)
            view_04.setBackgroundResource(R.drawable.bg_view_grey_oval)
        }else
            when(numbersList.size){
                1 ->{
                    num_01 = numbersList.get(0)
                    view_01.setBackgroundResource(R.drawable.bg_view_blue_oval)
                }
                2->{
                    num_02 = numbersList.get(1)
                    view_02.setBackgroundResource(R.drawable.bg_view_blue_oval)
                }
                3->{
                    num_03 = numbersList.get(2)
                    view_03.setBackgroundResource(R.drawable.bg_view_blue_oval)
                }
                4->{
                    num_04 = numbersList.get(3)
                    view_04.setBackgroundResource(R.drawable.bg_view_blue_oval)
                    passCode = num_01+num_02+num_03+num_04
                    if(getPassCodeFromSharedPreferences().length == 0){
                        savePasscode(passCode)
                        tv_noti.setText("Tạo mật khẩu thành công! Hãy nhập mật khẩu để mở khóa.")
                        tv_noti.setTextColor(Color.BLACK)
                        numbersList.clear()
                        passNumber(numbersList)
                    }else{
                        checkMatchPassCode()
                    }
                }
            }
    }

    private fun checkMatchPassCode() {
        if (getPassCodeFromSharedPreferences().equals(passCode)){
            Toast.makeText(this, "Mở khóa thành công", Toast.LENGTH_SHORT).show()
            val intent: Intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }else{
            Toast.makeText(this, "Sai mật khẩu", Toast.LENGTH_SHORT).show()
            numbers_list.clear()
            passNumber(numbers_list)
            tv_noti.setText("Sai mật khẩu! Hãy nhập lại...")
            tv_noti.setTextColor(Color.RED)
        }
    }

    private fun savePasscode(passCode: String): SharedPreferences.Editor{
        var sharedPreferences: SharedPreferences = getSharedPreferences("pass_code_pref", Context.MODE_PRIVATE)
        var edit: SharedPreferences.Editor = sharedPreferences.edit()
        edit.putString("pass_code", passCode)
        edit.commit()

        return edit
    }

    private fun getPassCodeFromSharedPreferences(): String{
        var sharedPreferences: SharedPreferences = getSharedPreferences("pass_code_pref", Context.MODE_PRIVATE)
        var passCode: String = sharedPreferences.getString("pass_code", "").toString()
        return passCode
    }
}