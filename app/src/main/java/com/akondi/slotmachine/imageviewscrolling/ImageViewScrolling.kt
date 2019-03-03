package com.akondi.slotmachine.imageviewscrolling

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import com.akondi.slotmachine.R
import kotlinx.android.synthetic.main.image_view_scrolling.view.*

class ImageViewScrolling : FrameLayout {
    internal lateinit var eventEnd: IEventEnd

    internal var lastResult = 0
    internal var oldValue = 0

    companion object {
        private val ANIMATION_DURATION = 150
    }

    val value: Int
        get() = Integer.parseInt(nextImage.tag.toString())

    fun setEventEnd(eventEnd: IEventEnd) {
        this.eventEnd = eventEnd
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.image_view_scrolling, this)

        nextImage.translationY = height.toFloat()
    }

    fun setValueRandom(image: Int, numRotate: Int) {
        currentImage.animate()
            .translationY((-height).toFloat())
            .setDuration(ANIMATION_DURATION.toLong()).start()

        nextImage.translationY = nextImage.height.toFloat()

        nextImage.animate().translationY(0f).setDuration(ANIMATION_DURATION.toLong())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    setImage(currentImage, oldValue % 6)
                    currentImage.translationY = 0f
                    if (oldValue < numRotate) { // if still have rotate
                        setValueRandom(image, numRotate)
                        oldValue++
                    } else {
                        lastResult = 0
                        oldValue = 0
                        setImage(nextImage, image)
                        eventEnd.eventEnd(image % 6, numRotate) //Because we have 6 images
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {
                }

            }).start()
    }

    private fun setImage(currentImage: ImageView?, i: Int) {
        when (i) {
            Util.bar        -> currentImage!!.setImageResource(R.drawable.bar_done)
            Util.lemon      -> currentImage!!.setImageResource(R.drawable.lemon_done)
            Util.orange     -> currentImage!!.setImageResource(R.drawable.orange_done)
            Util.seven      -> currentImage!!.setImageResource(R.drawable.sevent_done)
            Util.triple     -> currentImage!!.setImageResource(R.drawable.triple_done)
            Util.watermelon -> currentImage!!.setImageResource(R.drawable.waternelon_done)
        }

        currentImage!!.tag = i
        lastResult = i
    }
}