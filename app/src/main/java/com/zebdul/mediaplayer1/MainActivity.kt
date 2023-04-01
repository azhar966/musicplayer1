package com.zebdul.mediaplayer1


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer
    private var totalTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val play1 = findViewById<ImageView>(R.id.play)
        val pause1 = findViewById<ImageView>(R.id.pause)
        val stop1 = findViewById<ImageView>(R.id.stop)
        val seekBar1 = findViewById<SeekBar>(R.id.seekbar)

        supportActionBar?.hide()
        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer.setVolume(1f, 1f)
        totalTime = mediaPlayer.duration

        play1.setOnClickListener {
            mediaPlayer.start()
        }
        pause1.setOnClickListener {
            mediaPlayer.pause()
        }
        stop1.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        }
        //user changes the seekbar reflect on music timeline
        seekBar1.max = totalTime
        seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        //changes the seekbar according music timeline automatic
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekBar1.progress = mediaPlayer.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (exp: java.lang.Exception) {
                    seekBar1.progress = 0
                }
            }
        }, 0)
    }
}