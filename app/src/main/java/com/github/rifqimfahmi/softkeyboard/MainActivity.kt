package com.github.rifqimfahmi.softkeyboard

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MenuEditText.PopupListener {

    lateinit var parentView: View
    lateinit var menuKeyboard: SoftKeyBoardPopup
    lateinit var rootView: RelativeLayout
    lateinit var editText: MenuEditText
    lateinit var animationCheckBox: CheckBox
    lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootView = findViewById(R.id.rootView)
        parentView = findViewById<View>(android.R.id.content)
        editText = findViewById(R.id.editText)
        animationCheckBox = findViewById(R.id.animationCheckBox)
        radioGroup = findViewById(R.id.rdGroup)

        editText.popupListener = this

        menuKeyboard = SoftKeyBoardPopup(this, rootView, editText)
        animationCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            menuKeyboard.useAnimation = isChecked
        }

        menu_chat.setOnClickListener {
            toggle()
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val rdButton = group.findViewById<RadioButton>(checkedId)
            val value = Integer.parseInt(rdButton.text.toString())
            menuKeyboard.updateMenuCount(value)
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
