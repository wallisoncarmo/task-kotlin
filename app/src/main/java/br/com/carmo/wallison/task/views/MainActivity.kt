package br.com.carmo.wallison.task.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.carmo.wallison.task.R
import br.com.carmo.wallison.task.constants.TaskConstants
import br.com.carmo.wallison.task.controller.PriorityController
import br.com.carmo.wallison.task.controller.UserController
import br.com.carmo.wallison.task.repository.PriorityCacheConstants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val userController: UserController by lazy {
        UserController(this)
    }
    private val mPriorityController: PriorityController by lazy {
        PriorityController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        configToggle()
        configNav()
        configDefaultFrament()
        loadPriorities()
    }

    private fun loadPriorities() {
        PriorityCacheConstants.setCache(mPriorityController.getList())
    }

    private fun configNav() {
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun configToggle() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_done -> {
                showFragmentDone()
            }
            R.id.nav_todo -> {
                showFragmentTodo()
            }
            R.id.nav_logout -> {
                handleLogout()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun configDefaultFrament() {
        val fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.DONE)
        showFragment(fragment)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    private fun showFragmentDone() {
        var fragment: Fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.DONE)
        showFragment(fragment)
    }

    private fun showFragmentTodo() {
        var fragment: Fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.TODO)
        showFragment(fragment)
    }

    private fun handleLogout() {
        userController.logout()
        moveToLogin()
    }

    private fun moveToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
