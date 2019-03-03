package com.akondi.slotmachine

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.akondi.slotmachine.imageviewscrolling.IEventEnd
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), IEventEnd {
    override fun eventEnd(result: Int, count: Int) {
        if (count_down < 2)
            count_down++ // If still have image scrolling
        else {
            down.visibility = View.GONE
            up.visibility = View.VISIBLE

            count_down = 0

            if (image.value == image2.value && image2.value == image3.value) {
                Toast.makeText(this, "You won BIG prize", Toast.LENGTH_SHORT).show()
                Common.SCORE += 300
                txt_score.text = Common.SCORE.toString()
            } else if (image.value == image2.value
                || image2.value == image3.value
                || image.value == image3.value) {
                Toast.makeText(this, "You won SMALL prize", Toast.LENGTH_SHORT).show()
                Common.SCORE += 100
                txt_score.text = Common.SCORE.toString()
            } else {
                Toast.makeText(this, "You lost", Toast.LENGTH_SHORT).show()

            }
        }
    }

    internal var count_down = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        image.setEventEnd(this@MainActivity)
        image2.setEventEnd(this@MainActivity)
        image3.setEventEnd(this@MainActivity)

        up.setOnClickListener {
            if (Common.SCORE >= 50) {
                up.visibility = View.GONE
                down.visibility = View.VISIBLE

                image.setValueRandom(Random.nextInt(6), Random.nextInt(5, 15))
                image2.setValueRandom(Random.nextInt(6), Random.nextInt(5, 15))
                image3.setValueRandom(Random.nextInt(6), Random.nextInt(5, 15))

                Common.SCORE -= 50
                txt_score.text = Common.SCORE.toString()
            }
        }

    }
}
