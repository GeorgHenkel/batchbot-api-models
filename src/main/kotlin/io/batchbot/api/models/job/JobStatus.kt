package io.batchbot.api.models.job

enum class JobStatus {
    STARTED,
    FINISHED,
    ABORTED,
    PAUSED,
    CONTINUED,
    RESTARTED
}