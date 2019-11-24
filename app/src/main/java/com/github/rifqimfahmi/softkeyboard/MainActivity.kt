package com.github.rifqimfahmi.softkeyboard

import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.rifqimfahmi.softkeyboard.adapter.ChatAdapter
import com.github.rifqimfahmi.softkeyboard.widget.MenuEditText
import com.github.rifqimfahmi.softkeyboard.widget.SoftKeyBoardPopup
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MenuEditText.PopupListener {

    lateinit var parentView: View
    lateinit var menuKeyboard: SoftKeyBoardPopup
    lateinit var rootView: ConstraintLayout
    lateinit var editText: MenuEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDummyChat()

        rootView = findViewById(R.id.rootView)
        parentView = findViewById<View>(android.R.id.content)
        editText = findViewById(R.id.editText)

        editText.popupListener = this

        menuKeyboard = SoftKeyBoardPopup(
            this,
            rootView,
            editText,
            editText,
            menuChatContainer
        )

        menu_chat.setOnClickListener {
            toggle()
        }
    }

    private fun initDummyChat() {
        with (rvChat) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, true)
            adapter = ChatAdapter()
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
