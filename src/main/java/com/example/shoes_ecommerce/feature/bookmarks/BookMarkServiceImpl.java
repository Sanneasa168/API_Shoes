package com.example.shoes_ecommerce.feature.bookmarks;

import com.example.shoes_ecommerce.domain.Bookmarks;
import com.example.shoes_ecommerce.domain.Products;
import com.example.shoes_ecommerce.domain.Users;
import com.example.shoes_ecommerce.feature.bookmarks.dto.BookMarkResponse;
import com.example.shoes_ecommerce.feature.bookmarks.dto.BookMarkRequest;
import com.example.shoes_ecommerce.feature.products.ProductRepository;
import com.example.shoes_ecommerce.feature.user.UsersRepository;
import com.example.shoes_ecommerce.mapping.BookMarkMapping;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookMarkServiceImpl implements BookMarkService {

    private final ProductRepository productRepository;
    private final BookMarkRepository bookMarkRepository;
    private final BookMarkMapping bookMarkMapping;
    private final UsersRepository usersRepository;

    @Override
    public BookMarkResponse createBookMark(BookMarkRequest bookMarksRequest) {

        // Validate ProductUuid
        Products products = productRepository
                .findByUuid(bookMarksRequest.productUuid())
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Product Uuid has not been found"
                                ));

        // Validate UsersUuid
        Users users = usersRepository
                .findByUuid(bookMarksRequest.userUuid())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User Uuid has not been found"
                        ));

        Bookmarks bookmarks = bookMarkMapping.toBookMarksRequest(bookMarksRequest);

        // Set additional data
        bookmarks.setUsers(users);
        bookmarks.setProducts(products);
        bookmarks.setUuid(UUID.randomUUID().toString());
        bookmarks.setIsBookmark(true);

        // Save Bookmark
        bookMarkRepository.save(bookmarks);

        // Return the Response DTO
        return bookMarkMapping.fromBookMarksResponse(bookmarks);
    }

    @Override
    public void deleteBookMark(String uuid) {

        Bookmarks bookmarks = bookMarkRepository
                .findBookmarksByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Bookmark Uuid has  not been found"
                        )
                );

        bookMarkRepository.delete(bookmarks);
    }

    @Override
    public List<BookMarkResponse> findByList() {

        Sort   sortById = Sort.by(Sort.Direction.DESC, "id");
        List<Bookmarks> bookmarks = bookMarkRepository.findAll(sortById);
        return bookMarkMapping.fromBookMarksResponseList(bookmarks);

    }

}
