package si.kamdelat.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.eventbus.EventBus;

public class EventBusConfigurer implements InitializingBean, DisposableBean {
  private final Logger       logger = LoggerFactory.getLogger(this.getClass());
  private final EventBus     bus;
  private final List<Object> listeners;

  public EventBusConfigurer(final List<Object> listeners, final EventBus bus) {
    this.listeners = listeners;
    this.bus = bus;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    logger.trace("Registering listeners to bus.");

    for (final Object o : listeners)
      bus.register(o);
  }

  @Override
  public void destroy() throws Exception {
    logger.trace("Unregistering listeners from bus.");

    for (final Object o : listeners)
      bus.unregister(o);
  }

}
