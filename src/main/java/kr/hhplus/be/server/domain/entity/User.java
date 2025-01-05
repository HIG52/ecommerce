package kr.hhplus.be.server.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modify_at")
    private LocalDateTime modifyAt;

}
