
import com.google.inject.AbstractModule
import play.api.Logger

class MainModule extends AbstractModule {
  def configure() = bind(classOf[InitialData]).asEagerSingleton
}