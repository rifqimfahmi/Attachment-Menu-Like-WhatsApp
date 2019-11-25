package com.github.rifqimfahmi.softkeyboard.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.os.Build
import android.view.*
import android.view.WindowManager.LayoutParams
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupWindow
import androidx.core.animation.addListener
import com.github.rifqimfahmi.softkeyboard.R
import com.github.rifqimfahmi.softkeyboard.adapter.GridMenuAdapter
import com.github.rifqimfahmi.softkeyboard.util.toPx
import kotlin.math.sqrt

class SoftKeyBoardPopup(
    private val context: Context,
    private val rootView: ViewGroup,
    private val editText: EditText,
    private val anchorView: View,
    private val triggerView: View
) : PopupWindow(context), ViewTreeObserver.OnGlobalLayoutListener,
    GridMenuAdapter.GridMenuListener {

    private lateinit var view: View

    private var DEFAULT_KEYBOARD_HEIGHT = 281.toPx()
    private val KEYBOARD_OFFSET = 100
    private val defaultHorizontalMargin = 16.toPx()
    private val defaultBottomMargin = 6.toPx()

    private var isKeyboardOpened = false
    private var isShowAtTop = false
    private var isDismissing = false
    private var keyboardHeight = DEFAULT_KEYBOARD_HEIGHT

    init {
        initConfig()
        initKeyboardListener()
        initMenuView()
    }

    private fun initConfig() {
        softInputMode = LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        setSize(LayoutParams.MATCH_PARENT, keyboardHeight)
        setBackgroundDrawable(null)
        isOutsideTouchable = true
        setTouchInterceptor { _, event ->
            if (event.action == MotionEvent.ACTION_OUTSIDE) {
                dismiss()
                return@setTouchInterceptor true
            }
            return@setTouchInterceptor false
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
            isKeyboardOpened = true
        } else {
            isKeyboardOpened = false
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
            dismiss()
        }
    }

    @SuppressLint("InflateParams")
    private fun initMenuView() {
        view = LayoutInflater
            .from(context)
            .inflate(R.layout.menu_soft_keyboard, rootView, false)

        view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            revealView()
        }

        view.findViewById<MenuRecyclerView>(R.id.rvMenu).apply {
            addMenuClickListener(this@SoftKeyBoardPopup)
        }

        contentView = view
    }

    fun show() {
        if (!isKeyboardOpened) {
            showAtTop()
        } else {
            showOverKeyboard()
        }
    }

    override fun dismiss() {
        if (isDismissing || !isShowing) return
        isDismissing = true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            super.dismiss()
            return
        }
        val centerX = calculateCenterX()
        val endRadius = calculateRadius(centerX)
        val centerY = calculateCenterY()
        val animator = ViewAnimationUtils.createCircularReveal(
            contentView, centerX, centerY, endRadius, 0f
        )
        animator.addListener(onEnd = {
            super.dismiss()
            isDismissing = false
        })
        animator.start()
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
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

    private fun showAtTop() {
        isShowAtTop = true
        isFocusable = true
        setSize(rootView.width - defaultHorizontalMargin, keyboardHeight)
        val windowRect = Rect().apply {
            rootView.getWindowVisibleDisplayFrame(this)
        }
        val y =
            windowRect.bottom - keyboardHeight - (rootView.bottom - anchorView.top) - defaultBottomMargin
        showAtLocation(rootView, Gravity.TOP, 0, y)
    }

    private fun showOverKeyboard() {
        isShowAtTop = false
        isFocusable = false
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            hideKeyboard()
        }
        setSize(LayoutParams.MATCH_PARENT, keyboardHeight)
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0)
    }

    private fun hideKeyboard() {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    private fun revealView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
        val centerX = calculateCenterX()
        val centerY = calculateCenterY()
        val endRadius = calculateRadius(centerX)
        val animator = ViewAnimationUtils.createCircularReveal(
            contentView, centerX, centerY, 0f, endRadius
        )
        animator.start()
    }

    private fun calculateCenterY(): Int {
        var centerY = 0
        if (isShowAtTop) {
            centerY = view.bottom
        }
        return centerY
    }

    private fun calculateRadius(centerX: Int): Float {
        val h = contentView.height
        return sqrt((centerX * centerX + h * h).toDouble()).toFloat()
    }

    private fun calculateCenterX(): Int {
        val viewCenter = triggerView.width / 2
        return triggerView.left + viewCenter
    }

    fun clear() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        } else {
            rootView.viewTreeObserver.removeGlobalOnLayoutListener(this)
        }
    }

    override fun dismissPopup() {
        dismiss()
    }
}