package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.DeletePostingReqDTO;
import com.example.tripmingle.dto.req.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.dto.res.DeletePostingResDTO;
import com.example.tripmingle.dto.res.GetPreviewPostingResDTO;
import com.example.tripmingle.dto.res.PatchPostingResDTO;
import com.example.tripmingle.dto.res.PostPostingResDTO;

import java.util.List;

public interface PostingUseCase {

    PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO);

    PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO);

    DeletePostingResDTO deletePosting(DeletePostingReqDTO deletePostingReqDTO);

    List<GetPreviewPostingResDTO> getPreviewPostings();
}
