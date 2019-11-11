package com.github.rifqimfahmi.softkeyboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.rifqimfahmi.softkeyboard.fragment.SoftKeyBoardMenu
import kotlinx.android.synthetic.main.activity_main.*
import android.app.Activity
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager



class MainActivity : AppCompatActivity() {

    lateinit var parentView: View

    val MENU_TAG = "soft_menu_tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parentView = findViewById<View>(android.R.id.content)

        parentView.viewTreeObserver.addOnGlobalLayoutListener {
            var height = 0
            val screenSize = Point()
            getWindowManager().getDefaultDisplay().getSize(screenSize)

            val rect = Rect()
            parentView.getWindowVisibleDisplayFrame(rect)

            // REMIND, you may like to change this using the fullscreen size of the phone
            // and also using the status bar and navigation bar heights of the phone to calculate
            // the keyboard height. But this worked fine on a Nexus.
            val keyboardHeight = screenSize.y - rect.bottom

            if (keyboardHeight > 0 ) {
                hideMenu()
            }
            //                if (keyboardHeight == 0) {
            //                    showMenu()
            //                height = 0
            //                }
            //            } else {
            //                height = keyboardHeight
            //            }
            //            Log.d("ASD", "Asd")
        }

        menu_chat.setOnClickListener {
            toggle()
        }
    }

    fun toggle() {
        val screenSize = Point()
        getWindowManager().getDefaultDisplay().getSize(screenSize)
        val rect = Rect()
        parentView.getWindowVisibleDisplayFrame(rect)

        // REMIND, you may like to change this using the fullscreen size of the phone
        // and also using the status bar and navigation bar heights of the phone to calculate
        // the keyboard height. But this worked fine on a Nexus.
        val keyboardHeight = screenSize.y - rect.bottom

        if (keyboardHeight == 0) {
            showMenu()
            return
//                height = 0
        }

        parentView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                var height = 0
                val screenSize = Point()
                getWindowManager().getDefaultDisplay().getSize(screenSize)

                val rect = Rect()
                parentView.getWindowVisibleDisplayFrame(rect)

                // REMIND, you may like to change this using the fullscreen size of the phone
                // and also using the status bar and navigation bar heights of the phone to calculate
                // the keyboard height. But this worked fine on a Nexus.
                val keyboardHeight = screenSize.y - rect.bottom

                if (keyboardHeight == 0) {
                    showMenu()
//                height = 0
                }
//            } else {
//                height = keyboardHeight
//            }
//            Log.d("ASD", "Asd")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    parentView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    parentView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
            }

        })
//        parentView.viewTreeObserver.addOnGlobalLayoutListener {
//        }
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun hideMenu() {
        val fragment = supportFragmentManager.findFragmentByTag(MENU_TAG) ?: return
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
        supportFragmentManager.popBackStack()
        Log.d("asd", "asd")
    }

    private fun showMenu() {
        if (supportFragmentManager.findFragmentByTag(MENU_TAG) != null) {
            return
        }
        val menuFragment = SoftKeyBoardMenu()
        supportFragmentManager.beginTransaction()
            .replace(R.id.soft_menu, menuFragment, MENU_TAG)
            .addToBackStack("soft menu")
            .commit()
    }
}
