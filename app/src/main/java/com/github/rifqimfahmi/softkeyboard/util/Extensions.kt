package com.github.rifqimfahmi.softkeyboard.util

import android.content.res.Resources

fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()