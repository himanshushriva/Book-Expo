package com.himan.bookexpo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himan.bookexpo.data.model.Book
import com.himan.bookexpo.databinding.ItemBookBinding

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private val books = mutableListOf<Book>()

    fun submitList(newBooks: List<Book>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookViewHolder {
        val binding = ItemBookBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BookViewHolder,
        position: Int
    ) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size


    class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.tvBookTitle.text = book.title
            binding.tvBookSubtitle.text = book.subtitle.ifEmpty { "Not available" }
            binding.tvBookAuthor.text = book.authors.ifEmpty { "Unknown" }
        }
    }
}