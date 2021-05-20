package com.timer.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.timer.R
import com.timer.app.Constants
import com.timer.models.Timer
import com.timer.utils.CountDownUtils
import kotlinx.android.synthetic.main.raw_home.view.*
import kotlin.collections.ArrayList


class HomeAdapter(
    val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val beans = ArrayList<Timer>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return ItemViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.raw_home, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return beans.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        if (holder is ItemViewHolder) {

            holder.imgActionPlayResume.setOnClickListener {

                if (holder.etSeconds.text.toString().isNotEmpty()) {

                    when (holder.imgActionPlayResume.contentDescription) {

                        context.getString(R.string.start) -> {

                            setViews(
                                Constants.START,
                                holder.imgActionStop,
                                holder.imgActionPlayResume,
                                holder.etSeconds
                            )

                            holder.countDownUtils.startCountDownTimer(holder.etSeconds.text.toString()
                                .toLong() * 1000,

                                object : CountDownUtils.CountDownCallbacks {

                                    override fun onCountDownFinished() {

                                        setViews(
                                            Constants.STOP,
                                            holder.imgActionStop,
                                            holder.imgActionPlayResume,
                                            holder.etSeconds
                                        )

                                    }

                                    override fun onCountDownUpdated(
                                        millisUntilFinished: Long,
                                        time: String
                                    ) {
                                        holder.txtTimer.tag = millisUntilFinished
                                        holder.txtTimer.text = time
                                    }

                                }
                            )


                        }

                        context.getString(R.string.pause) -> {

                            setViews(
                                Constants.PAUSE,
                                holder.imgActionStop,
                                holder.imgActionPlayResume,
                                holder.etSeconds
                            )

                            holder.countDownUtils.pauseTimer()

                        }

                        context.getString(R.string.resume) -> {

                            setViews(
                                Constants.RESUME,
                                holder.imgActionStop,
                                holder.imgActionPlayResume,
                                holder.etSeconds
                            )

                            holder.countDownUtils.resumeTimer(holder.txtTimer.tag.toString()
                                .toLong(),

                                object : CountDownUtils.CountDownCallbacks {

                                    override fun onCountDownFinished() {

                                        setViews(
                                            Constants.STOP,
                                            holder.imgActionStop,
                                            holder.imgActionPlayResume,
                                            holder.etSeconds
                                        )

                                    }

                                    override fun onCountDownUpdated(
                                        millisUntilFinished: Long,
                                        time: String
                                    ) {
                                        holder.txtTimer.tag = millisUntilFinished
                                        holder.txtTimer.text = time
                                    }

                                }
                            )

                        }

                    }

                }

            }

            holder.imgActionStop.setOnClickListener {

                setViews(
                    Constants.STOP,
                    holder.imgActionStop,
                    holder.imgActionPlayResume,
                    holder.etSeconds
                )

                holder.countDownUtils.stopTimer()

                holder.txtTimer.text = context.getString(R.string._00_00_00)

            }

        }

    }

    private fun setViews(
        action: String,
        imgActionStop: ImageView,
        imgActionPlayResume: ImageView,
        etSeconds: EditText
    ) {

        if (action == Constants.START) {

            etSeconds.isEnabled = false
            etSeconds.alpha = 0.5f

            imgActionStop.visibility = View.VISIBLE

            imgActionPlayResume.setImageResource(R.drawable.ic_pause)
            imgActionPlayResume.contentDescription = context.getString(R.string.pause)

        } else if (action == Constants.PAUSE) {

            etSeconds.isEnabled = false
            etSeconds.alpha = 0.5f

            imgActionStop.visibility = View.VISIBLE

            imgActionPlayResume.setImageResource(R.drawable.ic_start)
            imgActionPlayResume.contentDescription = context.getString(R.string.resume)

        } else if (action == Constants.RESUME) {

            etSeconds.isEnabled = false
            etSeconds.alpha = 0.5f

            imgActionStop.visibility = View.VISIBLE

            imgActionPlayResume.setImageResource(R.drawable.ic_pause)
            imgActionPlayResume.contentDescription = context.getString(R.string.pause)

        } else if (action == Constants.STOP) {

            etSeconds.text.clear()

            etSeconds.isEnabled = true
            etSeconds.alpha = 1.0f

            imgActionStop.visibility = View.INVISIBLE

            imgActionPlayResume.setImageResource(R.drawable.ic_start)
            imgActionPlayResume.contentDescription = context.getString(R.string.start)

        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun addItems(items: List<Timer>) {
        beans.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: Timer) {
        beans.add(item)
        notifyItemChanged(itemCount)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val countDownUtils = CountDownUtils()

        val etSeconds = itemView.et_seconds
        val txtTimer = itemView.txt_timer
        val imgActionPlayResume = itemView.img_action_play_resume
        val imgActionStop = itemView.img_action_stop

    }


}