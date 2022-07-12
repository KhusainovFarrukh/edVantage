package kh.farrukh.edvantage.utils.pagination;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PagingNavItem {

    private PagingNavItemType itemType;

    private int index;

    private boolean active;

    public PagingNavItem(PagingNavItemType itemType, boolean active) {
        this.itemType = itemType;
        this.active = active;
    }
}
