package com.example.shoppingMall.domain;


import com.example.shoppingMall.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


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
        Member member = new Member.Builder("chy").name("choi").nickName("okMe").build();
        Long id = repository.Join(member);
        Member findMember = repository.findById(id);
        assertThat(member.getNickName()).isEqualTo(findMember.getNickName());
    }

    /*중복 유저 예외 테스트
    해당 테스트 코드가 통과하기 위한 특이사항: member1을 Join후 Join 코드 내부에서 em.flush를 해서
    진짜로 db에 반영해야 된다. flush같은 db 반영은 @Transactional이 걸린 어노테이션의 작업이 끝날 때
    반영된다. 따라서 member1을 Join한다고 해서 실제 db에 저장되지 않음.

    예시)
    1. member1 Join -> 영속성 컨텍스트에 저장 (DB 반영 X)
    2. member2 Join -> 영속성 컨텍스트에 저장 (DB 반영 X)과 동시에 assertThatThrownBy에서 Join 수행해봤는데
    오류가 안터지는데? 하고 테스트 코드 실패됨.
    */

    /*그렇다고 MemberRepositoryTest에 @Rollback(false)를 달면 테스트 코드 내부에서는 assertThatThrownBy에서
     오류를 못잡아내고 (왜냐하면 아직 둘 다 영속성에 있으니까) 테스트 코드가 끝난 시점에 Rollback(false)이기 때문에
    * DB에 반영해야되서 반영하는 순간 그때 오류가 터져버림 즉 오류를 못잡아냄. -> 실패*/

    /*테스트 코드 성공하는 방법
    1. member1 Join -> 영속성 컨텍스트에 저장 (DB 반영 X)
    2. flush (DB 반영 O)
    3. member2 Join -> 영속성 컨텍스트에 저장 (DB 반영 X)
    4. flush 하고 DB에 반영하려는 순간 DataIntegrityViolationException가 터지고 던져짐.
    5. 던져진 에러가 assertThatThrownBy에 의해 잡히고 테스트 코드 통과됨.

    * 결론: 트랜잭션 안에서 2명 이상에 대한 작업을 수행하지 말자*/

    @Test
    public void ExistJoinTest() throws RuntimeException{
        Member member1 = new Member.Builder("chy").name("choi").nickName("okMe").build();
        repository.Join(member1);
        Member member2 = new Member.Builder("chy").name("kim").nickName("jiggle").build();
        assertThatThrownBy(()->repository.Join(member2)).isInstanceOf(DataIntegrityViolationException.class);
    }
    

}