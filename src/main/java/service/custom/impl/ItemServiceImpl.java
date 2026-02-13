package service.custom.impl;

import model.Item;
import repository.RepositoryFactory;
import repository.custom.ItemRepository;
import service.custom.ItemService;
import util.RepositoryType;

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
    public Item searchItemById(String id) {
        return itemRepository.getById(id);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.getAll();
    }
}
