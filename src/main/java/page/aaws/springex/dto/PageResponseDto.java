package page.aaws.springex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
public class PageResponseDto<E> {
    /**
     * 현재 페이지 번호
     */
    @Getter
    private int page;

    /**
     * 페이지 당 아이템 개수
     */
    @Getter
    private int size;

    /**
     * 총 페이지 개수
     */
    @Getter
    private int count;

    /**
     * 총 아이템 개수
     */
    @Getter
    private int itemCount;

    /**
     * 내비게이션 시작 페이지 번호
     */
    @Getter
    private int start;

    /**
     * 내비게이션 종료 페이지 번호
     */
    @Getter
    private int end;

    /**
     * 내비게이션 시작 페이지의 이전 페이지 번호
     */
    @Getter
    private int lastPageOfPreviousBand;

    /**
     * 내비게이션 종료 페이지의 다음 페이지 번호
     */
    @Getter
    private int startPageOfNextBand;

    /**
     * 내비게이션 시작 페이지의 이전 페이지 존재 여부
     */
    private boolean previousBandExist;

    /**
     * 내비게이션 종료 페이지의 이후 페이지 존재 여부
     */
    private boolean nextBandExist;

    public boolean doesPreviousBandExist() {
        return this.previousBandExist;
    }

    public boolean doesNextBandExist() {
        return this.nextBandExist;
    }

    /**
     * 아이템 목록
     */
    @Getter
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
            this.previousBandExist = true;
            this.lastPageOfPreviousBand = this.start - 1;
        }
        if (this.end < this.count) {
            this.nextBandExist = true;
            this.startPageOfNextBand = this.end + 1;
        }
    }
}
