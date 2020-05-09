package com.akexorcist.example.handlestatechangesincustomview.inherited

import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.customview.view.AbsSavedState
import com.akexorcist.example.handlestatechangesincustomview.R
import kotlinx.android.synthetic.main.view_post_regular.view.*

class RegularPostView : BasePostView {
    private var dividerColorResId: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun getLayout(): Int = R.layout.view_post_regular

    override fun initView() {}

    override fun initStyleable(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RegularPostView)
        dividerColorResId = typedArray.getResourceId(R.styleable.RegularPostView_ipv_dividerColor, R.color.defaultDivider)
        typedArray.recycle()
        updateDividerColor(dividerColorResId)
    }

    override fun updateTitle(title: String?) {
        textViewTitle.text = title ?: ""
    }

    override fun updateDescription(description: String?) {
        textViewDescription.text = description ?: ""
    }

    fun setDividerColor(@ColorRes colorResId: Int) {
        this.dividerColorResId = colorResId
        updateDividerColor(colorResId)
    }

    fun getDividerColor(): Int = if (dividerColorResId != 0) {
        dividerColorResId
    } else {
        dividerColorResId
    }

    private fun updateDividerColor(@ColorRes color: Int) {
        viewDivider.setBackgroundColor(ContextCompat.getColor(context, if (color != 0) color else R.color.defaultDivider))
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState: Parcelable? = super.onSaveInstanceState()
        superState?.let {
            val state = SavedState(superState)
            state.dividerColorResId = this.dividerColorResId
            return state
        } ?: run {
            return superState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        when (state) {
            is SavedState -> {
                super.onRestoreInstanceState(state.superState)
                this.dividerColorResId = state.dividerColorResId
                updateDividerColor(this.dividerColorResId)
            }
            else -> {
                super.onRestoreInstanceState(state)
            }
        }
    }

    internal class SavedState : AbsSavedState {
        var dividerColorResId: Int = 0

        constructor(superState: Parcelable) : super(superState)

        constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
            dividerColorResId = source.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(dividerColorResId)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.ClassLoaderCreator<SavedState> = object : Parcelable.ClassLoaderCreator<SavedState> {
                override fun createFromParcel(source: Parcel, loader: ClassLoader): SavedState {
                    return SavedState(source, loader)
                }

                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source, null)
                }

                override fun newArray(size: Int): Array<SavedState> {
                    return newArray(size)
                }
            }
        }
    }
}