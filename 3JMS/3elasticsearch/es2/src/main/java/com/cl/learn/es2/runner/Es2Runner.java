package com.cl.learn.es2.runner;

import com.cl.learn.es2.model.User;
import com.cl.learn.es2.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Es2Runner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public Es2Runner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String id = "u1";

        // 0) 清理同 ID 的旧数据
        userRepository.deleteById(id);

        // 1) 保存文档
        User user = new User(id, "亚洲", 30, "上海");
        userRepository.save(user);
        System.out.println("[ES2] 已保存文档: " + user);

        // 2) 根据ID查询
        Optional<User> got = userRepository.findById(id);
        System.out.println("[ES2] 根据ID查询: " + got.orElse(null));

        // 3) 根据名称模糊查询
        List<User> byName = userRepository.findByNameContaining("亚");
        System.out.println("[ES2] 模糊查询结果数量: " + byName.size());
        for (User u : byName) {
            System.out.println("  -> " + u);
        }

        // 4) 不删除，便于在 Kibana/DevTools 中查看
        // userRepository.deleteById(id);
        // System.out.println("[ES2] 已删除文档 id=" + id);
    }
}
