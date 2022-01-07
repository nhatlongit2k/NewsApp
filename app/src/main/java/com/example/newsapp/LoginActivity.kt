package com.example.newsapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.databinding.ActivityLoginBinding
import com.example.newsapp.viewmodel.PreferenceViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityLoginBinding
    var numbers_list: ArrayList<String> = ArrayList()
    var passCode: String = ""
    lateinit var num_01: String
    lateinit var num_02: String
    lateinit var num_03: String
    lateinit var num_04: String

    private val preferenceViewModel: PreferenceViewModel by lazy {
        PreferenceViewModel(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        if (preferenceViewModel.getPassCodeFromSharedPreferences().length == 0) {
            binding.tvNoti.setText("Vì vấn đề bảo mật hãy tạo mật khẩu!")
            binding.tvNoti.setTextColor(Color.RED)
        } else {
            binding.tvNoti.setText("Hãy nhập mật khẩu")
            binding.tvNoti.setTextColor(Color.BLACK)
        }
    }

    private fun initView() {
        for (i in 0..binding.layoutNumber.childCount - 1) {
            val child: LinearLayout = binding.layoutNumber.getChildAt(i) as LinearLayout
            for (j in 0..child.childCount - 1) {
                if (child.getChildAt(j) is Button) {
                    child.getChildAt(j).setOnClickListener(this)
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_01 -> {
                numbers_list.add("1")
                passNumber(numbers_list)
            }
            R.id.btn_02 -> {
                numbers_list.add("2")
                passNumber(numbers_list)
            }
            R.id.btn_03 -> {
                numbers_list.add("3")
                passNumber(numbers_list)
            }
            R.id.btn_04 -> {
                numbers_list.add("4")
                passNumber(numbers_list)
            }
            R.id.btn_05 -> {
                numbers_list.add("5")
                passNumber(numbers_list)
            }
            R.id.btn_06 -> {
                numbers_list.add("6")
                passNumber(numbers_list)
            }
            R.id.btn_07 -> {
                numbers_list.add("7")
                passNumber(numbers_list)
            }
            R.id.btn_08 -> {
                numbers_list.add("8")
                passNumber(numbers_list)
            }
            R.id.btn_09 -> {
                numbers_list.add("9")
                passNumber(numbers_list)
            }
            R.id.btn_00 -> {
                numbers_list.add("0")
                passNumber(numbers_list)
            }
            R.id.btn_clear -> {
                numbers_list.clear()
                passNumber(numbers_list)
            }
        }
    }

    private fun passNumber(numbersList: ArrayList<String>) {
        if (numbersList.size == 0) {
            binding.view01.setBackgroundResource(R.drawable.bg_view_grey_oval)
            binding.view02.setBackgroundResource(R.drawable.bg_view_grey_oval)
            binding.view03.setBackgroundResource(R.drawable.bg_view_grey_oval)
            binding.view04.setBackgroundResource(R.drawable.bg_view_grey_oval)
        } else
            when (numbersList.size) {
                1 -> {
                    num_01 = numbersList.get(0)
                    binding.view01.setBackgroundResource(R.drawable.bg_view_blue_oval)
                }
                2 -> {
                    num_02 = numbersList.get(1)
                    binding.view02.setBackgroundResource(R.drawable.bg_view_blue_oval)
                }
                3 -> {
                    num_03 = numbersList.get(2)
                    binding.view03.setBackgroundResource(R.drawable.bg_view_blue_oval)
                }
                4 -> {
                    num_04 = numbersList.get(3)
                    binding.view04.setBackgroundResource(R.drawable.bg_view_blue_oval)
                    passCode = num_01 + num_02 + num_03 + num_04
                    if (preferenceViewModel.getPassCodeFromSharedPreferences().length == 0) {
                        preferenceViewModel.savePasscode(passCode)
                        binding.tvNoti.setText("Tạo mật khẩu thành công! Hãy nhập mật khẩu để mở khóa.")
                        binding.tvNoti.setTextColor(Color.BLACK)
                        numbersList.clear()
                        passNumber(numbersList)
                    } else {
                        checkMatchPassCode()
                    }
                }
            }
    }

    private fun checkMatchPassCode() {
        if (preferenceViewModel.checkPassword(passCode)) {
            Toast.makeText(this, "Mở khóa thành công", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Sai mật khẩu", Toast.LENGTH_SHORT).show()
            numbers_list.clear()
            passNumber(numbers_list)
            binding.tvNoti.setText("Sai mật khẩu! Hãy nhập lại...")
            binding.tvNoti.setTextColor(Color.RED)
        }
    }

    override fun onBackPressed() {

    }
}