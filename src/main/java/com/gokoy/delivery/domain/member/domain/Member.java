package com.gokoy.delivery.domain.member.domain;

import com.gokoy.delivery.domain.order.domain.Order;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    private String nickname;

    @ElementCollection
    @CollectionTable(name = "member_addresses", joinColumns = {
            @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    })
    private List<Address> addresses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Grade grade;

    //연관관계

    @OneToMany(mappedBy = "orderer")
    List<Order> orders = new ArrayList<>();

    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.role = "MEMBER";
        this.nickname = nickname;
        this.grade = Grade.THANKFUL;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
