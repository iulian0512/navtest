package com.iulian.navtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val notif = NotificationHelper.createnotification(this, "1234", "aaa", "bbbb")

        with(NotificationManagerCompat.from(this))
        {
            notify(Random.nextInt(), notif)
        }

    }

}
