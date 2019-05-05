/*
package com.voldy.myblog.repository.es;

import com.voldy.myblog.domain.es.EsBlog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = esBlogRepositoryTest.class)
public class esBlogRepositoryTest {

    @Resource
    private EsBlogRepository esBlogRepository;
    @Before
    public void setUp() throws Exception {

        esBlogRepository.deleteAll();

        esBlogRepository.save(new EsBlog("登鹳雀楼","登鹳雀楼王之涣","白日依山尽,黄河入海流。\n" +
                "欲穷千里目，更上一层楼。"));
        esBlogRepository.save(new EsBlog("静夜思","静夜思李白","床前明月光，疑是地上霜。 " +
                "举头望明月，低头思故乡。"));
        esBlogRepository.save(new EsBlog("相思","相思王维","红豆生南国，春来发几枝。\n" +
                "愿君多采撷，此物最相思。"));

    }
    @Test
    public void testFindDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(){
       */
/* Pageable pageable = new PageRequest(0, 20);
        String title = "思";
        String summary = "相思";
        String content = "相思";
        Page<EsBlog> page = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(
                title,summary,content,pageable
        );
        assertEquals(2,page.getTotalElements());

        page.getContent().forEach(System.out::println);*//*

        System.out.println("test...");
    }
    @After
    public void tearDown() throws Exception {
    }
}*/
