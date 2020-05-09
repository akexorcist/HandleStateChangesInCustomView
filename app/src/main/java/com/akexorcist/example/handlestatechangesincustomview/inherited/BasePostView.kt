package com.akexorcist.example.handlestatechangesincustomview.inherited

import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.customview.view.AbsSavedState
import com.akexorcist.example.handlestatechangesincustomview.R

abstract class BasePostView : FrameLayout {
    private var title: String? = null
    private var description: String? = null

    constructor(context: Context) : super(context) {
        setup(context)
        setupStyleable(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(context)
        setupStyleable(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setup(context)
        setupStyleable(context, attrs)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        setup(context)
        setupStyleable(context, attrs)
    }

    private fun setup(context: Context) {
        removeAllViews()
        LayoutInflater.from(context).inflate(getLayout(), this)
        initView()
    }

    private fun setupStyleable(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BasePostView)
        this.title = typedArray.getString(R.styleable.BasePostView_ipv_title)
        this.description = typedArray.getString(R.styleable.BasePostView_ipv_description)

        typedArray.recycle()
        updateTitle(this.title)
        updateDescription(this.description)
        initStyleable(context, attrs)
    }

    fun setTitle(title: String?) {
        this.title = title
        updateTitle(title)
    }

    fun getTitle(): String? = this.title

    fun setDescription(description: String?) {
        this.description = description
        updateDescription(description)
    }

    fun getDescription(): String? = this.description

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun initView()

    abstract fun initStyleable(context: Context, attrs: AttributeSet?)

    abstract fun updateTitle(title: String?)

    abstract fun updateDescription(description: String?)

    override fun onSaveInstanceState(): Parcelable? {
        val superState: Parcelable? = super.onSaveInstanceState()
        superState?.let {
            val state = SavedState(superState)
            state.title = this.title
            state.description = this.description
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
                updateTitle(this.title)
                updateDescription(this.description)
            }
            else -> {
                super.onRestoreInstanceState(state)
            }
        }
    }

    internal class SavedState : AbsSavedState {
        var title: String? = null
        var description: String? = null

        constructor(superState: Parcelable) : super(superState)

        constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
            title = source.readString()
            description = source.readString()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(title)
            out.writeString(description)
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