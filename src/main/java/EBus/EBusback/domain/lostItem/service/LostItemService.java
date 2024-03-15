package EBus.EBusback.domain.lostItem.service;

import EBus.EBusback.domain.lostItem.dto.ItemPostReqDto;
import EBus.EBusback.domain.lostItem.dto.ItemPostResDto;
import EBus.EBusback.domain.lostItem.dto.ItemSearchResDto;
import EBus.EBusback.domain.lostItem.entity.LostItem;
import EBus.EBusback.domain.lostItem.repository.LostItemRepository;
import EBus.EBusback.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LostItemService {
    private final LostItemRepository lostItemRepository;

    // 분실물 글 등록
    public ItemPostResDto createLostItemPost(Member writer, String imageUrl, ItemPostReqDto itemPostReqDto) {
        LostItem lostItem = LostItem.builder()
                .writer(writer)
                .title(itemPostReqDto.getTitle())
                .image(imageUrl)
                .foundDate(itemPostReqDto.getFoundDate())
                .foundTime(itemPostReqDto.getFoundTime())
                .foundLocation(itemPostReqDto.getFoundLocation())
                .depository(itemPostReqDto.getDepository())
                .build();
        lostItemRepository.save(lostItem);
        return new ItemPostResDto(lostItem);
    }

    public List<ItemSearchResDto> getSearchList(String keyword, LocalDate date){
        List<LostItem> searchItems = new ArrayList<>();
        List<ItemSearchResDto> searchResDtoList = new ArrayList<>();

        List<LostItem> lostItems = lostItemRepository.findAll();
        List<LostItem> keywordItems = new ArrayList<>();

        if ((Objects.equals(keyword, ""))){
            if (date==null){
                searchItems = lostItems;
            }
            else {
                for (LostItem lostItem : lostItems){
                    if (lostItem.getFoundDate().equals(date)){
                        searchItems.add(lostItem);
                    }
                }
            }
        }
        else {
            keywordItems = lostItemRepository.findByTitleContaining(keyword);
            if (date==null){
                searchItems = keywordItems;
            }
            else {
                for (LostItem keywordItem : keywordItems){
                    if(keywordItem.getFoundDate().equals(date)){
                        searchItems.add(keywordItem);
                    }
                }
            }
        }

        for (LostItem searchItem : searchItems) {
            ItemSearchResDto itemSearchResDto = new ItemSearchResDto(searchItem);
            searchResDtoList.add(itemSearchResDto);
        }
        return searchResDtoList;
    }
}
