package service.custom.impl;

import model.Item;
import repository.RepositoryFactory;
import repository.custom.ItemRepository;
import service.custom.ItemService;
import util.RepositoryType;

import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService {


    ItemRepository itemRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ITEM);

    @Override
    public boolean addItem(Item item) {
        return itemRepository.create(item);
    }

    @Override
    public boolean updateItem(Item item) {
        return itemRepository.update(item);
    }

    @Override
    public boolean deleteItem(String id) {
        return itemRepository.deleteById(id);

    }


    @Override
    public List<Item> getAllItems() throws SQLException {
        return itemRepository.getAll();
    }

    @Override
    public List<String> getItemCodes() throws SQLException {
        return itemRepository.getItemCodes();
    }

    @Override
    public Item getItemByCode(String code) throws SQLException {
        return itemRepository.getById(code);
    }
}
