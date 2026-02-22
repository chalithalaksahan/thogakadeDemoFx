package config;

import com.google.inject.AbstractModule;
import repository.custom.CustomerRepository;
import repository.custom.ItemRepository;
import repository.custom.OrderRepository;
import repository.custom.impl.CustomerRepositoryImpl;
import repository.custom.impl.ItemRepositoryImpl;
import repository.custom.impl.OrderRepositoryImpl;
import service.custom.CustomerService;
import service.custom.ItemService;
import service.custom.OrderService;
import service.custom.impl.CustomerServiceimpl;
import service.custom.impl.ItemServiceImpl;
import service.custom.impl.OrderServiceImpl;

public class AppModule extends AbstractModule {
    @Override
    protected  void configure(){
        bind(CustomerService.class).to(CustomerServiceimpl.class);
        bind(CustomerRepository.class).to(CustomerRepositoryImpl.class);
        bind(ItemService.class).to(ItemServiceImpl.class);
        bind(ItemRepository.class).to(ItemRepositoryImpl.class);
        bind(OrderService.class).to(OrderServiceImpl.class);
        bind(OrderRepository.class).to(OrderRepositoryImpl.class);
    }
}
