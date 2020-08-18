package com.brh.digiturbine

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.brh.digiturbine.ui.main.DetailFragment
import com.brh.digiturbine.ui.main.ListFragment

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    lateinit var viewProgress: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewProgress = findViewById(R.id.progress)

        //if detailView is present then we know this is dual screen mode
        val detailView: FragmentContainerView? = findViewById(R.id.fcv_detail)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fcv_list, ListFragment())
                detailView?.let {
                    add(R.id.fcv_detail, DetailFragment())
                }
            }
        }
    }

    fun showDetail(){
        supportFragmentManager.commit {
            addToBackStack(DetailFragment.BACKSTACK_NAME)
            add(R.id.fcv_list, DetailFragment())
        }
    }

    fun showProgress(show: Boolean) {
        if (show)
            viewProgress.show()
        else
            viewProgress.hide()
    }
}