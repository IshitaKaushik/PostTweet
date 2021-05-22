package com.example.posttweet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posttweet.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostAdapter(options:FirestoreRecyclerOptions<Post>, val clicked: onClicked):FirestoreRecyclerAdapter<Post,PostViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder =  PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_post, parent, false))
        viewHolder.likeButton.setOnClickListener{
            clicked.onClicking(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postText.text = model.text
        holder.userText.text = model.createdBy.name
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text = model.likedBy.size.toString()
        holder.createdAt.text = Utils.getTime(model.createdAt)

        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserId)
        if(isLiked){
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_baseline_favorite_like_24))
        }
        else{
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_baseline_favorite_border_unlike_24))
        }
    }
}

class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val postText: TextView = itemView.findViewById(R.id.postTitle)
    val userText: TextView = itemView.findViewById(R.id.userName)
    val createdAt: TextView = itemView.findViewById(R.id.createdAt)
    val likeCount: TextView = itemView.findViewById(R.id.likeCount)
    val userImage: ImageView = itemView.findViewById(R.id.userImage)
    val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
}
interface onClicked{
    fun onClicking(id:String)
}