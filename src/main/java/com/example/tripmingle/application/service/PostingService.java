package com.example.tripmingle.application.service;


import com.example.tripmingle.dto.req.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.dto.res.PatchPostingResDTO;
import com.example.tripmingle.dto.res.PostPostingResDTO;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingPersistPort postingPersistPort;
    private final UserPersistPort userPersistPort;

    public void getRecentPostings() {
        postingPersistPort.getRecentPostings();
    }

    public void getAllPostings() {
        postingPersistPort.getAllPostings();
    }

    public PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO) {
        User user = userPersistPort.findCurrentUserByEmail();
        Posting posting = Posting.builder()
                .title(postPostingReqDTO.getTitle())
                .content(postPostingReqDTO.getContent())
                .postingType(postPostingReqDTO.getPostingType())
                .user(user)
                .build();
        Long postingId = postingPersistPort.createPosting(posting);
        return PostPostingResDTO.builder()
                .postingId(postingId)
                .build();
    }

    public PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO) {
        Posting posting = postingPersistPort.getPostingById(patchPostingReqDTO.getPostingId());
        posting.updatePosting(patchPostingReqDTO);
        return PatchPostingResDTO.builder()
                .postingId(posting.getId())
                .build();
    }

    public void deletePosting() {
        postingPersistPort.deletePostingById();
    }

    public void getPostingInfo() {
//        postingPersistPort.getPostingById();
    }
}
