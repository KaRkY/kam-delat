package si.kamdelat.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import si.kamdelat.common.EventBusConfigurer;
import si.kamdelat.gui.MainWindow;
import si.kamdelat.gui.NewVisitWindow;

import com.google.common.eventbus.EventBus;

@Configuration
public class KamDelatConfig {

  @Bean
  public EventBusConfigurer configurer() {
    return new EventBusConfigurer(Arrays.asList(mainWindow(), newVisit()), guiBus());
  }

  @Bean
  public EventBus guiBus() {
    return new EventBus("gui");
  }

  @Bean
  public MainWindow mainWindow() {
    return new MainWindow("Kam delat", guiBus());
  }

  @Bean
  public NewVisitWindow newVisit() {
    return new NewVisitWindow(mainWindow().getFrame(), "Novi obisk");
  }
}
