package com.example.demo.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository<T, ID> extends JpaRepository<T, ID> {
//    viết thêm phương thức dùng chung ở đây nè
}

