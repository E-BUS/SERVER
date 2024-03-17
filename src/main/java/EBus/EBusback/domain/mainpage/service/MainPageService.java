package EBus.EBusback.domain.mainpage.service;

import EBus.EBusback.domain.lostItem.entity.LostItem;
import EBus.EBusback.domain.lostItem.repository.LostItemRepository;
import EBus.EBusback.domain.mainpage.dto.MainItemResDto;
import EBus.EBusback.domain.mainpage.dto.MainNoticeResDto;
import EBus.EBusback.domain.mainpage.dto.MainPageResDto;
import EBus.EBusback.domain.mainpage.dto.MainPostResDto;
import EBus.EBusback.domain.notice.entity.Notice;
import EBus.EBusback.domain.notice.repository.NoticeRepository;
import EBus.EBusback.domain.post.entity.Post;
import EBus.EBusback.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainPageService {
    private final NoticeRepository noticeRepository;
    private final LostItemRepository lostItemRepository;
    private final PostRepository postRepository;

    public MainPageResDto getMainPage(){
        List<Notice> allNotices = noticeRepository.findAllByOrderByCreatedDateDesc();
        List<MainNoticeResDto> noticeList = new ArrayList<>();
        if(!(allNotices.isEmpty())){
            for (int i=0; i<1; i++){
                noticeList.add(new MainNoticeResDto(allNotices.get(i)));
            }
        }

        List<LostItem> allLostItems = lostItemRepository.findAllByOrderByFoundDateDesc();
        List<MainItemResDto> lostItemList = new ArrayList<>();
        if(!allLostItems.isEmpty()){
            if (allLostItems.size()<5){
                for (LostItem lostItem : allLostItems) {
                    lostItemList.add(new MainItemResDto(lostItem));
                }
            }
            else {
                for (int i=0; i<5; i++){
                    lostItemList.add(new MainItemResDto(allLostItems.get(i)));
                }
            }
        }

        List<Post> allSuggestion = postRepository.findAllByIsSuggestionOrderByCreatedDate(true);
        List<MainPostResDto> suggestionList = new ArrayList<>();
        if (!(allSuggestion.isEmpty())){
            if (allSuggestion.size()<3){
                for (Post post : allSuggestion) {
                    suggestionList.add(new MainPostResDto(post));
                }
            }
            else {
                for (int i=0; i<3; i++){
                    suggestionList.add(new MainPostResDto(allSuggestion.get(i)));
                }
            }
        }

        List<Post> allAppreciation = postRepository.findAllByIsSuggestionOrderByCreatedDate(false);
        List<MainPostResDto> appreciationList = new ArrayList<>();
        if (!(allAppreciation.isEmpty())){
            if(allAppreciation.size()<3){
                for(Post post : allAppreciation) {
                    appreciationList.add(new MainPostResDto(post));
                }
            }
            else{
                for (int i=0; i<3; i++) {
                    suggestionList.add(new MainPostResDto(allAppreciation.get(i)));
                }
            }
        }

        return MainPageResDto.builder()
                .notice(noticeList)
                .items(lostItemList)
                .suggestion(suggestionList)
                .appreciation(appreciationList)
                .build();
    }
}
