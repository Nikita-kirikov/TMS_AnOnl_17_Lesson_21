package app.tms_lesson_19

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalStateException

class NoteAdapter :
    ListAdapter<NoteType, RecyclerView.ViewHolder>(Companion) {

    var onClick : ((Note) -> Unit)? = null
    var onGroupClick : ((Group) -> Unit)? = null
    var onLongClick : ((Note) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return when(val item = getItem(position)) {
            is Note -> {
                if (item.important) {
                    IMPORTANT_TYPE
                } else {
                    UNIMPORTANT_TYPE
                }
            }
            is Group -> GROUP_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            IMPORTANT_TYPE -> NoteImportantViewHolder.from(parent)
            UNIMPORTANT_TYPE -> NoteViewHolder.from(parent)
            GROUP_TYPE -> GroupViewHolder.from(parent)
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item is Note && holder is NoteImportantViewHolder) {
            holder.bind(item, onClick, onLongClick)
        } else if (item is Note && holder is NoteViewHolder) {
            holder.bind(item, onClick, onLongClick)
        } else if (item is Group && holder is GroupViewHolder) {
            holder.bind(item, onGroupClick)
        } else {
            throw IllegalStateException()
        }
    }

    companion object : DiffUtil.ItemCallback<NoteType>() {
        private const val IMPORTANT_TYPE = 1
        private const val UNIMPORTANT_TYPE = 2
        private const val GROUP_TYPE = 3

        override fun areItemsTheSame(oldItem: NoteType, newItem: NoteType): Boolean {
            return oldItem.id  == newItem.id // override realization when(NoteType) 1-group 2-note
        }

        override fun areContentsTheSame(oldItem: NoteType, newItem: NoteType): Boolean {
            return oldItem == newItem
        }
    }
}