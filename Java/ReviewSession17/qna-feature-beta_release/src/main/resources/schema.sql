create table if not exists persistent_logins
(
    username  varchar(64) not null,
    series    varchar(64) not null,
    token     varchar(64) not null,
    last_used timestamp   not null
);

-- create table if not exists users
-- (
--     email    varchar(255) not null
--         constraint users_pkey
--             primary key,
--     name     varchar(255),
--     password varchar(255),
--     role     varchar(255),
--     state    varchar(255)
-- );
--
-- alter table users
--     owner to postgres;
--
-- -- first invite insert to users table, sign up with email and invite key 'admin-invite'
-- INSERT INTO public.users (email, name, password, role, state, invite_token) VALUES ('admin@task.tracker', 'Admin Name', null, 'ADMIN', 'ACTIVE', 'admin-invite');
