package kh.farrukh.edvantage.utils.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedList<T> {

    private Page<T> page;

    private PagingNavData pagingNavData;

    public PagedList(Page<T> page) {
        this.page = page;
        this.pagingNavData = new PagingNavData(
                page.getTotalPages(),
                page.getPageable().getPageNumber() + 1,
                page.getPageable().getPageSize()
        );
    }
}
