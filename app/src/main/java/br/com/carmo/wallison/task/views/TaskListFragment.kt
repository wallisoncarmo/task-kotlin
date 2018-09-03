package br.com.carmo.wallison.task.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.carmo.wallison.task.R
import br.com.carmo.wallison.task.adapter.TaskListAdapter
import br.com.carmo.wallison.task.constants.TaskConstants
import br.com.carmo.wallison.task.controller.TaskController
import br.com.carmo.wallison.task.controller.UserController

class TaskListFragment : Fragment() {

    private var mTaskFilter: Int = 0

    private val userController: UserController by lazy {
        UserController(mContext)
    }

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView

    private val mTaskController: TaskController by lazy {
        TaskController(mContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mTaskFilter = it.getInt(TaskConstants.TASK_FILTER.KEY)
        }
    }

    override fun onResume() {
        super.onResume()
        loadTaskList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        mContext = rootView.context
        configFab(rootView)
        configRecycler(rootView)
        return rootView
    }

    private fun configRecycler(rootView: View) {
        mRecyclerTaskList = rootView.findViewById(R.id.recycleTaskList)
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)
    }

    private fun loadTaskList() {
        val taskList = mTaskController.findByUser(mTaskFilter)
        if (taskList.count() > 0) {
            mRecyclerTaskList.adapter = TaskListAdapter(taskList)
        }
    }

    private fun moveToLogin() {
        userController.logout()
        startActivity(Intent(mContext, LoginActivity::class.java))
    }

    private fun configFab(rootView: View) {
        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener {
            moveToFormTask()
        }
    }

    private fun moveToFormTask() {
        startActivity(Intent(mContext, TaskFormActivity::class.java))
    }

    companion object {
        //        @JvmStatic
        fun newInstance(taskFilter: Int): TaskListFragment =
                TaskListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(TaskConstants.TASK_FILTER.KEY, taskFilter)
                    }
                }
    }
}
