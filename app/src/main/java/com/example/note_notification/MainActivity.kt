package com.example.note_notification

import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.String as String1

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mNotificationHelper: NotificationHelper
    private var priority: Int = 0


    override fun onClick(view: View?) {
        val num = System.currentTimeMillis().toInt()
        when (view?.id) {
            R.id.btnAddNote -> sendNotification(num)
            R.id.btnLowPriority -> priority = 1
            R.id.btnMediumPriority -> priority = 2
            R.id.btnHighPriority -> priority = 3
            else -> Log.e("MainActivity", "No Action")
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun sendNotification(id: Int) {
        val mMessage = etNote.text.toString().trim()

        when (priority) {
            1 -> {
//                lblPriority.text =
                mNotificationHelper.notify(id, mNotificationHelper
                        .getNotificationLowPriority(mMessage))
            }
            2 -> {
//                lblPriority.text =
                mNotificationHelper.notify(id, mNotificationHelper
                        .getNotificationMediumPriority(id, mMessage))
            }
            3 -> {
//                lblPriority.text =
                mNotificationHelper.notify(id, mNotificationHelper
                        .getNotificationHighPriority(id, mMessage))
            }
            else ->
                mNotificationHelper.notify(id, mNotificationHelper
                        .getNotificationLowPriority(mMessage))
        }
        etNote?.text?.clear()
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lblWordsCount.text = String1.format(getString(R.string.lbl_word_count), 0)
        mNotificationHelper = NotificationHelper(this)
        btnAddNote.setOnClickListener(this)
        btnLowPriority.setOnClickListener(this)
        btnHighPriority.setOnClickListener(this)
        btnMediumPriority.setOnClickListener(this)
        buttonEffect(btnAddNote)
        buttonEffect(btnLowPriority)
        buttonEffect(btnMediumPriority)
        buttonEffect(btnHighPriority)
        etNote.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lblWordsCount.text = String1.format(getString(R.string.lbl_word_count),
                        s?.length.toString())
            }
        })
    }

    fun buttonEffect(button: View) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(Color.DKGRAY,
                            PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }
    }

}


