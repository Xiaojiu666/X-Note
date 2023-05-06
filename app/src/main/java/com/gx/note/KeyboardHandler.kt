package com.gx.note

import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class KeyboardHandler(private val view: View) {
    private var keyboardSize = 0
    private var isKeyboardOpen = false
    fun handleKeyboard() {
        handleKeyboardPopUpEvent()
        handleKeyboardAnimation()
    }

    private fun handleKeyboardPopUpEvent() {
        ViewCompat
            .setOnApplyWindowInsetsListener(
                view
            ) { view, insets ->
                val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                Log.e("handleKeyboardPopUpEvent", "handleKeyboard $bottom")
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    view.updatePadding(bottom = bottom)
                }
                if (bottom == 0) {
                    view.updatePadding(bottom = bottom)
                }
                if (bottom != 0) {
                    keyboardSize = bottom
                }
                isKeyboardOpen = bottom != 0
                insets
            }
    }

    private fun handleKeyboardAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ViewCompat.setWindowInsetsAnimationCallback(
                view,
                object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
                    override fun onProgress(
                        insets: WindowInsetsCompat,
                        runningAnimations: MutableList<WindowInsetsAnimationCompat>
                    ): WindowInsetsCompat {
                        val imeAnimation = runningAnimations.find {
                            it.typeMask and WindowInsetsCompat.Type.ime() != 0
                        } ?: return insets
                        if (isKeyboardOpen) {
                            val percent =
                                (keyboardSize * (imeAnimation.interpolatedFraction)).toInt()
                            view.updatePadding(bottom = percent)
                        }
                        return insets
                    }
                }
            )
        }
    }
}
