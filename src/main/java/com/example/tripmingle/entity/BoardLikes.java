package com.example.tripmingle.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="board_id", nullable = false)
    private Board board;


    @Column(name = "is_active")
    private boolean isActive;

    public boolean toggleBoardLikes(){
        this.isActive = !this.isActive;
        return this.isActive;
    }

    public void delete(){
        this.isActive = false;
    }
}
