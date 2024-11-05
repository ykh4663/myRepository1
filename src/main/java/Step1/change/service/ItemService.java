package Step1.change.service;

import Step1.change.Repository.ItemRepository;
import Step1.change.domain.item.Item;
import Step1.change.dto.item.UpdateItemRequest;
import Step1.change.exception.item.DuplicateItemException;
import Step1.change.exception.item.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long saveItem(Item item){
        validateDuplicateName(item);
        itemRepository.save(item);
        return item.getId();


    }

    private void validateDuplicateName(Item item) {
        List<Item> fitem = itemRepository.findByName(item.getName());
        if(!fitem.isEmpty()){
            throw new DuplicateItemException("이미 존재하는 아이템입니다");
        }

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findById(itemId)
                .orElseThrow(()->new ItemNotFoundException("해당 아이템이 존재하지 않습니다. ID: " + itemId));

    }

    @Transactional
    public void updateItemNPS(Long itemId, UpdateItemRequest request){
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("해당 아이템이 존재하지 않습니다. ID: " + itemId));
        findItem.updateNPS(request.getName(), request.getPrice(), request.getStockQuantity());

    }


}
