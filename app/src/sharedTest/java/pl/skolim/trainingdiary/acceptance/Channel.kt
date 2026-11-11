package pl.skolim.trainingdiary.acceptance

/** One way of running the whole app under the acceptance cases. */
enum class Channel {
    /** The whole app on the managed device, driven through Compose. */
    DEVICE,

    /** The whole app on the JVM without its UI, driven through the ViewModels' actions and state. */
    VIEW_MODEL,
}

/**
 * Put on a case that only [channel] can stage. The case is then skipped, not failed, on every other channel. Every
 * case without it runs on every channel.
 */
@Target(AnnotationTarget.FUNCTION)
annotation class OnlyOn(
    val channel: Channel,
)
