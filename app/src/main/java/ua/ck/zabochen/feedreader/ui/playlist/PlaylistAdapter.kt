package ua.ck.zabochen.feedreader.ui.playlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import ua.ck.zabochen.feedreader.R
import ua.ck.zabochen.feedreader.data.db.entity.video.VideoEntity
import ua.ck.zabochen.feedreader.data.db.entity.video.YoutubeVideoEntity
import ua.ck.zabochen.feedreader.internal.glide.GlideApp

class PlaylistAdapter : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    private val videoList: MutableList<YoutubeVideoEntity> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_item_playlist_video, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(videoList[position])
    }

    override fun getItemCount(): Int {
        return if (!videoList.isEmpty()) videoList.size else 0
    }

    fun setData(videoList: ArrayList<YoutubeVideoEntity>) {
        this.videoList.clear()
        this.videoList.addAll(videoList)
        notifyDataSetChanged()
    }

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }

        @BindView(R.id.adapterItemPlaylistVideo_textView_videoTitle)
        lateinit var videoTitle: TextView

        @BindView(R.id.adapterItemPlaylistVideo_imageView_videoImage)
        lateinit var coverImage: ImageView

        @BindView(R.id.adapterItemPlaylistVideo_textView_videoDescription)
        lateinit var videoDescription: TextView

        fun bind(video: VideoEntity) {
            this.videoTitle.text = video.title
            this.videoDescription.text = video.description
            GlideApp.with(itemView.context)
                .load(video.coverImageUrl)
                .into(coverImage)
        }
    }
}
