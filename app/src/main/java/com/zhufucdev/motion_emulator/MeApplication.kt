package com.zhufucdev.motion_emulator

import android.app.Application
import com.zhufucdev.motion_emulator.data.Emulations
import com.zhufucdev.motion_emulator.data.Motions
import com.zhufucdev.motion_emulator.data.Telephonies
import com.zhufucdev.motion_emulator.data.Traces
import com.zhufucdev.motion_emulator.plugin.Plugins
import com.zhufucdev.motion_emulator.provider.Scheduler

class MeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Plugins.init(this)

        Traces.require(this)
        Motions.require(this)
        Telephonies.require(this)
        Emulations.require(this)

        Scheduler.init(this)
    }
}