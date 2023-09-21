package page.aaws.springex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDto<E> {
    /**
     * 현재 페이지 번호
     */
    private int page;

    /**
     * 페이지 당 아이템 개수
     */
    private int size;

    /**
     * 총 페이지 개수
     */
    private int count;

    /**
     * 총 아이템 개수
     */
    private int itemCount;

    /**
     * 내비게이션 시작 페이지 번호
     */
    private int start;

    /**
     * 내비게이션 종료 페이지 번호
     */
    private int end;

    /**
     * 내비게이션 시작 페이지의 이전 페이지 번호
     */
    private int lastPageOfPreviousBand;

    /**
     * 내비게이션 종료 페이지의 다음 페이지 번호
     */
    private int startPageOfNextBand;

    /**
     * 내비게이션 시작 페이지의 이전 페이지 존재 여부
     */
    private boolean prev;

    /**
     * 내비게이션 종료 페이지의 이후 페이지 존재 여부
     */
    private boolean next;

    /**
     * 아이템 목록
     */
    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDto(PageRequestDto pageRequestDto, List<E> dtoList, int itemCount) {
        this.page = pageRequestDto.getPage();
        this.size = pageRequestDto.getSize();

        this.itemCount = itemCount;
        this.count = this.itemCount / this.size + (this.itemCount % this.size > 0 ? 1 : 0);

        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page / 10.0)) * 10;
        this.start = this.end - 9;

        if (this.start > 1) {
            this.lastPageOfPreviousBand = this.start - 1;
        }
        if (this.end < this.count) {
            this.startPageOfNextBand = this.end + 1;
        }

        int last = (int)(Math.ceil((itemCount /(double)size)));
        this.end = Math.min(end, last);
        this.prev = this.start > 1;
        this.next = itemCount > this.end * this.size;
    }
}
