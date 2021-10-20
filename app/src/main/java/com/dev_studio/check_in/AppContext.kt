package com.dev_studio.check_in

import android.content.Context
import com.dev_studio.check_in.ui.MainActivity

class AppContext {
    fun instance(appContext: Context): AppContext {
        if (context == null) {
            context = AppContext()
        }
        return context
    }

    fun updateActivityContext(context:MainActivity){
        activityContext=context
    }
    fun activityContext(): MainActivity {
        return activityContext
    }

    companion object {
        var context: AppContext
            get() {
              return  context
            }
            set(value) {
                context=value
            }
        lateinit var activityContext:MainActivity
    }
}