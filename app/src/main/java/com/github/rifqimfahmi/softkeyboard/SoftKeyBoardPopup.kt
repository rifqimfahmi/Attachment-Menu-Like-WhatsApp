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
import androidx.core.animation.addListener
import kotlin.math.sqrt

class SoftKeyBoardPopup(
    private val context: Context,
    private val rootView: ViewGroup,
    private val editText: EditText,
    private val anchorView: View
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
        initEditText()
        initKeyboardListener()
        initMenuView()
    }

    private fun initConfig() {
        softInputMode = LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        setSize(LayoutParams.MATCH_PARENT, keyboardHeight)
        setBackgroundDrawable(null)
    }

    private fun initEditText() {
        editText.setOnClickListener {
            showKeyBoard()
        }
    }

    private fun initKeyboardListener() {
        rootView.viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    @SuppressLint("InflateParams")
    private fun initMenuView() {
        view = LayoutInflater
            .from(context)
            .inflate(R.layout.menu_soft_keyboard, rootView, false)

        view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            revealView()
        }

        contentView = view
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
            isKeyboardOpened = false
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
            dismiss()
        }
    }

    override fun dismiss() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            super.dismiss()
            return
        }
        if (!isShowing) return
        val centerX = calculateAnchorCenter()
        val endRadius = calculateRadius(centerX)
        val animator = ViewAnimationUtils.createCircularReveal(
            contentView, centerX, 0, endRadius, 0f
        )
        animator.addListener(onEnd = {
            super.dismiss()
        })
        animator.start()
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

    private fun setSize(width: Int, height: Int) {
        setWidth(width)
        setHeight(height)
    }

    fun show() {
        if (!isKeyboardOpened) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                showAtLocation(rootView, Gravity.BOTTOM, 0, 0)
                return
            }
            showPending = true
            openKeyboard()
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                hideKeyboard()
            }
            showAtLocation(rootView, Gravity.BOTTOM, 0, 0)
        }
    }

    private fun hideKeyboard() {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    private fun revealView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
        val centerX = calculateAnchorCenter()
        val endRadius = calculateRadius(centerX)
        val animator = ViewAnimationUtils.createCircularReveal(
            contentView, centerX, 0, 0f, endRadius
        )
        animator.start()
    }

    private fun calculateRadius(centerX: Int): Float {
        val h = contentView.height
        return sqrt((centerX * centerX + h * h).toDouble()).toFloat()
    }

    private fun calculateAnchorCenter(): Int {
        val viewCenter = anchorView.width / 2
        return anchorView.left + viewCenter
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

    fun updateMenuCount(value: Int) {
        val rv: MenuRecyclerView = view.findViewById(R.id.rvMenu)
        rv.updateDataCount(value)
    }
}