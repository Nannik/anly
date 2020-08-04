insert into anly.usr (id, name, password, email)
                values (1, "admin", "$2y$08$34OpC2G73A.RZYTEij0Cq.4wi0Y5kaorZ98ZnZB4aGJNpb3AE9iuu", "anlyknitting@gmail.com");

insert into anly.user_role (user_id, roles)
                values (1, "USER"), (1, "ADMIN");