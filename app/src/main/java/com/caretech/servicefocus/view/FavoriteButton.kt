package com.caretech.servicefocus.view


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.caretech.servicefocus.R
import com.mcxiaoke.koi.ext.dpToPx

class FavoriteButton : AppCompatImageView {

    private var mButtonSize: Int = 0
    private var mPadding: Int = 0
    private var mFavorite: Boolean = false
    private var mAnimateFavorite: Boolean = false
    private var mAnimateUnfavorite: Boolean = false
    private var mFavoriteResource: Int = 0
    private var mNotFavoriteResource: Int = 0
    private var mRotationDuration: Int = 0
    private var mRotationAngle: Int = 0
    private var mBounceDuration: Int = 0
    private var mColor: Int = 0
    private var mType: Int = 0

    private var mOnFavoriteChangeListener: OnFavoriteChangeListener? = null
    private var mOnFavoriteAnimationEndListener: OnFavoriteAnimationEndListener? = null
    private var mBroadcasting: Boolean = false

    /**
     * Returns favorite state.
     *
     * @return true if button is in favorite state, false if not
     */
    /**
     * Changes the favorite state of this button without animation.
     *
     * @param favorite true to favorite the button, false to uncheck it
     */
    var isFavorite: Boolean
        get() = mFavorite
        set(favorite) = updateFavoriteButton(favorite, false, false)

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    /**
     * Interface definition for a callback to be invoked when the favorite state is changed.
     */
    interface OnFavoriteChangeListener {
        /**
         * Called when the favorite state is changed.
         *
         * @param buttonView the button view whose state has changed
         * @param favorite the favorite state
         */
        fun onFavoriteChanged(buttonView: FavoriteButton, favorite: Boolean)
    }

    /**
     * Set a listener will be called when the favorite state is changed.
     *
     * @param listener the [FavoriteButton.OnFavoriteChangeListener] will be called
     */
    fun setOnFavoriteChangeListener(listener: OnFavoriteChangeListener) {
        mOnFavoriteChangeListener = listener
    }

    /**
     * Interface definition for a callback to be invoked when the favorite animation ends.
     */
    interface OnFavoriteAnimationEndListener {
        /**
         * Called when the favorite animation ended.
         *
         * @param buttonView the button view whose animation ended
         * @param favorite the favorite state
         */
        fun onAnimationEnd(buttonView: FavoriteButton, favorite: Boolean)
    }

    /**
     * Set a listener will be called when the favorite state is changed.
     *
     * @param listener the [FavoriteButton.OnFavoriteAnimationEndListener] will be
     * called
     */
    fun setOnFavoriteAnimationEndListener(listener: OnFavoriteAnimationEndListener) {
        mOnFavoriteAnimationEndListener = listener
    }

    /**
     * Initialize the default values
     *
     *  * state = false
     *  * size = 48 dp
     *  * padding = 12 dp
     *  * is mFavorite = false
     *  * animated = true
     *  * default drawables - stars
     *  * rotation duration = 300 ms
     *  * rotation angle = 360 degrees
     *  * bounce duration = 300 ms
     *  * color of default icon = black
     *  * type of default icon = star
     *
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        mButtonSize = dpToPx(DEFAULT_BUTTON_SIZE)
        mPadding = dpToPx(DEFAULT_PADDING)
        mFavorite = DEFAULT_FAVORITE
        mAnimateFavorite = DEFAULT_ANIMATE_FAVORITE
        mAnimateUnfavorite = DEFAULT_ANIMATE_UNFAVORITE
        mFavoriteResource = FAVORITE_STAR_BLACK
        mNotFavoriteResource = FAVORITE_STAR_BORDER_BLACK
        mRotationDuration = DEFAULT_ROTATION_DURATION
        mRotationAngle = DEFAULT_ROTATION_ANGLE
        mBounceDuration = DEFAULT_BOUNCE_DURATION
        mColor = STYLE_BLACK
        mType = STYLE_STAR
        if (!isInEditMode) {
            if (attrs != null) {
                initAttributes(context, attrs)
            }
            setOnClickListener { toggleFavorite() }
        }
        if (mFavorite) {
            setImageResource(mFavoriteResource)
        } else {
            setImageResource(mNotFavoriteResource)
        }
        setPadding(mPadding, mPadding, mPadding, mPadding)
    }

    private fun initAttributes(context: Context, attributeSet: AttributeSet) {
        val attr = getTypedArray(context, attributeSet, R.styleable.FavoriteButton)
        if (attr != null) {
            try {
                mFavorite = attr.getBoolean(R.styleable.FavoriteButton_mfb_state, mAnimateUnfavorite)
                mButtonSize = dpToPx(attr.getInt(R.styleable.FavoriteButton_mfb_size, DEFAULT_BUTTON_SIZE))
                mAnimateFavorite = attr.getBoolean(R.styleable.FavoriteButton_mfb_animate_favorite,
                        mAnimateFavorite)
                mAnimateUnfavorite = attr.getBoolean(R.styleable.FavoriteButton_mfb_animate_unfavorite,
                        mAnimateUnfavorite)
                mPadding = dpToPx(attr.getInt(R.styleable.FavoriteButton_mfb_padding, DEFAULT_PADDING))
                if (attr.getResourceId(R.styleable.FavoriteButton_mfb_favorite_image, 0) != 0 && attr.getResourceId(R.styleable.FavoriteButton_mfb_not_favorite_image, 0) != 0) {
                    mFavoriteResource = attr.getResourceId(R.styleable.FavoriteButton_mfb_favorite_image,
                            FAVORITE_STAR_BLACK)
                    mNotFavoriteResource = attr.getResourceId(R.styleable.FavoriteButton_mfb_not_favorite_image,
                            FAVORITE_STAR_BORDER_BLACK)
                } else {
                    setTheme(attr.getInt(R.styleable.FavoriteButton_mfb_color, STYLE_BLACK),
                            attr.getInt(R.styleable.FavoriteButton_mfb_type, STYLE_STAR))
                }

                mRotationDuration = attr.getInt(R.styleable.FavoriteButton_mfb_rotation_duration,
                        mRotationDuration)
                mRotationAngle = attr.getInt(R.styleable.FavoriteButton_mfb_rotation_angle, mRotationAngle)
                mBounceDuration = attr.getInt(R.styleable.FavoriteButton_mfb_bounce_duration, mBounceDuration)
            } finally {
                attr.recycle()
            }
        }
    }

    private fun getTypedArray(context: Context, attributeSet: AttributeSet, attr: IntArray): TypedArray? {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0)
    }

    private fun setTheme(color: Int, type: Int) {
        if (color == STYLE_BLACK) {
            if (type == STYLE_STAR) {
                mFavoriteResource = FAVORITE_STAR_BLACK
                mNotFavoriteResource = FAVORITE_STAR_BORDER_BLACK
            } else {
                mFavoriteResource = FAVORITE_HEART_BLACK
                mNotFavoriteResource = FAVORITE_HEART_BORDER_BLACK
            }
        } else {
            if (type == STYLE_STAR) {
                mFavoriteResource = FAVORITE_STAR_WHITE
                mNotFavoriteResource = FAVORITE_STAR_BORDER_WHITE
            } else {
                mFavoriteResource = FAVORITE_HEART_WHITE
                mNotFavoriteResource = FAVORITE_HEART_BORDER_WHITE
            }
        }
    }

    private fun setResources() {
        if (mFavorite) {
            setImageResource(mFavoriteResource)
        } else {
            setImageResource(mNotFavoriteResource)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mButtonSize, mButtonSize)
    }

    /**
     * Changes the favorite state of this button with animation.
     *
     * @param favorite true to favorite the button, false to uncheck it
     */
    fun setFavoriteAnimated(favorite: Boolean) {
        updateFavoriteButton(favorite, true, false)
    }

    /**
     * Changes the favorite state of this button without calling OnFavoriteChangeListener.
     *
     * @param favorite true to favorite the button, false to uncheck it
     */
    fun setFavoriteSuppressListener(favorite: Boolean) {
        updateFavoriteButton(favorite, mAnimateFavorite, true)
    }

    /**
     * Changes the favorite state of this button.
     *
     * @param favorite true to favorite the button, false to uncheck it
     * @param animated true to force animated change, false to force not animated one
     */
    @Deprecated("")
    fun setFavorite(favorite: Boolean, animated: Boolean) {
        updateFavoriteButton(favorite, animated, false)
    }

    /**
     * Toggle the favorite state of this button.
     */
    fun toggleFavorite() {
        isFavorite = !mFavorite
    }

    /**
     * Toggle the favorite state of this button.
     *
     * @param animated true to force animated toggle, false to force not animated one
     */
    fun toggleFavorite(animated: Boolean) {
        if (!mFavorite) {
            val orig = mAnimateFavorite
            mAnimateFavorite = animated
            isFavorite = !mFavorite
            mAnimateFavorite = orig
        } else {
            val orig = mAnimateUnfavorite
            mAnimateUnfavorite = animated
            isFavorite = !mFavorite
            mAnimateUnfavorite = orig
        }
    }

    private fun updateFavoriteButton(favorite: Boolean, animate: Boolean, suppressOnChange: Boolean) {
        if (mFavorite != favorite) {
            mFavorite = favorite
            // Avoid infinite recursions if setChecked() is called from a listener
            if (mBroadcasting) {
                return
            }

            mBroadcasting = true
            if (mOnFavoriteChangeListener != null && !suppressOnChange) {
                mOnFavoriteChangeListener!!.onFavoriteChanged(this, mFavorite)
            }
            if (favorite) {
                if (animate) {
                    animateButton(favorite)
                } else {
                    super.setImageResource(mFavoriteResource)
                    if (mOnFavoriteAnimationEndListener != null) {
                        mOnFavoriteAnimationEndListener!!.onAnimationEnd(this, mFavorite)
                    }
                }
            } else {
                if (animate) {
                    animateButton(favorite)
                } else {
                    super.setImageResource(mNotFavoriteResource)
                    if (mOnFavoriteAnimationEndListener != null) {
                        mOnFavoriteAnimationEndListener!!.onAnimationEnd(this, mFavorite)
                    }
                }
            }
            mBroadcasting = false
        }
    }

    private fun animateButton(toFavorite: Boolean) {
        val startAngle = 0
        val endAngle: Int
        val startBounce: Float
        val endBounce: Float
        if (toFavorite) {
            endAngle = mRotationAngle
            startBounce = 0.2f
            endBounce = 1.0f
        } else {
            endAngle = -mRotationAngle
            startBounce = 1.3f
            endBounce = 1.0f
        }

        val animatorSet = AnimatorSet()
        val rotationAnim = ObjectAnimator.ofFloat(this, "rotation", startAngle.toFloat(), endAngle.toFloat())
        rotationAnim.duration = mRotationDuration.toLong()
        rotationAnim.interpolator = ACCELERATE_INTERPOLATOR

        val bounceAnimX = ObjectAnimator.ofFloat(this, "scaleX", startBounce, endBounce)
        bounceAnimX.duration = mBounceDuration.toLong()
        bounceAnimX.interpolator = OVERSHOOT_INTERPOLATOR

        val bounceAnimY = ObjectAnimator.ofFloat(this, "scaleY", startBounce, endBounce)
        bounceAnimY.duration = mBounceDuration.toLong()
        bounceAnimY.interpolator = OVERSHOOT_INTERPOLATOR
        bounceAnimY.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                if (mFavorite) {
                    setImageResource(mFavoriteResource)
                } else {
                    setImageResource(mNotFavoriteResource)
                }
            }
        })

        animatorSet.play(rotationAnim)
        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim)

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (mOnFavoriteAnimationEndListener != null) {
                    mOnFavoriteAnimationEndListener!!.onAnimationEnd(this@FavoriteButton, mFavorite)
                }
            }
        })

        animatorSet.start()
    }

    /**
     * Builder.
     */
    class Builder(private val context: Context) {

        private var mButtonSize = DEFAULT_BUTTON_SIZE
        private var mPadding = DEFAULT_PADDING
        private var mFavorite = DEFAULT_FAVORITE
        private var mAnimateFavorite = DEFAULT_ANIMATE_FAVORITE
        private var mAnimateUnfavorite = DEFAULT_ANIMATE_UNFAVORITE
        private var mFavoriteResource = FAVORITE_STAR_BLACK
        private var mNotFavoriteResource = FAVORITE_STAR_BORDER_BLACK
        private var mRotationDuration = DEFAULT_ROTATION_DURATION
        private var mRotationAngle = DEFAULT_ROTATION_ANGLE
        private var mBounceDuration = DEFAULT_BOUNCE_DURATION
        private var mColor = STYLE_WHITE
        private var mType = STYLE_BLACK
        private var mCustomResources = false

        fun size(size: Int): Builder {
            this.mButtonSize = size
            return this
        }

        fun padding(padding: Int): Builder {
            this.mPadding = padding
            return this
        }

        fun favorite(favorite: Boolean): Builder {
            this.mFavorite = favorite
            return this
        }

        fun animateFavorite(animation: Boolean): Builder {
            this.mAnimateFavorite = animation
            return this
        }

        fun animateUnfavorite(animation: Boolean): Builder {
            this.mAnimateUnfavorite = animation
            return this
        }

        fun favoriteResource(resource: Int): Builder {
            this.mFavoriteResource = resource
            mCustomResources = true
            return this
        }

        fun notFavoriteResource(recsource: Int): Builder {
            this.mNotFavoriteResource = recsource
            mCustomResources = true
            return this
        }

        fun rotationDuration(rotationDuration: Int): Builder {
            this.mRotationDuration = rotationDuration
            return this
        }

        fun rotationAngle(rotationAngle: Int): Builder {
            this.mRotationAngle = rotationAngle
            return this
        }

        fun bounceDuration(bounceDuration: Int): Builder {
            this.mBounceDuration = bounceDuration
            return this
        }

        fun color(color: Int): Builder {
            this.mColor = color
            mCustomResources = false
            return this
        }

        fun type(type: Int): Builder {
            this.mType = type
            mCustomResources = false
            return this
        }

        fun create(): FavoriteButton {
            val materialFavoriteButton = FavoriteButton(context)
            materialFavoriteButton.setSize(mButtonSize)
            materialFavoriteButton.setPadding(mPadding)
            materialFavoriteButton.setFavorite(mFavorite, false)
            materialFavoriteButton.setAnimateFavorite(mAnimateFavorite)
            materialFavoriteButton.setAnimateUnfavorite(mAnimateUnfavorite)
            materialFavoriteButton.setFavoriteResource(mFavoriteResource)
            materialFavoriteButton.setNotFavoriteResource(mNotFavoriteResource)
            materialFavoriteButton.setRotationDuration(mRotationDuration)
            materialFavoriteButton.setRotationAngle(mRotationAngle)
            materialFavoriteButton.setBounceDuration(mBounceDuration)
            if (!mCustomResources) {
                materialFavoriteButton.setColor(mColor)
                materialFavoriteButton.setType(mType)
            }
            materialFavoriteButton.setResources()

            return materialFavoriteButton
        }
    }

    fun setSize(size: Int) {
        this.mButtonSize = dpToPx(size)
    }

    fun setPadding(padding: Int) {
        this.mPadding = dpToPx(padding)
        setPadding(mPadding, mPadding, mPadding, mPadding)
    }

    fun setAnimateFavorite(animation: Boolean) {
        this.mAnimateFavorite = animation
    }

    fun setAnimateUnfavorite(animation: Boolean) {
        this.mAnimateUnfavorite = animation
    }

    fun setFavoriteResource(favoriteResource: Int) {
        this.mFavoriteResource = favoriteResource
    }

    fun setNotFavoriteResource(notFavoriteResource: Int) {
        this.mNotFavoriteResource = notFavoriteResource
    }

    fun setRotationDuration(rotationDuration: Int) {
        this.mRotationDuration = rotationDuration
    }

    fun setRotationAngle(rotationAngle: Int) {
        this.mRotationAngle = rotationAngle
    }

    fun setBounceDuration(bounceDuration: Int) {
        this.mBounceDuration = bounceDuration
    }

    fun setColor(color: Int) {
        this.mColor = color
        setTheme(color, mType)
    }

    fun setType(type: Int) {
        this.mType = type
        setTheme(mColor, type)
    }

    companion object {
        val STYLE_BLACK = 0
        val STYLE_WHITE = 1
        val STYLE_STAR = 0
        val STYLE_HEART = 1

        private val DEFAULT_BUTTON_SIZE = 32
        private val DEFAULT_PADDING = 4
        private val DEFAULT_FAVORITE = false
        private val DEFAULT_ANIMATE_FAVORITE = true
        private val DEFAULT_ANIMATE_UNFAVORITE = false
        private val DEFAULT_ROTATION_DURATION = 400
        private val DEFAULT_ROTATION_ANGLE = 360
        private val DEFAULT_BOUNCE_DURATION = 300
        private val FAVORITE_STAR_BLACK = R.drawable.ic_star_black_24dp
        private val FAVORITE_STAR_BORDER_BLACK = R.drawable.ic_star_border_black_24dp
        private val FAVORITE_STAR_WHITE = R.drawable.ic_star_white_24dp
        private val FAVORITE_STAR_BORDER_WHITE = R.drawable.ic_star_border_white_24dp
        private val FAVORITE_HEART_BLACK = R.drawable.ic_favorite_black_24dp
        private val FAVORITE_HEART_BORDER_BLACK = R.drawable.ic_favorite_border_black_24dp
        private val FAVORITE_HEART_WHITE = R.drawable.ic_favorite_white_24dp
        private val FAVORITE_HEART_BORDER_WHITE = R.drawable.ic_favorite_border_white_24dp
        private val ACCELERATE_INTERPOLATOR = AccelerateInterpolator()
        private val OVERSHOOT_INTERPOLATOR = OvershootInterpolator(4f)
    }
}