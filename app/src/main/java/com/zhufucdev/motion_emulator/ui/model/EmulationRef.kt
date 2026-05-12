package com.zhufucdev.motion_emulator.ui.model

import com.zhufucdev.me.stub.Box
import com.zhufucdev.me.stub.CoordinateSystem
import com.zhufucdev.me.stub.Data
import com.zhufucdev.me.stub.Emulation
import com.zhufucdev.me.stub.MapProjector
import com.zhufucdev.me.stub.Trace
import com.zhufucdev.motion_emulator.data.Telephonies
import com.zhufucdev.motion_emulator.data.Motions
import com.zhufucdev.motion_emulator.data.Traces
import com.zhufucdev.motion_emulator.extension.StoredBox
import kotlinx.serialization.Serializable

@Serializable
data class EmulationRef(
    override val id: String,
    val name: String,
    val trace: String,
    val motion: String,
    val cells: String,
    val velocity: Double,
    val repeat: Int,
    val satelliteCount: Int,
) : Data

fun EmulationRef.emulation(): Emulation {
    val traceBox = StoredBox(trace, Traces)
    val convertedTraceBox = if (traceBox is Box<Trace>) {
        val t = traceBox.value
        if (t.coordinateSystem == CoordinateSystem.GCJ02) {
            val wgsPoints = t.points.map { point ->
                with(MapProjector) { point.toIdeal() }.toPoint(CoordinateSystem.WGS84)
            }
            Box(t.copy(points = wgsPoints, coordinateSystem = CoordinateSystem.WGS84))
        } else {
            traceBox
        }
    } else {
        traceBox
    }

    return Emulation(
        trace = convertedTraceBox,
        motion = StoredBox(motion, Motions),
        cells = StoredBox(cells, Telephonies),
        repeat = repeat,
        velocity = velocity,
        satelliteCount = satelliteCount
    )
}