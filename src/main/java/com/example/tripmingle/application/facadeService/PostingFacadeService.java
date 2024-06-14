package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.PostingCommentService;
import com.example.tripmingle.application.service.PostingService;
import com.example.tripmingle.dto.req.DeletePostingReqDTO;
import com.example.tripmingle.dto.req.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.dto.res.DeletePostingResDTO;
import com.example.tripmingle.dto.res.PatchPostingResDTO;
import com.example.tripmingle.dto.res.PostPostingResDTO;
import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostingFacadeService implements PostingUseCase, PostingCommentUseCase {

    private final PostingService postingService;
    private final PostingCommentService postingCommentService;

    @Override
    public void getRecentPostings() {
        postingService.getRecentPostings();
    }

    @Override
    public void getAllPostings() {
        postingService.getAllPostings();
    }

    @Transactional
    @Override
    public PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO) {
        return postingService.createPosting(postPostingReqDTO);

    }

    @Transactional
    @Override
    public PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO) {
        return postingService.updatePosting(patchPostingReqDTO);
    }

    @Transactional
    @Override
    public DeletePostingResDTO deletePosting(DeletePostingReqDTO deletePostingReqDTO) {
        return postingService.deletePosting(deletePostingReqDTO);
    }

    @Override
    public void getPostingInfo() {
        postingService.getPostingInfo();
        postingCommentService.getPostingCommentsByPostingId();
    }

    @Override
    public void createPostingComment() {
        postingCommentService.createPostingComment();
    }

    @Override
    public void updatePostingComment() {
        postingCommentService.updatePostingComment();
    }

    @Override
    public void deletePostingComment() {
        postingCommentService.deletePostingComment();
    }
}
