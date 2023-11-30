    package com.example.neobis_android_news_app.adapter

    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.recyclerview.widget.AsyncListDiffer
    import androidx.recyclerview.widget.DiffUtil
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.example.neobis_android_news_app.databinding.ItemNewsBinding
    import com.example.neobis_android_news_app.model.Article


    class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

        private var onItemClickListener: ((Article) -> Unit)? = null

        private val differCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
        val differ = AsyncListDiffer(this, differCallback)

        inner class ViewHolder(val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemNewsBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val article = differ.currentList[position]
            with(holder.binding) {
                newsAuthor.text = article.author
                newsTime.text = article.publishedAt
                newsTitle.text = article.title
                newsDescription.text = article.description
                Glide.with(newsImage).load(article.urlToImage).into(newsImage)
                holder.itemView.setOnClickListener {
                    onItemClickListener?.let { it(article) }
                }
            }
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        fun setOnItemClickListener(listener: (Article) -> Unit) {
            onItemClickListener = listener
        }


    }