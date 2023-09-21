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
    private int startPage;

    /**
     * 내비게이션 종료 페이지 번호
     */
    @Getter
    private int endPage;

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
    private List<E> items;

    @Builder(builderMethodName = "withAll")
    public PageResponseDto(PageRequestDto pageRequestDto, List<E> items, int itemCount) {
        this.page = pageRequestDto.getPage();
        this.size = pageRequestDto.getSize();

        this.itemCount = itemCount;
        this.count = this.itemCount / this.size + (this.itemCount % this.size > 0 ? 1 : 0);

        this.items = items;

        this.endPage = (int)(Math.ceil(this.page / (float)this.size)) * this.size;
        this.startPage = this.endPage - this.size + 1;
        if (this.endPage > this.count) {
            this.endPage = this.count;
        }

        if (this.startPage > 1) {
            this.previousBandExist = true;
            this.lastPageOfPreviousBand = this.startPage - 1;
        }
        if (this.endPage < this.count) {
            this.nextBandExist = true;
            this.startPageOfNextBand = this.endPage + 1;
        }
    }
}
