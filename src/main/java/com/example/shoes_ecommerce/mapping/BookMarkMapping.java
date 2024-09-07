package com.example.shoes_ecommerce.mapping;

import com.example.shoes_ecommerce.domain.Bookmarks;
import com.example.shoes_ecommerce.feature.bookmarks.dto.BookMarkRequest;
import com.example.shoes_ecommerce.feature.bookmarks.dto.BookMarkResponse;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMarkMapping {

    Bookmarks toBookMarksRequest(BookMarkRequest bookMarksRequest);

    BookMarkResponse fromBookMarksResponse(Bookmarks bookmarks);

    List<BookMarkResponse>  fromBookMarksResponseList(List<Bookmarks> bookmarksList);
}
