package com.sarmad.moody.domain.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.invoke
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

class FakeDispatcherProvider(
    scheduler: TestCoroutineScheduler,
) : CoroutineDispatcherProvider {
    override val main: CoroutineDispatcher = StandardTestDispatcher(scheduler)
    override val io: CoroutineDispatcher = StandardTestDispatcher(scheduler)
    override val default: CoroutineDispatcher = StandardTestDispatcher(scheduler)
    override val unconfined: CoroutineDispatcher = StandardTestDispatcher(scheduler)
}
