package com.example.shoppingMall.domain;


import com.example.shoppingMall.exception.ExistFieldException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional // 모든 @Test에 트랜잭션 적용.
public class MemberRepositoryTest {

    //참고 @Transactional 어노테이션은 Test가 끝나면 작업들을 Rollback하기 때문에
    // 다른 테스트에 영향을 주지 않지만, @Transactional이 없고 DB에 영구 반영되면
    // afterEach로 다른 테스트에 영향 미치지 않게 해야함.

    @Autowired
    MemberRepository repository = new MemberRepository();

    @Test
    public void joinTest(){
        Member member = new Member();
        member.setName("최씨");
        member.setNickName("allisWell");
        member.setLevel(Level.USER);
        member.setUser_id("chy123");
        member.setPassword("k123");
        member.setPhone("010-1234-1234");
        member.setRegister_date(LocalDateTime.now());

        repository.Join(member);
    }

    //중복 유저 예외 테스트
    @Test
    public void ExistJoinTest() throws RuntimeException{
        Member member = new Member();
        member.setUser_id("b-free");
        repository.Join(member);

        Member member1 = new Member();
        member1.setUser_id("b-free");
        member1.setPhone("1234-808");
        assertThatThrownBy(() -> repository.Join(member1))
                .isInstanceOf(ExistFieldException.class);
    }
    

}