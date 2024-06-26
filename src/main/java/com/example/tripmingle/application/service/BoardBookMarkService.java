package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardBookMark;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardBookMarkPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardBookMarkService {
    private final BoardBookMarkPersistPort boardBookMarkPersistPort;
    public boolean toggleBoardBookMark(Board board, User currentUser) {
        BoardBookMark boardBookMark;
        if(boardBookMarkPersistPort.existsBoardBookMarkByUserAndBoard(currentUser,board)){
            boardBookMark = boardBookMarkPersistPort.findByUserAndBoard(currentUser,board);
            if(boardBookMark.toggleBoardBookMark()) board.increaseBookMarkCount();
            else board.decreaseBookMarkCount(1);
        }
        else{
            boardBookMark = BoardBookMark.builder()
                    .user(currentUser)
                    .board(board)
                    .isActive(true)
                    .build();
            boardBookMarkPersistPort.saveBoardBookMark(boardBookMark);
            board.increaseBookMarkCount();
        }
        return boardBookMark.isActive();
    }


    public Page<BoardBookMark> getMyBookMarkedBoards(User user, Pageable pageable) {
        return boardBookMarkPersistPort.getBoardBookMarksByUser(user, pageable);
    }

    public boolean isBookMarkedBoard(User currentUser, Board board) {
        if(boardBookMarkPersistPort.existsBoardBookMarkByUserAndBoard(currentUser,board)){
            return boardBookMarkPersistPort.findByUserAndBoard(currentUser,board).isActive();
        }
        else return false;
    }

    public void deleteBoardBookMarksByBoardId(Long boardId) {
        boardBookMarkPersistPort.findBoardBookMarksByBoardId(boardId).stream()
                .forEach(boardBookMark -> {
                        boardBookMark.getBoard().decreaseBookMarkCount(1);
                        boardBookMark.delete();});
    }
}
