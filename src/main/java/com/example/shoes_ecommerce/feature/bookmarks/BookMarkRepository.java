package com.example.shoes_ecommerce.feature.bookmarks;

import com.example.shoes_ecommerce.domain.Bookmarks;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<Bookmarks ,Integer> {

       Optional<Bookmarks> findBookmarksByUuid(String uuid);

       boolean existsByUuid(String uuid);
}
