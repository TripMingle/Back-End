package com.example.tripmingle.application.service;


import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.req.posting.GetAllPostingsReqDTO;
import com.example.tripmingle.dto.req.posting.GetPreviewPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingReqDTO;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingPersistPort postingPersistPort;
    private final UserUtils userUtils;

    public Long createPosting(PostPostingReqDTO postPostingReqDTO, User currentUser) {
        Posting posting = Posting.builder()
                .title(postPostingReqDTO.getTitle())
                .content(postPostingReqDTO.getContent())
                .postingType(postPostingReqDTO.getPostingType())
                .country(postPostingReqDTO.getCountry())
                .user(currentUser)
                .build();
        return postingPersistPort.createPosting(posting);
    }

    public Long updatePosting(PatchPostingReqDTO patchPostingReqDTO, User currentUser) {
        Posting posting = postingPersistPort.getPostingById(patchPostingReqDTO.getPostingId());
        userUtils.validateMasterUser(posting.getUser().getId(),currentUser.getId());
        posting.updatePosting(patchPostingReqDTO);

        return posting.getId();
    }

    public void deletePosting(Posting posting, User currentUser) {
        userUtils.validateMasterUser(posting.getUser().getId(),currentUser.getId());
        posting.deletePosting();

    }

    public List<Posting> getPreviewPostings(GetPreviewPostingReqDTO getPreviewPostingReqDTO) {
        return postingPersistPort.findAllPostingForPreview(getPreviewPostingReqDTO.getCountry(), getPreviewPostingReqDTO.getPostingType());
    }

    public Posting getOnePosting(Long postingId) {
        return postingPersistPort.getPostingById(postingId);
    }

    public Page<Posting> getAllPostings(GetAllPostingsReqDTO getAllPostingsReqDTO, Pageable pageable) {
        return postingPersistPort.getAllPostings(getAllPostingsReqDTO.getCountry(), getAllPostingsReqDTO.getPostingType(), pageable);
    }

    public Page<Posting> getSearchPostings(String keyword, Pageable pageable) {
        return postingPersistPort.getSearchPostings(keyword, pageable);
    }
}
