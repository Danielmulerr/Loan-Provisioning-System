package com.example.loanprovisioning.utils;

import com.example.loanprovisioning.dto.PageableDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.example.loanprovisioning.utils.RequestUtils.ASC;
import static com.example.loanprovisioning.utils.RequestUtils.SORT_BY_REGEX;

public class PageableUtils {
    public static PageableDto preparePageInfo(Page page) {
        return PageableDto.builder()
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();
    }
    public static Pageable getPageable(List<String> sortType, int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize, Sort.by(prepareSortExpression(sortType)));
    }
    public static Sort.Order[] prepareSortExpression(List<String> sortType) {
        return sortType.stream()
                .map(s -> {
                    String[] split = s.split(SORT_BY_REGEX);
                    String fieldName = split[0];
                    String direction = split.length > 1 ? split[1] : ASC;
                    return new Sort.Order(Sort.Direction.fromString(direction), fieldName);
                })
                .toArray(Sort.Order[]::new);
    }
}
