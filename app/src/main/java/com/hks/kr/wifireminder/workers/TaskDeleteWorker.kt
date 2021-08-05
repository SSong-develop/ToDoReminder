package com.hks.kr.wifireminder.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hks.kr.wifireminder.api.local.database.TaskDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

/**
 * TODO : 할일의 시작날과 끝나는 날이 있는 형태로 테이블을 변경할껀데 , 끝나는 날이 지난 TASK의 경우엔 지워져야한다.
 * TODO : 그때 유저가 일일히 지워줄 수 있겠지만 , WorkManager를 이용해서 날이 지난 TASK는 지울 수 있도록 하자
 */
@HiltWorker
class TaskDeleteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context,params) {
    
    @Inject
    lateinit var taskDao : TaskDao
    
    override suspend fun doWork(): Result {
        return Result.success()
    }

}