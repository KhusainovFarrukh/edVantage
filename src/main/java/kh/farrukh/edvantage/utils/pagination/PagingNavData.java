package kh.farrukh.edvantage.utils.pagination;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingNavData {

    private static final int PAGINATION_STEP = 2;

    private boolean nextEnabled;
    private boolean prevEnabled;
    private int pageSize;
    private int pageNumber;

    private List<PagingNavItem> items = new ArrayList<>();

    public void addPageItems(int from, int to, int pageNumber) {
        for (int i = from; i < to; i++) {
            items.add(new PagingNavItem(
                    PagingNavItemType.PAGE, i, pageNumber != i
            ));
        }
    }

    public void last(int pageSize) {
        items.add(new PagingNavItem(PagingNavItemType.DOTS, false));
        items.add(new PagingNavItem(PagingNavItemType.PAGE, pageSize, true));
    }

    public void first(int pageNumber) {
        items.add(new PagingNavItem(PagingNavItemType.PAGE, 1, pageNumber != 1));
        items.add(new PagingNavItem(PagingNavItemType.DOTS, false));
    }

    public PagingNavData(int totalPages, int pageNumber, int pageSize) {
        setPageSize(pageSize);
        setNextEnabled(pageNumber != totalPages);
        setPrevEnabled(pageNumber != 1);
        setPageNumber(pageNumber);

        // TODO: 7/13/22 needs improvement
        if (totalPages < PAGINATION_STEP * 2 + 6) {
            addPageItems(1, totalPages + 1, pageNumber);

        } else if (pageNumber < PAGINATION_STEP * 2 + 1) {
            addPageItems(1, PAGINATION_STEP * 2 + 4, pageNumber);
            last(totalPages);

        } else if (pageNumber > totalPages - PAGINATION_STEP * 2) {
            first(pageNumber);
            addPageItems(totalPages - PAGINATION_STEP * 2 - 2, totalPages + 1, pageNumber);

        } else {
            first(pageNumber);
            addPageItems(pageNumber - PAGINATION_STEP, pageNumber + PAGINATION_STEP + 1, pageNumber);
            last(totalPages);
        }
    }
}