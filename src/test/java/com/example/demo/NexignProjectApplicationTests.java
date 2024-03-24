//package com.example.demo;
//
//import com.example.demo.commutator.Commutator;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//class NexignProjectApplicationTests extends IntegrationTest {
//    @MockBean
//    private AppScheduler scheduler;
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//    @Autowired
//    Commutator commutator;
//
//    @Test
//    @Transactional
//    @Rollback
//    void shouldAddCdrToRepo() {
//        commutator.processAllCDRFiles("src/test_files");
//        var obj = jdbcTemplate.queryForObject("select id from cdr", Long.class);
//        System.out.println(obj);
//        Assertions.assertEquals(
//                79995556677L,
//                jdbcTemplate.queryForObject("select phone_number from cdr where phone_number=79995556677", Long.class)
//        );
//    }
//
////    @Test
////    @Transactional
////    @Rollback
////    void shouldDeleteChat() {
////        //given
////        jdbcTemplate.update("insert into chats(id) values (123)");
////
////        //when
////        service.deleteChat(123L);
////
////        //then
////        Assertions.assertTrue(
////                jdbcTemplate.queryForList("select id from chats where id = 123", Long.class).isEmpty()
////        );
////    }
//
//}
