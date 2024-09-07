package com.example.shoes_ecommerce.feature.bookmarks;

import com.example.shoes_ecommerce.feature.bookmarks.dto.BookMarkRequest;
import com.example.shoes_ecommerce.feature.bookmarks.dto.BookMarkResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/bookmarks")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    BookMarkResponse createBookMark(@Valid  @RequestBody BookMarkRequest bookMarkRequest) {
        return bookMarkService.createBookMark(bookMarkRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping
    List<BookMarkResponse> findList() {
        return  bookMarkService.findByList();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deleteBookMark(@Valid @PathVariable("uuid") String uuid) {
        bookMarkService.deleteBookMark(uuid);
    }

}
