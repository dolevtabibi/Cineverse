import com.app.cineverse.utils.Resource

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.error(e.message ?: "An unknown Error Occurred")
    }
}