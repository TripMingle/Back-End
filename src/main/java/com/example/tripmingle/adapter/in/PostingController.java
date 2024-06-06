package com.example.tripmingle.adapter.in;

import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostingController {
    private final PostingUseCase postingUseCase;
    private final PostingCommentUseCase postingCommentUseCase;

    //포스트 미리보기(나라입력, 최근n개)
    public void getRecentPostings(){
        postingUseCase.getRecentPostings();
    }

    //포스트 전체조회(나라입력, 페이징)
    public void getAllPostings(){
        postingUseCase.getAllPostings();
    }

    //단일포스트 조회하기(댓글포함)
    public void getPostingInfo(){
        postingUseCase.getPostingInfo();
    }

    //포스트 게시하기
    public void createPosting(){
        postingUseCase.createPosting();
    }

    //포스트 수정하기
    public void updatePosting(){
        postingUseCase.updatePosting();
    }

    //포스트 삭제하기
    public void deletePosting(){
        postingUseCase.deletePosting();
    }

    //포스트 댓글달기 (대댓글도 같은 API사용)
    public void createPostingComment(){
        postingCommentUseCase.createPostingComment();
    }

    //포스트 댓글수정 (대댓글도 같은 API사용)
    public void updatePostingComment(){
        postingCommentUseCase.updatePostingComment();
    }

    //포스트 댓글삭제 (대댓글도 같은 API사용)
    public void deletePostingComment(){
        postingCommentUseCase.deletePostingComment();
    }


}
