package com.example.project
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProductAdapter(options: FirebaseRecyclerOptions<Cakes>) :
    FirebaseRecyclerAdapter<Cakes, ProductAdapter.MyViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Cakes) {
        val storageRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.photo)
        Glide.with(holder.image.context).load(storageRef).into(holder.image)
        holder.name.text = model.name
        holder.description.text = model.description
        val type = model.type
        val price = model.price

        holder.buyButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            // Pass the values of the current item to the detail activity
            intent.putExtra("cakeName", model.name)
            intent.putExtra("cakeDescription", model.description)
            intent.putExtra("photo", model.photo)
            intent.putExtra("type", model.type)
            intent.putExtra("price", model.price)
            context.startActivity(intent)
        }
    }
    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.products, parent, false))
    {


        val name: TextView = itemView.findViewById(R.id.name)
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val description: TextView = itemView.findViewById(R.id.description)
        val buyButton: Button = itemView.findViewById(R.id.buy)
        val goBackButton: Button = itemView.findViewById(R.id.back)

        init {

            goBackButton.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }

    }
}
