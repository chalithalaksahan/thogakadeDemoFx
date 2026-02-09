package repository.custom;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import model.Order;
import repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String> {
}
