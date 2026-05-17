package com.example.Edu_Video_web;

import com.example.Edu_Video_web.controller.LoginController;
import com.example.Edu_Video_web.controller.VideoController;
import com.example.Edu_Video_web.controller.AdminController;
import com.example.Edu_Video_web.controller.CourseController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EduVideoWebApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertThat(context).isNotNull();
    }

    @Test
    void allControllersAreLoaded() {
        assertThat(context.getBean(LoginController.class)).isNotNull();
        assertThat(context.getBean(VideoController.class)).isNotNull();
        assertThat(context.getBean(AdminController.class)).isNotNull();
        assertThat(context.getBean(CourseController.class)).isNotNull();
    }
}
