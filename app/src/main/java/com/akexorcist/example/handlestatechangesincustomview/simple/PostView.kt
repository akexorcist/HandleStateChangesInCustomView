package com.akexorcist.example.handlestatechangesincustomview.simple

import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.customview.view.AbsSavedState
import com.akexorcist.example.handlestatechangesincustomview.R
import kotlinx.android.synthetic.main.view_post_regular.view.*

class PostView : FrameLayout {
    private var title: String? = null
    private var description: String? = null
    private var dividerColorResId: Int = 0

    constructor(context: Context) : super(context) {
        setup(context)
        setupStyleable(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(context)
        setupStyleable(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(context)
        setupStyleable(context, attrs)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        setup(context)
        setupStyleable(context, attrs)
    }

    private fun setup(context: Context) {
        removeAllViews()
        LayoutInflater.from(context).inflate(R.layout.view_post, this)
    }

    private fun setupStyleable(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PostView)
        this.title = typedArray.getString(R.styleable.PostView_spv_title)
        this.description = typedArray.getString(R.styleable.PostView_spv_description)
        this.dividerColorResId = typedArray.getResourceId(R.styleable.PostView_spv_dividerColor, R.color.defaultDivider)
        typedArray.recycle()
        updateTitle(this.title)
        updateDescription(this.description)
        updateDividerColor(this.dividerColorResId)
    }

    fun setTitle(title: String?) {
        this.title = title
        updateTitle(title)
    }

    fun getTitle(): String? = this.title

    private fun updateTitle(title: String?) {
        textViewTitle.text = title ?: ""
    }

    fun setDescription(description: String?) {
        this.description = description
        updateDescription(description)
    }

    fun getDescription(): String? = this.description

    private fun updateDescription(description: String?) {
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
            state.title = this.title
            state.description = this.description
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
                this.title = state.title
                this.description = state.description
                this.dividerColorResId = state.dividerColorResId
                updateTitle(this.title)
                updateDescription(this.description)
                updateDividerColor(this.dividerColorResId)
            }
            else -> {
                super.onRestoreInstanceState(state)
            }
        }
    }

    internal class SavedState : AbsSavedState {
        var title: String? = null
        var description: String? = null
        var dividerColorResId: Int = 0

        constructor(superState: Parcelable) : super(superState)

        constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
            title = source.readString()
            description = source.readString()
            dividerColorResId = source.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(title)
            out.writeString(description)
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