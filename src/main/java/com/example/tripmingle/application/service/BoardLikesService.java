package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardLikes;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardLikesPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardLikesService {
    private final UserPersistPort userPersistPort;
    private final BoardLikesPersistPort boardLikesPersistPort;
    public List<BoardLikes> getMyLikedBoards(User currentUser) {
        return boardLikesPersistPort.findBoardLikesByUser(currentUser);
    }

    public boolean toggleBoardLikes(Board board) {
        User currentUser = userPersistPort.findCurrentUserByEmail();
        BoardLikes boardLikes;
        if(boardLikesPersistPort.existsBoardBookMarkByUserAndBoard(currentUser,board)){
            boardLikes = boardLikesPersistPort.findByUserAndBoard(currentUser,board);
            boardLikes.toggleBoardLikes();
        }
        else{
            boardLikes = BoardLikes.builder()
                    .user(currentUser)
                    .board(board)
                    .isActive(true)
                    .build();
            boardLikesPersistPort.saveBoardLikes(boardLikes);
        }
        return boardLikes.isActive();
    }


    public boolean isLikedBoard(User currentUser, Board board) {
        if(boardLikesPersistPort.existsBoardBookMarkByUserAndBoard(currentUser,board)){
            return boardLikesPersistPort.findByUserAndBoard(currentUser,board).isActive();
        }
        else return false;
    }
}