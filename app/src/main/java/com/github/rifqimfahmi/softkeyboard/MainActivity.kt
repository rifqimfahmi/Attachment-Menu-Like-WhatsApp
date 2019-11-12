package com.github.rifqimfahmi.softkeyboard

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MenuEditText.PopupListener {

    lateinit var parentView: View
    lateinit var menuKeyboard: SoftKeyBoardPopup
    lateinit var rootView: RelativeLayout
    lateinit var editText: MenuEditText

    val MENU_TAG = "soft_menu_tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootView = findViewById(R.id.rootView)
        parentView = findViewById<View>(android.R.id.content)
        editText = findViewById(R.id.editText)
        editText.popupListener = this

        menuKeyboard = SoftKeyBoardPopup(this, rootView, editText)

        menu_chat.setOnClickListener {
            toggle()
        }
    }

    private fun toggle() {
        if (menuKeyboard.isShowing) {
            menuKeyboard.dismiss()
        } else {
            menuKeyboard.show()
        }
    }

    override fun onDestroy() {
        menuKeyboard.clear()
        super.onDestroy()
    }

    override fun getPopup(): PopupWindow {
        return menuKeyboard
    }
}
