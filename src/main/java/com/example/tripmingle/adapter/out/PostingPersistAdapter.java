package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.PostingNotFoundException;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingType;
import com.example.tripmingle.port.out.PostingPersistPort;
import com.example.tripmingle.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostingPersistAdapter implements PostingPersistPort {

    private final PostingRepository postingRepository;

    @Override
    public Long createPosting(Posting posting) {
        return postingRepository.save(posting).getId();
    }

    @Override
    public Posting getPostingById(Long postingId) {
        return postingRepository.findById(postingId).orElseThrow(() -> new PostingNotFoundException("Posting Not Found.", ErrorCode.POSING_NOT_FOUND));
    }

    @Override
    public List<Posting> findAllPostingForPreview(String country, PostingType postingType) {
        return postingRepository.findTop10ByCountryAndPostingTypeOrderByCreatedAtDesc(country, postingType);
    }

    @Override
    public Page<Posting> getAllPostings(String country, PostingType postingType, Pageable pageable) {
        return postingRepository.findAllByCountryAndPostingType(country, postingType, pageable);
    }

    @Override
    public Page<Posting> getSearchPostings(String keyword, Pageable pageable) {
        return postingRepository.findAllBySearching(keyword.toLowerCase(), pageable);
    }

}
