//package com.example.shoppingMall.domain;
//
//import com.example.shoppingMall.repository.CartItemRepository;
//import com.example.shoppingMall.repository.ItemRepository;
//import com.example.shoppingMall.repository.MemberRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@Transactional
//public class CartItemRepositoryTest {
//    @Autowired
//    private CartItemRepository cartItemrepository = new CartItemRepository();
//
//    @Autowired
//    private MemberRepository memberRepository = new MemberRepository();
//
//    @Autowired
//    private ItemRepository itemRepository = new ItemRepository();
//
//    @Test
//    public void saveCartItem(){
//        //given
//        //회원 등록
//        Member member = Member.builder()
//                .userId("adkdkdk")
//                .password("123")
//                .name("최환용")
//                .nickName("okMe")
//                .address(new Address("city of seoul","moo","1412"))
//                .phone("010-7359-4777")
//                .level(Level.ROLE_USER)
//                .registerDate(LocalDateTime.now())
//                .build();
//
//        //장바구니 및 아이템 생성
//        member.setCart(new Cart());
//
//        Item item1 = newItem("비누",1000L,100L);
//        Item item2 = newItem("칫솔",1500L,120L);
//        Item item3 = newItem("허니버터 칩",1900L,10L);
//
//        //장바구니에 여러 아이템 삽입
//
//        CartItem cartItem1 = newCartItem(item1,10L);
//        CartItem cartItem2 = newCartItem(item2,13L);
//        CartItem cartItem3 = newCartItem(item3,3L);
//
//
//        Cart cart = member.getCart();
//        cart.addCartItem(cartItem1);
//        cart.addCartItem(cartItem2);
//        cart.addCartItem(cartItem3);
//
//        //when
//        memberRepository.Join(member);
//        List<CartItem> cartItems = memberRepository.findById(member.getId()).getCart().getCartItems();
//        for (CartItem item : cartItems) {
//            System.out.println(item.getItem().getName());
//        }
//
//        //then
//    }
//
//    public Item newItem(String name,Long price,Long quantity){
//        Item item = new Item();
//        item.setName(name);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//        itemRepository.saveItem(item);
//        return item;
//    }
//
//    public CartItem newCartItem(Item item,Long quantity){
//        CartItem cartItem = new CartItem();
//        cartItem.setItem(item);
//        cartItem.setQuantity(quantity);
//        cartItemrepository.saveCartItem(cartItem);
//        return cartItem;
//    }
//}
