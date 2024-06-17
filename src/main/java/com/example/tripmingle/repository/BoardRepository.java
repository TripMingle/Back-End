package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    //컬렉션 포함해서 로직 재작성 필요
    @Query("SELECT b FROM Board b WHERE b.countryName = :countryName AND " +
            "(:gender IS NULL OR b.user.gender = :gender) AND " +
            "(:language IS NULL OR b.language = :language)")
    Page<Board> findAllByCountryNameAndFilters(String countryName, String gender, String language, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.countryName = :countryName")
    Page<Board> findByCountryName(String countryName, Pageable pageable);

    default List<Board> findRecentBoards(String countryName) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Board> page = findByCountryName(countryName, pageable);
        return page.getContent();
        //return findAll();
    }

    Optional<Board> findById(Long Id);

    @Query("SELECT b FROM Board b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    List<Board> searchByTitleOrContent(String keyword);

}
