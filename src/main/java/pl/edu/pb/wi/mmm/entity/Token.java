package pl.edu.pb.wi.mmm.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.edu.pb.wi.mmm.enumeration.TokenType;

@Entity
@Table(name = "auth_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Token {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "token", unique = true)
    public String token;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    @Column(name = "revoked")
    public boolean revoked;

    @Column(name = "expired")
    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}

