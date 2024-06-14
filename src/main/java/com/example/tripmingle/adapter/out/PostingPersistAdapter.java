package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.PostingNotFoundException;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.port.out.PostingPersistPort;
import com.example.tripmingle.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostingPersistAdapter implements PostingPersistPort {

    private final PostingRepository postingRepository;

    @Override
    public void getRecentPostings() {

    }

    @Override
    public void getAllPostings() {

    }

    @Override
    public Long createPosting(Posting posting) {
        return postingRepository.save(posting).getId();
    }

    @Override
    public void updatePosting() {

    }

    @Override
    public Posting getPostingById(Long postingId) {
        return postingRepository.findById(postingId).orElseThrow(() -> new PostingNotFoundException("Posting Not Found.", ErrorCode.POSING_NOT_FOUND));
    }

    @Override
    public void deletePostingById() {

    }
}
