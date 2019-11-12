package com.github.rifqimfahmi.softkeyboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.view.*
import android.view.WindowManager.LayoutParams
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupWindow

class SoftKeyBoardPopup(
    private val context: Context,
    private val rootView: ViewGroup,
    private val editText: EditText
) : PopupWindow(context), ViewTreeObserver.OnGlobalLayoutListener {

    private lateinit var view: View

    private var isKeyboardOpened = false
    private var showPending = false
    private var DEFAULT_KEYBOARD_HEIGHT =
        (281 * Resources.getSystem().displayMetrics.density).toInt()
    private var keyboardHeight = DEFAULT_KEYBOARD_HEIGHT
    private val KEYBOARD_OFFSET = 100

    init {
        initConfig()
        initAnimation()
        initEditText()
        initKeyboardListener()
        initMenuView()
    }

    private fun initConfig() {
        softInputMode = LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        setSize(LayoutParams.MATCH_PARENT, keyboardHeight)
        setBackgroundDrawable(null)
    }

    private fun initAnimation() {
        animationStyle = R.style.PopupContextAnimation
    }

    private fun initEditText() {
        editText.setOnClickListener {
            showKeyBoard()
        }
    }

    private fun initKeyboardListener() {
        rootView.viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        val screenHeight = getScreenHeight()
        val windowRect = Rect().apply {
            rootView.getWindowVisibleDisplayFrame(this)
        }
        val windowHeight = windowRect.bottom - windowRect.top
        val statusBarHeight = getStatusBarHeight()

        val heightDifference = screenHeight - windowHeight - statusBarHeight

        if (heightDifference > KEYBOARD_OFFSET) {
            keyboardHeight = heightDifference
            setSize(LayoutParams.MATCH_PARENT, keyboardHeight)
            isKeyboardOpened = true
            if (showPending) {
                showPending = false
                show()
            }
        } else {
            dismiss()
            isKeyboardOpened = false
        }
    }

    private fun getScreenHeight(): Int {
        val metrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        windowManager.defaultDisplay.getMetrics(metrics)

        return metrics.heightPixels
    }

    private fun getStatusBarHeight(): Int {
        var height = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = context.resources.getDimensionPixelSize(resourceId)
        }
        return height
    }

    @SuppressLint("InflateParams")
    private fun initMenuView() {
        view = LayoutInflater
            .from(context)
            .inflate(R.layout.menu_soft_keyboard, rootView, false)

        contentView = view
    }

    private fun setSize(width: Int, height: Int) {
        setWidth(width)
        setHeight(height)
    }

    fun show() {
        if (!isKeyboardOpened) {
            showPending = true
            openKeyboard()
        } else {
            showAtLocation(rootView, Gravity.BOTTOM, 0, 0)
        }
    }

    private fun showKeyBoard() {
        if (isShowing) {
            dismiss()
        }
    }

    private fun openKeyboard() {
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun clear() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        } else {
            rootView.viewTreeObserver.removeGlobalOnLayoutListener(this)
        }
    }

}