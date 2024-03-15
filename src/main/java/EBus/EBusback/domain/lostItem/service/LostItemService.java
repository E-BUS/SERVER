package EBus.EBusback.domain.lostItem.service;

import EBus.EBusback.domain.lostItem.dto.ItemPostReqDto;
import EBus.EBusback.domain.lostItem.dto.ItemPostResDto;
import EBus.EBusback.domain.lostItem.entity.LostItem;
import EBus.EBusback.domain.lostItem.repository.LostItemRepository;
import EBus.EBusback.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
