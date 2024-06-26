package com.example.tripmingle.repository;

import com.example.tripmingle.entity.PostingComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingCommentRepository extends JpaRepository<PostingComment, Long> {
    @Query("select pc.id, pc.comment, pc.posting, pc.user, pc.postingComment from PostingComment pc where pc.postingComment.id=:parentCommentId")
    List<PostingComment> findCoCommentsByParentCommentId(Long parentCommentId);

    List<PostingComment> findAllByPostingIdOrderByCreatedAt(Long postingId);

    List<PostingComment> findAllByPostingCommentId(Long parentPostingCommentId);
}
