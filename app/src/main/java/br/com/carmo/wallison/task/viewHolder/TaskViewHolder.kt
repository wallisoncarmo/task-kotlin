package br.com.carmo.wallison.task.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.carmo.wallison.task.R
import br.com.carmo.wallison.task.entities.TaskEntity
import br.com.carmo.wallison.task.repository.PriorityCacheConstants

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDueDate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)


    fun bindData(task: TaskEntity) {
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.id)
        mTextDueDate.text = task.dueDate
        if(task.complete){
            mImageTask.setImageResource(R.drawable.ic_done)
        }

        mTextDescription.setOnClickListener {

        }
    }
}