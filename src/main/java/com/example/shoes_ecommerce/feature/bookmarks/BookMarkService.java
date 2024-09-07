package com.example.shoes_ecommerce.feature.bookmarks;

import com.example.shoes_ecommerce.feature.bookmarks.dto.BookMarkResponse;
import com.example.shoes_ecommerce.feature.bookmarks.dto.BookMarkRequest;
import java.util.List;

public interface BookMarkService {

    BookMarkResponse  createBookMark(BookMarkRequest bookMarksRequest);

    void deleteBookMark(String uuid);

    List<BookMarkResponse> findByList();

}
