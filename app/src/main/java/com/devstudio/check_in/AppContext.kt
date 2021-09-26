package com.devstudio.check_in

import android.content.Context
import com.devstudio.check_in.ui.MainActivity

class AppContext {
    public fun instance(appContext: Context): AppContext {
        if (context == null) {
            context = AppContext()
        }
        return context as AppContext
    }

    fun updateActivityContext(context:MainActivity){
        activityContext=context;
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